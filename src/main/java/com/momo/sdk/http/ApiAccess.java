package com.momo.sdk.http;

import com.momo.sdk.config.MomoConfig;
import com.momo.sdk.config.MomoConstants;
import com.momo.sdk.config.MomoSession;
import com.momo.sdk.crypto.Base64Utils;
import com.momo.sdk.crypto.CryptoUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * 单次 API 请求构造器。
 *
 * <p>负责把高层调用方提供的 params/body 按 Momo 协议要求加密、签名、装入表单,然后通过
 * {@link HttpClient} 发送,并把返回的字节流解密、解压、深度解析成 {@link JSONObject}。
 *
 * <p>每次请求都应当 new 一个新实例,本类不复用、不线程安全。
 */
public class ApiAccess {

  private final String url;
  private final MomoSession session;
  private final ApiSecurity security;
  private final Map<String, String> headers = new HashMap<>();
  private final Map<String, String> body = new HashMap<>();
  private final JSONObject params = new JSONObject();

  private String sign;

  /**
   * 构造一次请求。
   *
   * @param url 完整接口 URL(已包含 {@link MomoConstants#BASE_URL})
   * @param config 用户配置(目前只用到 {@link MomoConfig#getUsername() username})
   * @param session 运行期会话
   */
  public ApiAccess(String url, MomoConfig config, MomoSession session) {
    String username = config.getUsername();
    this.url = (username == null) ? url : url + "?fr=" + username;
    this.session = session;
    this.security = new ApiSecurity(session);
  }

  /**
   * 批量追加 query 参数。
   *
   * @param params 待追加参数
   * @return this
   */
  public ApiAccess params(JSONObject params) {
    Map<String, Object> map = params.toMap();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      this.params.put(entry.getKey(), entry.getValue());
    }
    return this;
  }

  /**
   * 批量追加表单 body。
   *
   * @param body 待追加表单
   * @return this
   */
  public ApiAccess body(JSONObject body) {
    Map<String, Object> map = body.toMap();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      this.body.put(entry.getKey(), String.valueOf(entry.getValue()));
    }
    return this;
  }

  /**
   * 发送普通业务请求(响应必定是加密 + Brotli 压缩的)。
   *
   * @return 解析后的 JSON 对象
   */
  public JSONObject doRequest() {
    byte[] response = readBody();
    byte[] key = session.getKey().getBytes();
    byte[] decoded = CryptoUtils.decode(response, key);
    String body = BrotliUtils.decompress(decoded);
    return JsonUtils.deep(body);
  }

  /**
   * 发送登录请求。
   *
   * <p>登录接口的响应体可能是加密压缩的,也可能是明文 JSON(如错误码),需要根据头两个
   * 字节是否等于固定头 {@code 0x02 0x03} 来分流。
   *
   * @return 解析后的 JSON 对象
   */
  public JSONObject doLogin() {
    byte[] response = readBody();
    if (response.length >= 2 && response[0] == 2 && response[1] == 3) {
      byte[] key = session.getKey().getBytes();
      byte[] decoded = CryptoUtils.decode(response, key);
      String body = BrotliUtils.decompress(decoded);
      return JsonUtils.deep(body);
    }
    return JsonUtils.deep(new String(response));
  }

  /** 真正发起一次 HTTP 请求。 */
  private byte[] readBody() {
    initRequest();
    return HttpClient.getInstance().url(url).headers(headers).body(body).execute();
  }

  /** 把当前 params 加密、塞进 body,并准备好 headers/sign。 */
  private void initRequest() {
    byte[] data = params.toString().getBytes();
    byte[] encoded = security.encode(data);
    body.put("mzip", Base64Utils.encode(encoded));
    sign = security.sign(encoded);
    fillHeaders();
  }

  /** 装填协议要求的所有请求头。 */
  private void fillHeaders() {
    String currentSession = session.getSession();
    if (currentSession != null) {
      headers.put("cookie", "SESSIONID=" + currentSession);
    }
    headers.put("X-SIGN", sign);
    headers.put("X-Span-Id", MomoConstants.HEADER_X_SPAN_ID);
    headers.put("X-ACT", MomoConstants.HEADER_X_ACT);
    headers.put("X-LV", MomoConstants.HEADER_X_LV);
    headers.put("X-KV", session.getKv());
    headers.put("Accept-Language", MomoConstants.HEADER_LANGUAGE);
    headers.put("X-Trace-Id", IdUtils.uuid());
    headers.put("User-Agent", MomoConstants.USER_AGENT);
  }
}

package com.momo.sdk.config;

import lombok.experimental.UtilityClass;

/**
 * Momo SDK 中所有"非用户输入"的固定参数。
 *
 * <p>除了 {@link MomoConfig} 中的账号、密码之外,所有协议层硬编码值都集中在这里,便于统一
 * 维护、灰度升级。常量按用途分组:
 *
 * <ul>
 *   <li>设备/客户端身份:{@link #DEVICE_ID}、{@link #USER_AGENT}、{@link #APK_SIGN}
 *   <li>服务端地址:{@link #BASE_URL}
 *   <li>协议字段:{@link #ETYPE}、{@link #CODE_VERSION}、{@link #LOGOUT_SOURCE}
 *   <li>请求头常量:{@link #HEADER_X_ACT}、{@link #HEADER_X_LV}、{@link #HEADER_LANGUAGE}
 * </ul>
 */
@UtilityClass
public class MomoConstants {

  // ===================== 服务端地址 =====================

  /** Momo 接口基础地址。 */
  public final String BASE_URL = "https://api.immomo.com";

  // ===================== 设备 / 客户端身份 =====================

  /** 设备 ID。 */
  public final String DEVICE_ID = "a3931e93ff9cb0bc16e38cf3a14aa599";

  /** APK 签名。 */
  public final String APK_SIGN = "4f3a531caff3e37c278659cc78bfaecc";

  /** 客户端 User-Agent。 */
  public final String USER_AGENT =
      "MomoChat/9.15.4 Android/12625 (V2241A; Android 12; Gapps 0; zh_CN; 1; vivo)";

  // ===================== 协议字段 =====================

  /** 登录请求中的 etype 字段。 */
  public final String ETYPE = "2";

  /** 登录请求中的 code_version 字段。 */
  public final String CODE_VERSION = "2";

  /** 登出请求中的 source 字段。 */
  public final String LOGOUT_SOURCE = "1";

  // ===================== 请求头 =====================

  /** 请求头 X-ACT 固定值,代表响应使用 Brotli 压缩。 */
  public final String HEADER_X_ACT = "br";

  /** 请求头 X-LV 固定值。 */
  public final String HEADER_X_LV = "1";

  /** 请求头 Accept-Language 固定值。 */
  public final String HEADER_LANGUAGE = "zh-CN";

  /** 请求头 X-Span-Id 固定值。 */
  public final String HEADER_X_SPAN_ID = "0";
}

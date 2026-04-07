package com.momo.sdk.http;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

/**
 * ID 与随机串生成工具类。
 *
 * <p>替代原工程中依赖 Hutool 的 {@code Utilize},改用 JDK 自带能力。
 */
@UtilityClass
public class IdUtils {

  /**
   * 生成大写、不带连字符外形的 UUID(保留 Hutool 原有行为为大写带连字符)。
   *
   * @return 大写 UUID 字符串
   */
  public String uuid() {
    return UUID.randomUUID().toString().toUpperCase();
  }

  /**
   * 生成 Momo 协议要求的 mapId(10 位数字字符串)。
   *
   * <p>规则:取当前毫秒数对一百万取模,不足 6 位前补到 10 万,再拼接 4 位 [1000, 9999] 的
   * 随机数。
   *
   * @return 10 位数字字符串
   */
  public String mapId() {
    long millis = System.currentTimeMillis() % 1000000L;
    if (millis < 100000L) {
      millis += 100000L;
    }
    int suffix = ThreadLocalRandom.current().nextInt(1000, 10000);
    return millis + "" + suffix;
  }
}

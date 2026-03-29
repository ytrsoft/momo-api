package com.ytrsoft.momo;

import com.ytrsoft.momo.model.LoginResult;

/** Minimal usage demo. */
public final class DemoApp {

  private DemoApp() {}

  public static void main(String[] args) {
    MomoConfig config = new MomoConfig.Builder()
        .account("979025201")
        .password("momo88888888")
        .build();

    MomoClient client = new MomoClient(config);
    LoginResult result = client.login();
    System.out.println("Session: " + result.getSession());
  }
}

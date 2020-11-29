package com.x.x17fun.alipay;

public interface AliPayResultListener {
  void onPaySuccess();

  void onPaying();

  void onPayFail();

  void onPayCancel();

  void onPayConnectError();
}

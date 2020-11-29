package com.x.x17fun.base;

public interface IBaseCallBack<T> {
    void onSuccess(T data);
    void onFail(String msg);
}

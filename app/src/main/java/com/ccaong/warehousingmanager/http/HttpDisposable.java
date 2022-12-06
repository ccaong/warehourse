package com.ccaong.warehousingmanager.http;


import io.reactivex.observers.DisposableObserver;

/**
 * @author devel
 * 返回数据
 */
public abstract class HttpDisposable<T> extends DisposableObserver<T> {

    public HttpDisposable() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    public void onNext(T value) {
        success(value);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     *
     * @param t
     */
    public abstract void success(T t);
}
package com.ccaong.warehousingmanager.http;


import com.ccaong.warehousingmanager.config.Config;
import com.ccaong.warehousingmanager.config.Constant;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ccaong
 */
public class HttpFactory {

    private HttpFactory() {
    }

    /**
     * 设置HttpClient
     */
    private static OkHttpClient HTTP_CLIENT =
            new OkHttpClient.Builder()
                    .addInterceptor(new HttpInterceptor())
                    //设置超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

    private static Retrofit retrofit = null;

    /**
     * 获取单例Retrofit
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> service) {
        String baseUrl = Hawk.get(Constant.BASE_URL, Config.DEFAULT_URL);
        baseUrl = baseUrl + "/external/api/pda/";

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HTTP_CLIENT)
                .build();
        return retrofit.create(service);
    }


    /**
     * 获取单例的Retrofit
     *
     * @param newBaseUrl 新的baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getChangeUrlInstance(String newBaseUrl, Class<T> service) {
        return new Retrofit.Builder().baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HTTP_CLIENT)
                .build()
                .create(service);
    }


    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> schedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}

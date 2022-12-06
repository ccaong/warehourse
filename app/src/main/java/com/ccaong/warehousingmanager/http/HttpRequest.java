package com.ccaong.warehousingmanager.http;

/**
 * @author ccaong
 */
public class HttpRequest {

    private static Api Instance;

    public static Api getInstance() {
        if (Instance == null) {
            synchronized (HttpRequest.class) {
                if (Instance == null) {
                    Instance = HttpFactory.getInstance(Api.class);
                }
            }
        }
        return Instance;
    }

    public static Api getInstance(String url) {
        return HttpFactory.getChangeUrlInstance(url, Api.class);
    }

    /**
     * 重置（修改了BaseUrl之后，需要重置）
     */
    public static void resetApi() {
        Instance = null;
    }
}

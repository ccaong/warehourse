package com.ccaong.warehousingmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.ccaong.warehousingmanager.manager.MyActivityManager;
import com.ccaong.warehousingmanager.util.SPconfig;
import com.orhanobut.hawk.Hawk;
import com.pow.api.cls.RfidPower;
import com.uhf.api.cls.ErrInfo;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : devel
 * @date : 2020/2/19 11:30
 * @desc :application
 */

public class App extends Application {
    private static Context context;

    public Reader Mreader;
    public SPconfig spf;
    public RfidPower Rpower;
    public ReaderParams Rparams;


    public ErrInfo ei = new ErrInfo();


    public class ReaderParams {

        // save param
        public int opant;

        public List<String> invpro;
        public String opro;
        public int[] uants;
        public int readtime;
        public int sleep;

        public int checkant;
        public int[] rpow;
        int crpow;
        public int[] wpow;

        public int region;
        public int[] frecys;
        public int frelen;

        public int session;
        public int qv;
        public int wmode;
        public int blf;
        public int maxlen;
        public int target;
        public int gen2code;
        public int gen2tari;

        public String fildata;
        public int filadr;
        public int filbank;
        public int filisinver;
        public int filenable;

        public int emdadr;
        public int emdbytec;
        public int emdbank;
        public int emdenable;

        public int adataq;
        public int rhssi;
        public int invw;
        public int iso6bdeep;
        public int iso6bdel;
        public int iso6bblf;
        public int option;
        // other params

        public int optime;


        public ReaderParams() {
            opant = 1;
            invpro = new ArrayList<String>();
            invpro.add("GEN2");
            uants = new int[1];
            uants[0] = 1;
            sleep = 400;
            readtime = 1000;
            optime = 1000;
            opro = "GEN2";
            checkant = 1;
            crpow = 1000;
            rpow = new int[]{crpow, crpow, crpow, crpow, crpow, crpow, crpow,
                    crpow, crpow, crpow, crpow, crpow, crpow, crpow, crpow,
                    crpow};
            wpow = new int[]{crpow, crpow, crpow, crpow, crpow, crpow, crpow,
                    crpow, crpow, crpow, crpow, crpow, crpow, crpow, crpow,
                    crpow};
            region = 1;
            frelen = 0;
            session = 0;
            qv = -1;
            wmode = 0;
            blf = 0;
            maxlen = 0;
            target = 0;
            gen2code = 2;
            gen2tari = 0;

            fildata = "";
            filadr = 32;
            filbank = 1;
            filisinver = 0;
            filenable = 0;

            emdadr = 0;
            emdbytec = 0;
            emdbank = 1;
            emdenable = 0;

            adataq = 0;
            rhssi = 1;
            invw = 0;
            iso6bdeep = 0;
            iso6bdel = 0;
            iso6bblf = 0;
            option = 0;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initActivityManager();
        init();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 管理Activity
     */
    private void initActivityManager() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 一些第三方库和本地代码的初始化设置
     */
    private void init() {
        Hawk.init(context).build();
    }

}

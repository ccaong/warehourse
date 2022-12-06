package com.ccaong.warehousingmanager.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ccaong.warehousingmanager.App;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.TestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.uhf.api.cls.Reader;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Activity的基类
 *
 * @param <DB> data binding
 * @param <VM> view model
 * @author devel
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends AppCompatActivity {

    public SoundPool soundPool;
    public App myapp;
    public ReadRfidTask readRfidTask;

    private boolean scanServiceStatus = false;


    public static final String TAG1 = BaseActivity.class.getSimpleName();


    public ScanChangeReceiver scanChangeReceiver;


    public DB mDataBinding;

    protected VM mViewModel;

    public ActionBar actionBar;


    /**
     * 创建一个名为ScanChangeReceiver的广播接收器
     */
    public class ScanChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.START_SCAN_CODE_ACTION)) {
                // 按下扳机按钮
                startScan();
                if (isSupportRfid()) {
                    startScanRFID();
                }
            } else if (intent.getAction().equals(Constant.SCAN_CODE_RESULT_ACTION)) {
                // 扫码结果
                String value = intent.getExtras().getString("DATA");
                Log.e(TAG1, "扫码结果" + value);
                scanResult(value);
                if (isSupportRfid()) {
                    stopRfid();
                }
            }
        }
    }

    public void initScanReceiver() {
        Log.e("BaseActivity", "注册");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.SCAN_CODE_RESULT_ACTION);
        intentFilter.addAction(Constant.START_SCAN_CODE_ACTION);
        scanChangeReceiver = new ScanChangeReceiver();
        registerReceiver(scanChangeReceiver, intentFilter);
    }

    /**
     * 扫码
     */
    public void scanCode() {
        Intent it = new Intent(Constant.START_SCAN_CODE_ACTION);
        sendBroadcast(it);
    }

    /**
     * 开始扫描RFID标签
     */
    public void startScanRFID() {
        startTime = System.currentTimeMillis();
        if (readRfidTask != null) {
            if (readRfidTask.isRead()) {
                readRfidTask.stop();
            } else {
                readRfidTask.isExit = false;
                new Thread(readRfidTask).start();
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        if (isNoActionBar()) {
            setNoActionBar();
        }

        if (isNoToolBar()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId());

        if (isSupportRfid()) {
            // 初始化声音对象
            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            soundPool.load(this, R.raw.beep333, 1);
            Application app = getApplication();
            myapp = (App) app;

            readRfidTask = new ReadRfidTask();
        }

        initViewModel();
        bindViewModel();

        mDataBinding.setLifecycleOwner(this);

        init();

        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSupportScan() || isSupportRfid()) {
            initScanReceiver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消注册，动态注册的广播接收器一定要取消注册才行
        if (scanChangeReceiver != null) {
            Log.e("BaseActivity", "取消注册");
            unregisterReceiver(scanChangeReceiver);
        }
    }

    @Override
    public void onStop() {
        stopRfid();
        super.onStop();
    }


    /**
     * 设置沉浸式状态栏
     */
    private void setNoActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 处理参数
     *
     * @param intent 参数容器
     */
    protected void handleIntent(Intent intent) {

    }


    /**
     * 是否隐藏toolbar
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isNoToolBar() {
        return false;
    }

    /**
     * 是否为沉浸模式
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isNoActionBar() {
        return false;
    }

    /**
     * 是否支持二维码和条形码扫描
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isSupportScan() {
        return false;
    }

    /**
     * 是否支持rfid扫描
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isSupportRfid() {
        return false;
    }

    /**
     * 获取当前页面的布局资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化ViewModel
     */
    protected abstract void initViewModel();

    /**
     * 绑定ViewModel
     */
    protected abstract void bindViewModel();

    /**
     * 初始化
     */
    protected abstract void init();


    /**
     * 按下扳机按钮开始扫描
     */
    protected void startScan() {
    }

    /**
     * 条形码和二维码扫码结果
     *
     * @param result 结果
     */
    protected void scanResult(String result) {

    }

    /**
     * RFID扫描结果
     *
     * @param result 结果
     */
    protected void rfidResult(String result) {

    }

    /**
     * activity onStart 对扫描节点预处理
     */
    public void onStartPreparation() {
        // 写入节点
        writeToNode(Constant.GPIO_PATH, Constant.UART_FOR_UFH);

    }

    /**
     * activity onStop 对扫描节点预处理
     */
    public void onStopDestruction() {
        // 写入节点
        writeToNode(Constant.GPIO_PATH, Constant.UART_FOR_SCAN);
    }

    /**
     * 开启扫码服务
     */
    public void startScanService() {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(new ComponentName(Constant.SCANNER_SERVICE_PACKAGE_NAME, Constant.SCANNER_SERVICE_NAME));
        startService(intent);
    }

    /**
     * 停止扫码服务
     */
    public void stopScanService() {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(new ComponentName(Constant.SCANNER_SERVICE_PACKAGE_NAME, Constant.SCANNER_SERVICE_NAME));
        scanServiceStatus = stopService(intent);
    }

    public void writeToNode(String path, int value) {
        android.util.Log.i("krn", "path: " + path + ", value: " + value);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(String.valueOf(value));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRfid() {
        if (readRfidTask != null) {
            readRfidTask.stop();
        }
    }

    public Long startTime;

    public class ReadRfidTask implements Runnable {

        public boolean isExit = false;
        public boolean isRead = false;

        public void stop() {
            isRead = false;
            isExit = true;
        }

        public boolean isRead() {
            return isRead;
        }

        @Override
        public void run() {
            while (!isExit) {
                Log.e(TAG1, "循环读卡中");
                Log.e(TAG1, "读卡线程" + android.os.Process.myTid());
                if (System.currentTimeMillis() - startTime > 8000) {
                    Log.e(TAG1, "读卡超时");
                    stop();
                    return;
                }
                String[] tag = null;
                int[] tagcnt = new int[1];
                tagcnt[0] = 0;

                synchronized (this) {
                    Reader.READER_ERR er;
                    er = myapp.Mreader.TagInventory_Raw(myapp.Rparams.uants,
                            myapp.Rparams.uants.length,
                            (short) myapp.Rparams.readtime, tagcnt);

                    if (er == Reader.READER_ERR.MT_OK_ERR) {
                        if (tagcnt[0] > 0) {
                            soundPool.play(1, 1, 1, 0, 0, 1);
                            tag = new String[tagcnt[0]];
                            for (int i = 0; i < tagcnt[0]; i++) {
                                Reader.TAGINFO tfs = myapp.Mreader.new TAGINFO();
                                er = myapp.Mreader.GetNextTag(tfs);
                                if (er == Reader.READER_ERR.MT_HARDWARE_ALERT_ERR_BY_TOO_MANY_RESET) {
                                    runOnUiThread(() -> Toast.makeText(BaseActivity.this, "读取失败，请重试", Toast.LENGTH_SHORT).show());
                                    stop();
                                    return;
                                }

                                if (er == Reader.READER_ERR.MT_OK_ERR) {
                                    tag[i] = Reader.bytes_Hexstr(tfs.EpcId);
                                    String result = tag[i];
                                    runOnUiThread(() -> rfidResult(result));
                                    stop();
                                    return;
                                } else {
                                    break;
                                }
                            }
                        }

                    } else {
                        myapp.Mreader.GetLastDetailError(myapp.ei);
                        if (er == Reader.READER_ERR.MT_HARDWARE_ALERT_ERR_BY_TOO_MANY_RESET) {
                            runOnUiThread(() -> Toast.makeText(BaseActivity.this, "读取失败，请重试", Toast.LENGTH_SHORT).show());
                            stop();
                            return;
                        }
                    }
                }

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

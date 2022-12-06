package com.ccaong.warehousingmanager.ui.activity.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.bean.LoginResponseBean;
import com.ccaong.warehousingmanager.bean.WareHouseResponseBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityLoginBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.main.MainActivity;
import com.ccaong.warehousingmanager.ui.activity.server.ServerSettingActivity;
import com.ccaong.warehousingmanager.util.DevicesUtils;
import com.ccaong.warehousingmanager.util.RsaUtils;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ccaong
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        mDataBinding.cbRePwd.setChecked(Hawk.get(Constant.REMEMBER_PWD, false));
        mDataBinding.btnLogin.setOnClickListener(view -> login());
        mDataBinding.tvServer.setOnClickListener(view -> startActivity(new Intent(this, ServerSettingActivity.class)));

        String imei = DevicesUtils.getIMEI1(this);
        Hawk.put(Constant.DEVICE_IMEI, imei);
//        Hawk.put(Constant.DEVICE_IMEI, "869448031919732");
        Log.e(TAG, "设备imei" + imei);

        String sn = DevicesUtils.getSN();
        Log.e(TAG, "设备sn" + sn);
    }

    /**
     * 登录
     */
    private void login() {

        if (Objects.requireNonNull(mViewModel.userName.getValue()).isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.requireNonNull(mViewModel.userPwd.getValue()).isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = mViewModel.userPwd.getValue();
        try {
            password = RsaUtils.encryptByPublicKey(password);
        } catch (Exception e) {
            Log.e(TAG, "加密出错" + e);
            e.printStackTrace();
        }
        Log.e(TAG, "加密后的密码" + password);
        Map request = new HashMap<String, String>();
        request.put("username", mViewModel.userName.getValue());
        request.put("password", password);
        HttpRequest.getInstance()
                .login(request)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<LoginResponseBean>() {
                    @Override
                    public void success(LoginResponseBean bean) {

                        Log.e(TAG, bean.getMsg());
                        if (bean.getCode() == 200) {
                            Hawk.put(Constant.USER_NAME, mViewModel.userName.getValue());
                            Hawk.put(Constant.TOKEN, bean.getToken());
                            if (mDataBinding.cbRePwd.isChecked()) {
                                Hawk.put(Constant.LOGIN_NAME, mViewModel.userName.getValue());
                                Hawk.put(Constant.LOGIN_PWD, mViewModel.userPwd.getValue());
                                Hawk.put(Constant.REMEMBER_PWD, true);
                            } else {
                                Hawk.delete(Constant.LOGIN_NAME);
                                Hawk.delete(Constant.LOGIN_PWD);
                                Hawk.put(Constant.REMEMBER_PWD, false);
                            }
                            getWarehouse();
                        } else {
                            Toast.makeText(LoginActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, e.toString());
                    }
                });
    }

    /**
     * 获取当前账户的仓库列表
     */
    private void getWarehouse() {

        HttpRequest.getInstance()
                .getStorehouse()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<WareHouseResponseBean>() {
                    @Override
                    public void success(WareHouseResponseBean bean) {
                        Log.e(TAG, "仓库列表" + bean.getCode() + bean.getMsg());
                        if (bean.getCode() == 200) {
                            showSingleChoiceDialog(bean.getData());
                        } else {
                            Toast.makeText(LoginActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, e.toString());
                    }
                });
    }

    int yourChoice;

    /**
     * 选择仓库的dialog
     */
    private void showSingleChoiceDialog(List<WareHouseResponseBean.DataDTO> list) {

        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getName();
        }

        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(LoginActivity.this);
        singleChoiceDialog.setTitle("请选择仓库");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                (dialog, which) -> yourChoice = which);
        singleChoiceDialog.setPositiveButton("确定",
                (dialog, which) -> {
                    if (yourChoice != -1) {
                        Toast.makeText(LoginActivity.this,
                                "你选择了" + items[yourChoice],
                                Toast.LENGTH_SHORT).show();
                        Hawk.put(Constant.STOREHOUSE_ID, list.get(yourChoice).getId());
                        Hawk.put(Constant.STOREHOUSE_NAME, list.get(yourChoice).getName());
                        Hawk.put(Constant.STOREHOUSE_TYPE, list.get(yourChoice).getType());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
        singleChoiceDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
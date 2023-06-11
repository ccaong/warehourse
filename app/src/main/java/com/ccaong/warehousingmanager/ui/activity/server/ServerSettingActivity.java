package com.ccaong.warehousingmanager.ui.activity.server;

import android.widget.Toast;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.config.Config;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityServerBinding;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.orhanobut.hawk.Hawk;

/**
 * @author caocong
 * @date 2022/9/17
 */
public class ServerSettingActivity extends BaseActivity<ActivityServerBinding, ServerSettingViewModel> {

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_server;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        mDataBinding.etServerAddress.setText(Hawk.get(Constant.BASE_URL, Config.DEFAULT_URL));
        mDataBinding.tvCancel.setOnClickListener(view -> finish());
        mDataBinding.tvConfirm.setOnClickListener(view -> setServerAddress(mDataBinding.etServerAddress.getText().toString()));
    }


    private void setServerAddress(String address) {
        if (address.isEmpty()) {
            Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
        } else {
            Hawk.put(Constant.BASE_URL, address);
            HttpRequest.resetApi();
            finish();
        }
    }
}

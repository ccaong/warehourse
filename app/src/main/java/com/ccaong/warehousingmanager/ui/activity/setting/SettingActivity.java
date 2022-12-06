package com.ccaong.warehousingmanager.ui.activity.setting;

import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivitySettingBinding;
import com.ccaong.warehousingmanager.ui.activity.setting.rules.ConfigureRulesActivity;

/**
 * @author eyecool
 * @date 2022/10/21
 */
public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        Intent intent = new Intent(SettingActivity.this, ConfigureRulesActivity.class);

        mDataBinding.btnSetWl.setOnClickListener(view -> {
            intent.putExtra("TYPE", Constant.RULES_CODE_GOOD);
            startActivity(intent);
        });
        mDataBinding.btnSetWz.setOnClickListener(view -> {
            intent.putExtra("TYPE", Constant.RULES_CODE_LOCAL);
            startActivity(intent);
        });

        mDataBinding.btnSetZj.setOnClickListener(view -> {
            intent.putExtra("TYPE", Constant.RULES_CODE_CONTAINER);
            startActivity(intent);
        });
    }
}

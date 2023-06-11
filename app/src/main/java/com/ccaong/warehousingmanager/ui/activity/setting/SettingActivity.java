package com.ccaong.warehousingmanager.ui.activity.setting;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.App;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.bean.SpinnerTestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivitySettingBinding;
import com.ccaong.warehousingmanager.ui.activity.setting.rules.ConfigureRulesActivity;
import com.orhanobut.hawk.Hawk;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
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

        actionBar.setTitle("设置");

        initSp();

        Application app = getApplication();
        myapp = (App) app;

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

        String rfidCropw = Hawk.get("RFID_CROPW", "1000");
        switch (rfidCropw) {
            case "500":
                mDataBinding.spRfid.setSelection(0);
                break;
            case "800":
                mDataBinding.spRfid.setSelection(1);
                break;
            case "1000":
                mDataBinding.spRfid.setSelection(2);
                break;
            case "1500":
                mDataBinding.spRfid.setSelection(3);
                break;
            case "2000":
                mDataBinding.spRfid.setSelection(4);
                break;
        }

        mDataBinding.btnSave.setOnClickListener(view -> {
            String strval = "";
            for (int i = 0; i < 16; i++) {
                strval += Integer.parseInt(cropw) + ",";
            }
            strval = strval.substring(0, strval.length() - 1);
            myapp.spf.SaveString("RPOW", strval);
            myapp.spf.SaveString("WPOW", strval);
            // 需要在首页重新连接
            Hawk.put("RE_CONNECT_RFID", true);
            Hawk.put("RFID_CROPW", cropw);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        });
    }

    private String cropw = "1000";

    private void initSp() {
        List<SpinnerTestBean> list = new ArrayList<>();
        list.add(new SpinnerTestBean("500", "500"));
        list.add(new SpinnerTestBean("800", "800"));
        list.add(new SpinnerTestBean("1000", "1000"));
        list.add(new SpinnerTestBean("1500", "1500"));
        list.add(new SpinnerTestBean("2000", "2000"));

        ArrayAdapter<SpinnerTestBean> productAdapter = new ArrayAdapter<>(this, R.
                layout.item_setting_spinner, list);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spRfid.setAdapter(productAdapter);
        mDataBinding.spRfid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTestBean data = list.get(i);
                cropw = data.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

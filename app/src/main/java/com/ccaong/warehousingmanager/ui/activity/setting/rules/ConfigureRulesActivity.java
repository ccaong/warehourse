package com.ccaong.warehousingmanager.ui.activity.setting.rules;

import static com.ccaong.warehousingmanager.util.CodeParseUtils.searchAllIndex;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityConfigRulesBinding;
import com.orhanobut.hawk.Hawk;

import java.util.List;

/**
 * @author caocong
 * @date 2022/10/21
 */
public class ConfigureRulesActivity extends BaseActivity<ActivityConfigRulesBinding, ConfigureRulesViewModel> {

    String type;
    String result = "";

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        type = intent.getStringExtra("TYPE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_config_rules;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ConfigureRulesViewModel.class);

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        if (type.equals(Constant.RULES_CODE_GOOD)) {
            mDataBinding.btnSku.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.btnSku.setVisibility(View.GONE);
        }

        mDataBinding.btnA.setOnClickListener(view -> updateRules("A"));
        mDataBinding.btn0.setOnClickListener(view -> updateRules("0"));
        mDataBinding.btnOther.setOnClickListener(view -> updateRules("无规则"));
        mDataBinding.btnSku.setOnClickListener(view -> updateRules("SKU"));
        mDataBinding.btnSeparate.setOnClickListener(view -> updateRules("_"));
        mDataBinding.btnDel.setOnClickListener(view -> {
            result = "";
            mDataBinding.tvResult.setText(result);
        });

        mDataBinding.tvSubmit.setOnClickListener(view -> saveRules());
    }

    private void updateRules(String s) {
        result = result + s;
        mDataBinding.tvResult.setText(result);
    }

    /**
     * 保存规则
     */
    private void saveRules() {

        if ("".equals(result)) {
            Toast.makeText(this, "请先输入规则", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (type) {
            case Constant.RULES_CODE_GOOD:
                if (!result.contains("SKU")) {
                    Toast.makeText(this, "物料码中必须有SKU", Toast.LENGTH_SHORT).show();
                    return;
                }
                Hawk.put(Constant.RULES_SHOW_GOOD, result);
                break;
            case Constant.RULES_CODE_LOCAL:
                Hawk.put(Constant.RULES_SHOW_LOCAL, result);
                break;
            case Constant.RULES_CODE_CONTAINER:
                Hawk.put(Constant.RULES_SHOW_CONTAINER, result);
                break;
            default:
                break;
        }


        String[] sArray = result.split("_");
        int size = sArray.length;
        // 记录长度
        Hawk.put(type + Constant.RULES_SIZE, size);

        for (int i = 0; i < size; i++) {
            if (sArray[i].equals("无规则")) {
                Hawk.put(type + Constant.RULES_UNIT_LENGTH + "_" + i, -1);
            } else if (sArray[i].equals("SKU")) {
                Hawk.put(type + Constant.RULES_UNIT_LENGTH + "_" + i, -1);
                Hawk.put(type + Constant.RULES_SKU_INDEX, i);
            } else {
                String unit = sArray[i];
                int length = unit.length();
                List<Integer> indexs = searchAllIndex(unit);
                Hawk.put(type + Constant.RULES_UNIT_LENGTH + "_" + i, length);
                Hawk.put(type + Constant.RULES_UNIT_A_INDEX + "_" + i, indexs);
            }
        }

        Toast.makeText(this, "保存成功，规则已生效", Toast.LENGTH_SHORT).show();
    }
}

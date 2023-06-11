package com.ccaong.warehousingmanager.ui.activity.lower.manual;

import static com.ccaong.warehousingmanager.util.StringUtils.isEmpty;

import android.content.Intent;
import android.util.Log;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.databinding.ActivityPutManualBinding;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * @author caocong
 * @date 2022/9/21
 */
public class LowerManualActivity extends BaseActivity<ActivityPutManualBinding, BaseViewModel> {

    String code = "";

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        code = intent.getStringExtra("LOCATION_CODE");
    }

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_put_manual;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }


    @Override
    protected void startScan() {
        super.startScan();

        mDataBinding.etZj.clearFocus();
        mDataBinding.etWz.clearFocus();
    }

    @Override
    protected void scanResult(String result) {
        Log.e(TAG1, "载具码：" + result);
        if (CodeParseUtils.isCodeContainer(result)) {
            mDataBinding.etZj.setText(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);
        if (!CodeParseUtils.rfidIsLocalCode(result)) {
            mDataBinding.etZj.setText(result);
        }
    }

    @Override
    protected void init() {
        mDataBinding.tvTitle.setText("人工下架");

        if (!isEmpty(code)) {
            mDataBinding.etWz.setText(code);
        }

        mDataBinding.tvCancel.setOnClickListener(view -> finish());
        mDataBinding.tvConfirm.setOnClickListener(view -> submit());

        mDataBinding.ivScanWz.setOnClickListener(view -> scanCode());

    }


    private void submit() {

        String zjId = mDataBinding.etZj.getText().toString();
        String wzId = mDataBinding.etWz.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("ZJ_ID", zjId);
        intent.putExtra("WZ_ID", wzId);
        setResult(RESULT_OK, intent);
        finish();
    }
}

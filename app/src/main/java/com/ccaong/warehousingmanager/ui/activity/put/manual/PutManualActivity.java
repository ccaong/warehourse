package com.ccaong.warehousingmanager.ui.activity.put.manual;

import static com.ccaong.warehousingmanager.util.StringUtils.isEmpty;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.ValidLocationResponse;
import com.ccaong.warehousingmanager.databinding.ActivityPutManualBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.put.detail.PutDetailActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * @author caocong
 * @date 2022/9/21
 */
public class PutManualActivity extends BaseActivity<ActivityPutManualBinding, BaseViewModel> {

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
        code = intent.getStringExtra("CONTAINER_CODE");
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
        mDataBinding.etWz.setText("");
        mDataBinding.etWz.clearFocus();
        mDataBinding.etZj.clearFocus();
    }

    @Override
    protected void scanResult(String result) {
        Log.e(TAG1, "位置码：" + result);
        if (CodeParseUtils.isLocalCode(result)) {
            getValidLocation(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);
        if (CodeParseUtils.rfidIsLocalCode(result)) {
            String code = CodeParseUtils.getRfidLocalCode(result);
            getValidLocation(code);
        }
    }


    private void getValidLocation(String id) {
        HttpRequest.getInstance()
                .getValidLocation(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ValidLocationResponse>() {
                    @Override
                    public void success(ValidLocationResponse bean) {
                        if (bean.getCode() == 200) {
                            if (bean.getData()) {
                                mDataBinding.etWz.setText(id);
                            } else {
                                Toast.makeText(PutManualActivity.this, "扫描的位置不可用！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PutManualActivity.this, "扫描的位置不可用！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Toast.makeText(PutManualActivity.this, "扫描的位置不可用！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void init() {
        if (!isEmpty(code)) {
            mDataBinding.etZj.setText(code);
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

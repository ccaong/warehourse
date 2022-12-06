package com.ccaong.warehousingmanager.ui.activity.put.manual;

import static com.ccaong.warehousingmanager.util.StringUtils.isEmpty;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.databinding.ActivityPutManualBinding;

/**
 * @author eyecool
 * @date 2022/9/21
 */
public class PutManualActivity extends BaseActivity<ActivityPutManualBinding, BaseViewModel> {

    String code = "";

    @Override
    protected boolean isSupportScan() {
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
        mDataBinding.etWz.requestFocus();
    }

    @Override
    protected void scanResult(String result) {
        Log.e(TAG1, "位置码：" + result);
    }

    @Override
    protected void init() {
        if (!isEmpty(code)) {
            mDataBinding.etZj.setText(code);
        }

        mDataBinding.tvCancel.setOnClickListener(view -> finish());
        mDataBinding.tvConfirm.setOnClickListener(view -> submit());

        mDataBinding.ivScanWz.setOnClickListener(view -> scanCode());

//        mDataBinding.ivScanZj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: 2022/10/11 扫码载具码
//                Toast.makeText(PutManualActivity.this, "扫描载具编码", Toast.LENGTH_SHORT).show();
//            }
//        });
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

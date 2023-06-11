package com.ccaong.warehousingmanager.ui.activity.inventory.manual;

import android.content.Intent;
import android.widget.Toast;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.databinding.ActivitySortManualBinding;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * @author caocong
 * @date 2022/9/22
 */
public class SortManualActivity extends BaseActivity<ActivitySortManualBinding, BaseViewModel> {

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sort_manual;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        mDataBinding.tvConfirm.setOnClickListener(view -> confirm());
        mDataBinding.tvCancel.setOnClickListener(view -> finish());

        mDataBinding.ivScan.setOnClickListener(view -> scanCode()
        );
    }

    @Override
    protected void startScan() {
        super.startScan();
        mDataBinding.etFo.requestFocus();
    }

    @Override
    protected void scanResult(String result) {
        scan(result);
    }


    /**
     * 扫码物料编码
     */
    private void scan(String id) {
        int num = 1;
        mDataBinding.etNum.setText(String.valueOf(num));

        if (CodeParseUtils.isGoodsCode(id)) {
            mDataBinding.etWl.setText(CodeParseUtils.getGoodSkuCode(id));
        } else {
            Toast.makeText(this, "不是物料码！", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirm() {
        String id = mDataBinding.etWl.getText().toString();
        String numStr = mDataBinding.etNum.getText().toString();

        int num = 0;
        try {
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "请输入正确的数量", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("ID", id);
        intent.putExtra("NUM", num);
        setResult(RESULT_OK, intent);
        finish();
    }
}

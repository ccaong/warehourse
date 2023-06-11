package com.ccaong.warehousingmanager.ui.activity.goods;

import android.widget.Toast;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.databinding.ActivityGoodInfoBinding;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

public class GoodsQueryActivity extends BaseActivity<ActivityGoodInfoBinding, BaseViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_good_info;
    }

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);

        if (CodeParseUtils.isGoodsCode(result)) {
            String sku = CodeParseUtils.getGoodSkuCode(result);
            String serialNumber = CodeParseUtils.getSno(result);
            String taskNumber = CodeParseUtils.getTaskNumber(result);
            String owner = CodeParseUtils.getOwnerCode(result);

            mDataBinding.sku.setText(sku);
            mDataBinding.serialNum.setText(serialNumber);
            mDataBinding.taskNumber.setText(taskNumber);
            mDataBinding.owner.setText(owner);
        } else {
            showErrorDialog("警告", "扫描的二维码不是物资码，请重新扫描！");
            mDataBinding.sku.setText("");
            mDataBinding.serialNum.setText("");
            mDataBinding.taskNumber.setText("");
            mDataBinding.owner.setText("");
        }
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        actionBar.setTitle("物资信息");
    }
}

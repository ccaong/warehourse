package com.ccaong.warehousingmanager.ui.activity.inventory.work;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.InventoryDetailResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityInventoryWorkBinding;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;

/**
 * 盘点
 *
 * @author caocong
 * @date 2022/9/21
 */
public class InventoryWorkActivity extends BaseActivity<ActivityInventoryWorkBinding, InventoryWorkViewModel> {
    private static final String TAG = InventoryWorkActivity.class.getSimpleName();

    CommonAdapter<InventoryDetailResponse.DataDTO.ListDTO.ListDTOProduct> commonAdapter;
    String id;
    String code;


    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected void startScan() {
        super.startScan();
        mDataBinding.etFo.requestFocus();
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        if (CodeParseUtils.isGoodsCode(result)) {
            changeData(CodeParseUtils.getGoodSkuCode(result), 1);
        }
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        code = intent.getStringExtra("C_CODE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_inventory_work;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(InventoryWorkViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }


    @Override
    protected void init() {
        actionBar.setTitle("盘点任务");
        mDataBinding.tvId.setText(id);
        mDataBinding.tvCode.setText(code);
        mDataBinding.tvWarehouse.setText(Hawk.get(Constant.STOREHOUSE_NAME));
        mViewModel.loadData(id, code);
        initRecyclerView();
        mViewModel.getData().observe(this, listDTO -> commonAdapter.onItemDatasChanged(listDTO.getList()));
        mDataBinding.tvSubmit.setOnClickListener(view -> submit());

    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<InventoryDetailResponse.DataDTO.ListDTO.ListDTOProduct>(R.layout.item_inventor_work, BR.inventorWorkData) {
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    /**
     * 根据扫码结果修改物料的盘点数量
     *
     * @param id  物料编码
     * @param num 物料盘点数量
     */
    private void changeData(String id, int num) {
        Log.e(TAG, "物料：" + id + "的数量是：" + num);
        for (InventoryDetailResponse.DataDTO.ListDTO.ListDTOProduct product : mViewModel.getData().getValue().getList()) {
            if (id.equals(product.getMaterialCode())) {
                Log.e(TAG, "修改物料的盘点数量");
                int lastNum = 0;
                try {
                    lastNum = Integer.parseInt(product.getPandianNum());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                num = num + lastNum;
                product.setPandianNum(String.valueOf(num));
            }
        }
        // 刷新数据
        mViewModel.productData.postValue(mViewModel.getData().getValue());
    }

    /**
     * 提交
     */
    private void submit() {
        Hawk.put(Constant.PANDIAN_ZAIJU_DATA, mViewModel.getData().getValue());
        setResult(RESULT_OK, new Intent());
        finish();
    }
}

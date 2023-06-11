package com.ccaong.warehousingmanager.ui.activity.sort.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.SortDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivitySortWarehouseDetailBinding;
import com.ccaong.warehousingmanager.ui.activity.sort.work.SortWarehouseWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class SortWarehouseDetailActivity extends BaseActivity<ActivitySortWarehouseDetailBinding, SortWarehouseDetailViewMode> {

    String relNumber;
    String orderNumber;
    String relSource;

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
        relNumber = intent.getStringExtra("REL_NUMBER");
        orderNumber = intent.getStringExtra("ORDER_NUMBER");
        relSource = intent.getStringExtra("REL_SOURCE");
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sort_warehouse_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SortWarehouseDetailViewMode.class);
    }

    @Override
    protected void bindViewModel() {

        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.scan) {
            startScanRFID();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        if (CodeParseUtils.isCodeContainer(result)) {
            for (SortDetailResponse.DataDTO itemData : mViewModel.getData().getValue()) {
                if (result.equals(itemData.getGoodsInfo().getContainerSerialNum())) {
                    if (itemData.getPickStatus().endsWith("4")) {
                        Toast.makeText(SortWarehouseDetailActivity.this, "拣货已经完成，不需要继续操作", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(itemData.getId(), itemData.getGoodsInfo().getContainerSerialNum());
                    return;
                }
            }
            Toast.makeText(this, "扫描的载具码不在任务内！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);
        if (!CodeParseUtils.rfidIsLocalCode(result)) {
            for (SortDetailResponse.DataDTO itemData : mViewModel.getData().getValue()) {
                if (result.equals(itemData.getGoodsInfo().getContainerSerialNum())) {
                    if (itemData.getPickStatus().endsWith("4")) {
                        Toast.makeText(SortWarehouseDetailActivity.this, "拣货已经完成，不需要继续操作", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(itemData.getId(), itemData.getGoodsInfo().getContainerSerialNum());
                    return;
                }
            }
            Toast.makeText(this, "扫描的载具码不在任务内！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void init() {
        actionBar.setTitle("拣货物料列表");
        initRecyclerView();
    }

    private void initRecyclerView() {

        CommonAdapter<SortDetailResponse.DataDTO> commonAdapter = new CommonAdapter<SortDetailResponse.DataDTO>(R.layout.item_sort_product, BR.sortProduct) {
            @Override
            public void addListener(View root, SortDetailResponse.DataDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    if (itemData.getPickStatus().endsWith("4")) {
                        Log.e(TAG1, "addListener: 拣货已经完成，不需要继续操作");
                        Toast.makeText(SortWarehouseDetailActivity.this, "拣货已经完成，不需要继续操作", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(itemData.getId(), itemData.getGoodsInfo().getContainerSerialNum());
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getData().observe(this, commonAdapter::onItemDatasChanged);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadData(relNumber);
    }


    /**
     * 跳转到出库拣货作业
     */
    private void startActivity(String id, String code) {
        Intent intent = new Intent(SortWarehouseDetailActivity.this, SortWarehouseWorkActivity.class);

        intent.putExtra("CODE", code);
        intent.putExtra("ORDER_NUMBER", orderNumber);
        intent.putExtra("REL_NUMBER", relNumber);
        intent.putExtra("REL_SOURCE", relSource);
        startActivity(intent);
    }

}

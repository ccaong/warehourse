package com.ccaong.warehousingmanager.ui.activity.sort.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.SortDetailResponse;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.databinding.ActivitySortWarehouseDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.sort.SortWareHouseActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.work.SortWarehouseWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class SortWarehouseDetailActivity extends BaseActivity<ActivitySortWarehouseDetailBinding, SortWarehouseDetailViewMode> {

    String relNumber;

//    @Override
//    protected boolean isSupportScan() {
//        return true;
//    }
//
//    @Override
//    protected boolean isSupportRfid() {
//        return true;
//    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        relNumber = intent.getStringExtra("REL_NUMBER");
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
                        return;
                    }
                    Intent intent = new Intent(SortWarehouseDetailActivity.this, SortWarehouseWorkActivity.class);
                    intent.putExtra("ID", itemData.getId());
                    intent.putExtra("CODE", itemData.getGoodsInfo().getContainerSerialNum());
                    startActivity(intent);
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


//    @Override
//    protected void scanResult(String result) {
//        if (CodeParseUtils.isCodeContainer(result)) {
//            startActivity(result);
//        }
//    }
//
//    @Override
//    protected void rfidResult(String result) {
//        startActivity(result);
//    }


    /**
     * 跳转到出库拣货作业
     */
    private void startActivity(String id, String code) {
        Intent intent = new Intent(SortWarehouseDetailActivity.this, SortWarehouseWorkActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("CODE", code);
        startActivity(intent);
    }

}

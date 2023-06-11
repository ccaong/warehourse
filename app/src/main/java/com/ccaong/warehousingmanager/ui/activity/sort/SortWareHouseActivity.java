package com.ccaong.warehousingmanager.ui.activity.sort;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.InboundListResponse;
import com.ccaong.warehousingmanager.bean.PullTaskListResponse;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.bean.TestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.databinding.ActivitySortWaerBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.save.SaveWareHouseActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.detail.SortWarehouseDetailActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.work.SortWarehouseWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * 拣货作业
 *
 * @author caocong
 * @date 2022/9/19
 */
public class SortWareHouseActivity extends BaseActivity<ActivityListBinding, SortWareHouseViewModel> {

    CommonAdapter<SortListResponse.RowsDTO.ListDTO> commonAdapter;
    private List<SortListResponse.RowsDTO.ListDTO> list = new ArrayList<>();

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
        list = (List<SortListResponse.RowsDTO.ListDTO>) intent.getSerializableExtra("LIST");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void scanResult(String result) {
        if (CodeParseUtils.isCodeContainer(result)) {
            checkContainer(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        if (!CodeParseUtils.rfidIsLocalCode(result)) {
            checkContainer(result);
        }
    }


    @Override
    protected void init() {

        actionBar.setTitle("出库分拣单列表");

        initRecyclerView();

    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<SortListResponse.RowsDTO.ListDTO>(R.layout.item_sort, BR.sortData) {
            @Override
            public void addListener(View root, SortListResponse.RowsDTO.ListDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(SortWareHouseActivity.this, SortWarehouseDetailActivity.class);
                    intent.putExtra("REL_NUMBER", itemData.getRelNumber());
                    intent.putExtra("ORDER_NUMBER", itemData.getOrderNumber());
                    intent.putExtra("REL_SOURCE", itemData.getRelSource());
                    startActivity(intent);
                    finish();
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        commonAdapter.onItemDatasChanged(list);
    }


    private void checkContainer(String code) {
        for (SortListResponse.RowsDTO.ListDTO rowsDTO : list) {
            for (SortListResponse.RowsDTO.ListDTO.RelListDTO relsDTO : rowsDTO.getRelList()) {
                if (code.equals(relsDTO.getContainerSerialNumber())) {
                    Intent intent = new Intent(SortWareHouseActivity.this, SortWarehouseWorkActivity.class);
                    intent.putExtra("CODE", code);
                    intent.putExtra("ORDER_NUMBER", rowsDTO.getOrderNumber());
                    intent.putExtra("REL_NUMBER", rowsDTO.getRelNumber());
                    intent.putExtra("REL_SOURCE", rowsDTO.getRelSource());
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        }
    }

}

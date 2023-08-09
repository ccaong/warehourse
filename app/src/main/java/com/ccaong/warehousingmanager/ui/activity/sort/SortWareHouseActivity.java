package com.ccaong.warehousingmanager.ui.activity.sort;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.ui.activity.sort.work.SortWarehouseWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

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

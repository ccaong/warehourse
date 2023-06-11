package com.ccaong.warehousingmanager.ui.activity.move.detail.done.vehicle;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityMoveVehicleDetailBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 移库点收作业
 */
public class MoveVehicleDetailActivity extends BaseActivity<ActivityMoveVehicleDetailBinding, MoveVehicleDetailViewModel> {


    CommonAdapter<SortListResponse.RowsDTO> commonAdapter;
    private List<SortListResponse.RowsDTO> list = new ArrayList<>();

    String id;
    String vehicleId;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        vehicleId = intent.getStringExtra("VEHICLE_ID");
    }

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_move_vehicle_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MoveVehicleDetailViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        initRecyclerView();


        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        mDataBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<SortListResponse.RowsDTO>(R.layout.item_sort, BR.sortData) {
            @Override
            public void addListener(View root, SortListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void loadData() {

    }


}

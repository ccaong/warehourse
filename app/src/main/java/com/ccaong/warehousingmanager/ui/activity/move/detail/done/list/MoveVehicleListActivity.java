package com.ccaong.warehousingmanager.ui.activity.move.detail.done.list;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.MoveListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityMoveVehicleListBinding;
import com.ccaong.warehousingmanager.ui.activity.move.detail.done.vehicle.MoveVehicleDetailActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * 载具移库详情
 */
public class MoveVehicleListActivity extends BaseActivity<ActivityMoveVehicleListBinding, MoveVehicleListViewModel> {

    String id;
    String no;
    String type;
    CommonAdapter<Object> adapter;


    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        no = intent.getStringExtra("NO");
        type = intent.getStringExtra("TYPE");
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    String id = result.getData().getStringExtra("ID");
                    changeData(id);
                }
            });


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_move_vehicle_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MoveVehicleListViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        actionBar.setTitle("移库单详情");
        mDataBinding.tvSubmit.setOnClickListener(view -> submit());
    }


    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);

        if (!CodeParseUtils.rfidIsLocalCode(result)){
            Intent intent = new Intent(this, MoveVehicleDetailActivity.class);
            intent.putExtra("ID", result);
            intent.putExtra("VEHICLE_ID", result);
            intentActivityResultLauncher.launch(intent);
        }
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        if (CodeParseUtils.isCodeContainer(result)) {
            Intent intent = new Intent(this, MoveVehicleDetailActivity.class);
            intent.putExtra("ID", result);
            intent.putExtra("VEHICLE_ID", result);
            intentActivityResultLauncher.launch(intent);
        }
    }


    private void initRecyclerView() {
        adapter = new CommonAdapter<Object>(R.layout.item_move_vehicle_lib_in, BR.vehicleLibIn) {
            @Override
            public void addListener(View root, Object itemData, int position) {
                super.addListener(root, itemData, position);
            }
        };

        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.rvList.setAdapter(adapter);
    }


    private void loadData() {


    }

    private void changeData(String id) {

    }

    private void submit() {

    }
}

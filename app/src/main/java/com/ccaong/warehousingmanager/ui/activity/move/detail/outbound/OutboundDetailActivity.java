package com.ccaong.warehousingmanager.ui.activity.move.detail.outbound;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.CommonResponse;
import com.ccaong.warehousingmanager.bean.MoveLibDetailResponse;
import com.ccaong.warehousingmanager.bean.MoveVehicleDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivityMoveOutDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * 移库详情
 */
public class OutboundDetailActivity extends BaseActivity<ActivityMoveOutDetailBinding, OutboundViewMode> {

    CommonAdapter<MoveLibDetailResponse.DataDTO.DatasDTO> goodsMoveLibAdapter;
    CommonAdapter<MoveLibDetailResponse.DataDTO.DatasDTO> vehicleMoveLibAdapter;
    CommonAdapter<MoveVehicleDetailResponse.DataDTO.DatasDTO> vehicleMoveLocalAdapter;

    private String id;
    private String no;
    private String type;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);

        id = intent.getStringExtra("ID");
        no = intent.getStringExtra("NO");
        type = intent.getStringExtra("TYPE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_move_out_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(OutboundViewMode.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        actionBar.setTitle("移库单详情");

        initRecyclerView();

        mDataBinding.tv11.setText(no);

        switch (type) {
            case "0":
                // 物资移库
                loadGoodsMoveLibData();
                mDataBinding.tv88.setText("物资移库");
                mDataBinding.rvList.setAdapter(goodsMoveLibAdapter);
                break;
            case "1":
                // 载具移库
                loadVehicleMoveLibData();
                mDataBinding.tv88.setText("载具移库");
                mDataBinding.rvList.setAdapter(vehicleMoveLibAdapter);

                break;
            case "2":
                mDataBinding.tv88.setText("载具移位");
                mDataBinding.rvList.setAdapter(vehicleMoveLocalAdapter);

                loadVehicleMoveLocalData();
                break;
            case "3":
                // TODO: 2023/2/15 物资移位,暂时处理这个模块
                mDataBinding.tv88.setText("物资移位");
                break;
        }


        //开始移库
        mDataBinding.tvSubmit.setOnClickListener(view -> {
            submit();
        });

    }


    private void initRecyclerView() {

        goodsMoveLibAdapter = new CommonAdapter<>(R.layout.item_move_goods_lib, BR.goodsLib);

        vehicleMoveLibAdapter = new CommonAdapter<>(R.layout.item_move_vehicle_lib, BR.vehicleLib);

        vehicleMoveLocalAdapter = new CommonAdapter<>(R.layout.item_move_vehicle_local, BR.vehicleLocal);

        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    /**
     * 获取物资移库详情
     */
    private void loadGoodsMoveLibData() {

        HttpRequest.getInstance()
                .getTransLibraryDetail(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<MoveLibDetailResponse>() {
                    @Override
                    public void success(MoveLibDetailResponse bean) {
                        if (bean.getCode() == 200) {

                            mDataBinding.tv22.setText(bean.getData().getCreateBy());
                            mDataBinding.tv33.setText(bean.getData().getCreateTime());
                            mDataBinding.tv55.setText(bean.getData().getSourceWarehouse());
                            mDataBinding.tv66.setText(bean.getData().getDestinationWarehouse());

                            goodsMoveLibAdapter.onItemDatasChanged(bean.getData().getDatas());
                        }
                    }

                });

    }

    /**
     * 获取载具移库详情
     */
    private void loadVehicleMoveLibData() {

        HttpRequest.getInstance()
                .getTransLibraryDetail(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<MoveLibDetailResponse>() {
                    @Override
                    public void success(MoveLibDetailResponse bean) {
                        if (bean.getCode() == 200) {

                            mDataBinding.tv22.setText(bean.getData().getCreateBy());
                            mDataBinding.tv33.setText(bean.getData().getCreateTime());
                            mDataBinding.tv55.setText(bean.getData().getSourceWarehouse());
                            mDataBinding.tv66.setText(bean.getData().getDestinationWarehouse());

                            vehicleMoveLibAdapter.onItemDatasChanged(bean.getData().getDatas());
                        }
                    }
                });

    }

    /**
     * 获取载具移位详情
     */
    private void loadVehicleMoveLocalData() {
        HttpRequest.getInstance()
                .getTranslocationDetail(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<MoveVehicleDetailResponse>() {
                    @Override
                    public void success(MoveVehicleDetailResponse bean) {
                        if (bean.getCode() == 200) {

                            mDataBinding.tv22.setText(bean.getData().getCreateBy());
                            mDataBinding.tv33.setText(bean.getData().getCreateTime());
                            mDataBinding.tv55.setText(bean.getData().getSourceWarehouse());
                            mDataBinding.tv66.setText(bean.getData().getDestinationWarehouse());

                            vehicleMoveLocalAdapter.onItemDatasChanged(bean.getData().getDatas());
                        }
                    }
                });
    }

    /**
     * 提交
     */
    private void submit() {

        HttpRequest.getInstance()
                .startTranslocation(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<CommonResponse>() {
                    @Override
                    public void success(CommonResponse bean) {
                        if (bean.getCode() == 200) {
                            Toast.makeText(OutboundDetailActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}

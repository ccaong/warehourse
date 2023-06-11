package com.ccaong.warehousingmanager.ui.activity.move;

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
import com.ccaong.warehousingmanager.bean.MoveListResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.move.detail.done.MoveDoneActivity;
import com.ccaong.warehousingmanager.ui.activity.move.detail.done.list.MoveVehicleListActivity;
import com.ccaong.warehousingmanager.ui.activity.move.detail.outbound.OutboundDetailActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class MoveActivity extends BaseActivity<ActivityListBinding, MoveViewModel> {

    private int page = 1;
    CommonAdapter<MoveListResponse.RowsDTO> commonAdapter;
    private List<MoveListResponse.RowsDTO> list = new ArrayList<>();


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
    protected void init() {


        actionBar.setTitle("移库任务列表");

        initRecyclerView();

        //刷新列表
        mDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            loadData();
        });

        // 加载更多
        mDataBinding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            loadData();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        loadData();
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<MoveListResponse.RowsDTO>(R.layout.item_move, BR.moveData) {
            @Override
            public void addListener(View root, MoveListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    String warehouseId = Hawk.get(Constant.STOREHOUSE_ID);
                    if (warehouseId.equals(itemData.getSourceWarehouseId())) {
                        if ("1".equals(itemData.getOrderStatus())) {
                            // 移库详情
                            Intent intent = new Intent(MoveActivity.this, OutboundDetailActivity.class);
                            intent.putExtra("ID", itemData.getOrderId());
                            intent.putExtra("NO", itemData.getOrderNumber());
                            intent.putExtra("TYPE", itemData.getOrderType());
                            startActivity(intent);
                        }
                    } else if (warehouseId.equals(itemData.getDestinationWarehouseId())) {
//                        if ("3".equals(itemData.getOrderStatus())) {
//                            if ("0".equals(itemData.getOrderType())) {
//                                // 物资移库，移入
//                                Intent intent = new Intent(MoveActivity.this, MoveDoneActivity.class);
//                                intent.putExtra("id", itemData.getOrderId());
//                                intent.putExtra("NO", itemData.getOrderNumber());
//                                intent.putExtra("TYPE", itemData.getOrderType());
//                                startActivity(intent);
//                            } else if ("1".equals(itemData.getOrderType())) {
//                                // 载具移库,移入
//                                Intent intent = new Intent(MoveActivity.this, MoveVehicleListActivity.class);
//                                intent.putExtra("id", itemData.getOrderId());
//                                intent.putExtra("NO", itemData.getOrderNumber());
//                                intent.putExtra("TYPE", itemData.getOrderMode());
//                                startActivity(intent);
//                            }
//                        }
                    }
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void loadData() {
        HttpRequest.getInstance()
                .getTransferOrder(page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<MoveListResponse>() {
                    @Override
                    public void success(MoveListResponse bean) {
                        mDataBinding.refreshLayout.finishRefresh();
                        mDataBinding.refreshLayout.finishLoadMore();
                        if (bean.getCode() == 200) {
                            if (page == 1) {
                                list = bean.getRows();
                            } else {
                                list.addAll(bean.getRows());
                            }
                            mDataBinding.refreshLayout.setNoMoreData(list.size() >= bean.getTotal());
                            commonAdapter.onItemDatasChanged(list);
                        } else {
                            Toast.makeText(MoveActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        if (list.size() == 0) {
                            mDataBinding.llNoData.setVisibility(View.VISIBLE);
                        } else {
                            mDataBinding.llNoData.setVisibility(View.GONE);
                        }
                    }
                });
    }

}

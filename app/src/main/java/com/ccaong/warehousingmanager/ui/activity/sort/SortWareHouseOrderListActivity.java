package com.ccaong.warehousingmanager.ui.activity.sort;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.orhanobut.hawk.Hawk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 拣货作业
 *
 * @author caocong
 * @date 2022/9/19
 */
public class SortWareHouseOrderListActivity extends BaseActivity<ActivityListBinding, SortWareHouseViewModel> {

    private int page = 1;
    CommonAdapter<SortListResponse.RowsDTO> commonAdapter;
    private List<SortListResponse.RowsDTO> list = new ArrayList<>();

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

        actionBar.setTitle("出库分拣单列表");

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
        commonAdapter = new CommonAdapter<SortListResponse.RowsDTO>(R.layout.item_sort_order, BR.sortDataOrder) {
            @Override
            public void addListener(View root, SortListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    // TODO: 2023/4/8 判断数量，如果itemData.getList()的数量为1，可以点击直接进入详情界面【SortWarehouseDetailActivity】
                    Intent intent = new Intent(SortWareHouseOrderListActivity.this, SortWareHouseActivity.class);
                    intent.putExtra("LIST", (Serializable) itemData.getList());
                    startActivity(intent);
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void loadData() {

        String storehosueId = Hawk.get(Constant.STOREHOUSE_ID, "");

        HttpRequest.getInstance()
                .outboundRelNotStartList(storehosueId, page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<SortListResponse>() {
                    @Override
                    public void success(SortListResponse bean) {
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
                            Toast.makeText(SortWareHouseOrderListActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
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

package com.ccaong.warehousingmanager.ui.activity.inventory;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.InboundListResponse;
import com.ccaong.warehousingmanager.bean.InventoryListResponse;
import com.ccaong.warehousingmanager.bean.TestBean;
import com.ccaong.warehousingmanager.databinding.ActivityInventorBinding;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.inventory.detail.InventoryDetailActivity;
import com.ccaong.warehousingmanager.ui.activity.save.SaveWareHouseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点
 *
 * @author eyecool
 * @date 2022/9/19
 */
public class InventoryActivity extends BaseActivity<ActivityListBinding, InventoryViewModel> {

    private int page = 1;
    private List<InventoryListResponse.RowsDTO> list = new ArrayList<>();
    private CommonAdapter<InventoryListResponse.RowsDTO> commonAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        actionBar.setTitle("盘点任务列表");

        initRecyclerView();

        loadData();

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


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<InventoryListResponse.RowsDTO>(R.layout.item_invertor, BR.inventorData) {
            @Override
            public void addListener(View root, InventoryListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(InventoryActivity.this, InventoryDetailActivity.class);
                    intent.putExtra("ID", itemData.getTaskId());
                    startActivity(intent);
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void loadData() {

        HttpRequest.getInstance()
                .getInventoryTask(page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InventoryListResponse>() {
                    @Override
                    public void success(InventoryListResponse bean) {
                        if (bean.getCode() == 200) {

                            if (page == 1) {
                                list = bean.getRows();
                            } else {
                                list.addAll(bean.getRows());
                            }
                            mDataBinding.refreshLayout.finishRefresh();
                            mDataBinding.refreshLayout.finishLoadMore();
                            mDataBinding.refreshLayout.setNoMoreData(list.size() >= bean.getTotal());

                            commonAdapter.onItemDatasChanged(list);
                        } else {
                            Toast.makeText(InventoryActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

package com.ccaong.warehousingmanager.ui.activity.put;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.InventoryListResponse;
import com.ccaong.warehousingmanager.bean.PutTaskListResponse;
import com.ccaong.warehousingmanager.bean.TestBean;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.databinding.ActivityPutShelvesBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.inventory.InventoryActivity;
import com.ccaong.warehousingmanager.ui.activity.put.detail.PutDetailActivity;
import com.ccaong.warehousingmanager.ui.activity.save.detail.SaveDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class PutShelvesActivity extends BaseActivity<ActivityListBinding, PutShelvesViewModel> {
    private int page = 1;
    private List<PutTaskListResponse.RowsDTO> list = new ArrayList<>();
    private CommonAdapter<PutTaskListResponse.RowsDTO> commonAdapter;

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

        actionBar.setTitle("上架任务列表");

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
        commonAdapter = new CommonAdapter<PutTaskListResponse.RowsDTO>(R.layout.item_put, BR.putData) {
            @Override
            public void addListener(View root, PutTaskListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(PutShelvesActivity.this, PutDetailActivity.class);
                    intent.putExtra("ID", itemData.getId());
                    intent.putExtra("PUT_TYPE", itemData.getPutType());
                    startActivity(intent);
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {

        HttpRequest.getInstance()
                .getPutTaskList(page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<PutTaskListResponse>() {
                    @Override
                    public void success(PutTaskListResponse bean) {
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

                            Toast.makeText(PutShelvesActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

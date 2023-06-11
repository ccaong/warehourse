package com.ccaong.warehousingmanager.ui.activity.lower;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.PullTaskListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class LowerShelvesOrderListActivity extends BaseActivity<ActivityListBinding, LowerShelvesViewModel> {
    private int page = 1;
    private List<PullTaskListResponse.RowsDTO> list = new ArrayList<>();
    private CommonAdapter<PullTaskListResponse.RowsDTO> commonAdapter;

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
        actionBar.setTitle("下架任务列表");
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

        commonAdapter = new CommonAdapter<PullTaskListResponse.RowsDTO>(R.layout.item_lower_order, BR.lowerDataOrder) {
            @Override
            public void addListener(View root, PullTaskListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(LowerShelvesOrderListActivity.this, LowerShelvesActivity.class);
                    intent.putExtra("LIST", (Serializable) itemData.getList());
                    startActivity(intent);
                });
            }
        };

        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {

        HttpRequest.getInstance()
                .getPullTaskList(page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<PullTaskListResponse>() {
                    @Override
                    public void success(PullTaskListResponse bean) {
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
                            Toast.makeText(LowerShelvesOrderListActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
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

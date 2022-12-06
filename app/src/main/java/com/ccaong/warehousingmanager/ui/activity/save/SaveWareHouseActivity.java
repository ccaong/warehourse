package com.ccaong.warehousingmanager.ui.activity.save;

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
import com.ccaong.warehousingmanager.databinding.ActivitySaveWaerBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.save.detail.SaveDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 入库列表
 *
 * @author eyecool
 * @date 2022/9/18
 */
public class SaveWareHouseActivity extends BaseActivity<ActivitySaveWaerBinding, SaveWareHouseViewModel> {

    private static final String TAG = SaveWareHouseActivity.class.getSimpleName();
    private int page = 1;
    private List<InboundListResponse.RowsDTO> list = new ArrayList<>();
    private CommonAdapter<InboundListResponse.RowsDTO> commonAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_save_waer;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        actionBar.setTitle("入库单列表");
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
        commonAdapter = new CommonAdapter<InboundListResponse.RowsDTO>(R.layout.item_warehousing, BR.warehouse) {
            @Override
            public void addListener(View root, InboundListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(SaveWareHouseActivity.this, SaveDetailActivity.class);
                    intent.putExtra("ID", itemData.getOrderId());
                    startActivity(intent);
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {

        HttpRequest.getInstance()
                .getInboundOrder(page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InboundListResponse>() {
                    @Override
                    public void success(InboundListResponse bean) {
                        Log.e(TAG, bean.getMsg());
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
                            Toast.makeText(SaveWareHouseActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

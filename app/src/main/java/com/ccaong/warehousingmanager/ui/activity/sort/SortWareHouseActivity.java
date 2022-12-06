package com.ccaong.warehousingmanager.ui.activity.sort;

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
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.bean.TestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.databinding.ActivitySortWaerBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.save.SaveWareHouseActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.detail.SortWarehouseDetailActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.work.SortWarehouseWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * 拣货作业
 *
 * @author eyecool
 * @date 2022/9/19
 */
public class SortWareHouseActivity extends BaseActivity<ActivityListBinding, SortWareHouseViewModel> {

    private int page = 1;
    CommonAdapter<SortListResponse.RowsDTO> commonAdapter;
    private List<SortListResponse.RowsDTO> list = new ArrayList<>();

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
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
        checkContainer(result);
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
        commonAdapter = new CommonAdapter<SortListResponse.RowsDTO>(R.layout.item_sort, BR.sortData) {
            @Override
            public void addListener(View root, SortListResponse.RowsDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(SortWareHouseActivity.this, SortWarehouseDetailActivity.class);
                    intent.putExtra("REL_NUMBER", itemData.getRelNumber());
                    startActivity(intent);
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void checkContainer(String code) {
        for (SortListResponse.RowsDTO rowsDTO : list) {
            for (SortListResponse.RowsDTO.RelsDTO relsDTO : rowsDTO.getRels()) {
                if (code.equals(relsDTO.getGoodsInfo().getContainerSerialNum())) {
                    Intent intent = new Intent(SortWareHouseActivity.this, SortWarehouseWorkActivity.class);
                    intent.putExtra("ID", relsDTO.getId());
                    intent.putExtra("CODE", code);
                    startActivity(intent);
                }
            }
        }
    }


    private void loadData() {

        String storehosueId = Hawk.get(Constant.STOREHOUSE_ID, "");

        HttpRequest.getInstance()
                .outboundRelNotStartList(storehosueId, page, 10)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<SortListResponse>() {
                    @Override
                    public void success(SortListResponse bean) {
                        Log.e(TAG1, bean.getMsg());
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
                            Toast.makeText(SortWareHouseActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

}

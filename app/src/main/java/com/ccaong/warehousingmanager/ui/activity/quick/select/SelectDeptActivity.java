package com.ccaong.warehousingmanager.ui.activity.quick.select;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.DeptListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eyecool
 * @date 2022/10/17
 */
public class SelectDeptActivity extends BaseActivity<ActivityListBinding, SelectDeptViewModel> {

    CommonAdapter<DeptListResponse.DataDTO> adapter;
    List<DeptListResponse.DataDTO> list;

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
        list = new ArrayList<>();

        mDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> getCustomerList());
        initRecyclerView();
        getCustomerList();
    }


    private void initRecyclerView() {
        adapter = new CommonAdapter<DeptListResponse.DataDTO>(R.layout.item_dept, BR.dept) {
            @Override
            public void addListener(View root, DeptListResponse.DataDTO itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.item).setOnClickListener(view -> {
                    Intent intent = new Intent();
                    intent.putExtra("NAME", itemData.getName());
                    intent.putExtra("ID", itemData.getId());
                    setResult(RESULT_OK, intent);
                    finish();
                });
            }
        };
        mDataBinding.rvList.setAdapter(adapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    /**
     * 获取物品类型
     */
    public void getCustomerList() {
        HttpRequest.getInstance()
                .getCustomerList()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<DeptListResponse>() {
                    @Override
                    public void success(DeptListResponse bean) {
                        if (bean.getCode() == 200) {
                            list = bean.getData();
                            adapter.onItemDatasChanged(list);
                        }
                    }

                });
    }
}

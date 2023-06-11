package com.ccaong.warehousingmanager.ui.activity.quick.select;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.ManufacturerResponse;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/10/17
 */
public class SelectFactureActivity extends BaseActivity<ActivityListBinding, BaseViewModel> {

    CommonAdapter<ManufacturerResponse.DataDTO> adapter;
    List<ManufacturerResponse.DataDTO> list;

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
        adapter = new CommonAdapter<ManufacturerResponse.DataDTO>(R.layout.item_facture, BR.facture) {
            @Override
            public void addListener(View root, ManufacturerResponse.DataDTO itemData, int position) {
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
     * 获取生产厂家
     */
    public void getCustomerList() {
        HttpRequest.getInstance().getManufacturerList().compose(HttpFactory.schedulers()).subscribe(new HttpDisposable<ManufacturerResponse>() {
            @Override
            public void success(ManufacturerResponse bean) {
                if (bean.getCode() == 200) {
                    list = bean.getData();
                    adapter.onItemDatasChanged(list);
                }
            }
        });
    }
}

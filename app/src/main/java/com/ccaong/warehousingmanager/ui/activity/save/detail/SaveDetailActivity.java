package com.ccaong.warehousingmanager.ui.activity.save.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivitySaveDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.save.group.GroupProductActivity;

import java.util.Objects;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class SaveDetailActivity extends BaseActivity<ActivitySaveDetailBinding, SaveDetailViewModel> {
    String id;
    CommonAdapter<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> commonAdapter;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_save_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SaveDetailViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }


    @Override
    protected void init() {
        actionBar.setTitle("入库单详情");
        initRecyclerView();

        mViewModel.getData().observe(this, dataDTO -> {
                    if (dataDTO != null) {
                        if (dataDTO.getOrderStatus().equals("1")) {
                            mDataBinding.tvSubmit.setVisibility(View.VISIBLE);
                            mDataBinding.tvSubmit.setText("点收组盘");
                        } else {
                            mDataBinding.tvSubmit.setVisibility(View.GONE);
                        }
                    }
                    if (dataDTO != null && dataDTO.getGoodsTypeDetail() != null) {
                        commonAdapter.onItemDatasChanged(dataDTO.getGoodsTypeDetail());
                    }
                }
        );

        mDataBinding.tvSubmit.setOnClickListener(view -> {
            if (mViewModel.getData().getValue() != null) {
                Intent intent = new Intent(SaveDetailActivity.this, GroupProductActivity.class);
                if (mViewModel.getData().getValue() != null) {
                    intent.putExtra("ID", id);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadData(id);
    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO>(R.layout.item_warehouse_detail, BR.productData) {
            @Override
            public void addListener(View root, InboundDetailResponse.DataDTO.GoodsTypeDetailDTO itemData, int position) {
                super.addListener(root, itemData, position);
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

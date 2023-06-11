package com.ccaong.warehousingmanager.ui.activity.lower;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.os.Bundle;
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
import com.ccaong.warehousingmanager.ui.activity.lower.detail.LowerDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class LowerShelvesActivity extends BaseActivity<ActivityListBinding, LowerShelvesViewModel> {
    private List<PullTaskListResponse.RowsDTO.ListDTO> list = new ArrayList<>();
    private CommonAdapter<PullTaskListResponse.RowsDTO.ListDTO> commonAdapter;

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
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        list = (List<PullTaskListResponse.RowsDTO.ListDTO>) intent.getSerializableExtra("LIST");
    }

    @Override
    protected void init() {
        actionBar.setTitle("下架任务列表");
        initRecyclerView();
    }


    private void initRecyclerView() {

        commonAdapter = new CommonAdapter<PullTaskListResponse.RowsDTO.ListDTO>(R.layout.item_lower, BR.lowerData) {
            @Override
            public void addListener(View root, PullTaskListResponse.RowsDTO.ListDTO itemData, int position) {
                super.addListener(root, itemData, position);

                root.setOnClickListener(view -> {
                    Intent intent = new Intent(LowerShelvesActivity.this, LowerDetailActivity.class);
                    intent.putExtra("ID", itemData.getId());
                    startActivity(intent);
                });
            }
        };

        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        commonAdapter.onItemDatasChanged(list);
    }
}

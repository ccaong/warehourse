package com.ccaong.warehousingmanager.ui.activity.put;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.PutTaskListResponse;
import com.ccaong.warehousingmanager.databinding.ActivityListBinding;
import com.ccaong.warehousingmanager.ui.activity.put.detail.PutDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class PutShelvesActivity extends BaseActivity<ActivityListBinding, PutShelvesViewModel> {

    private List<PutTaskListResponse.RowsDTO.ListDTO> list = new ArrayList<>();
    private CommonAdapter<PutTaskListResponse.RowsDTO.ListDTO> commonAdapter;


    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        list = (List<PutTaskListResponse.RowsDTO.ListDTO>) intent.getSerializableExtra("LIST");
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
    protected void init() {
        actionBar.setTitle("上架任务列表");
        initRecyclerView();
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<PutTaskListResponse.RowsDTO.ListDTO>(R.layout.item_put, BR.putData) {
            @Override
            public void addListener(View root, PutTaskListResponse.RowsDTO.ListDTO itemData, int position) {
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

        commonAdapter.onItemDatasChanged(list);
    }
}

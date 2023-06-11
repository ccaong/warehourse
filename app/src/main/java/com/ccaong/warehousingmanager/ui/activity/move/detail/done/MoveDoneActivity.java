package com.ccaong.warehousingmanager.ui.activity.move.detail.done;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.databinding.ActivityMoveDoneDetailBinding;
import com.ccaong.warehousingmanager.ui.activity.move.detail.done.collect.MoveCollectActivity;

public class MoveDoneActivity extends BaseActivity<ActivityMoveDoneDetailBinding, MoveDoneViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_move_done_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MoveDoneViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);

    }

    @Override
    protected void init() {
        actionBar.setTitle("移库单详情");
        mViewModel.loadDetail();

        //开始点收
        mDataBinding.tvSubmit.setOnClickListener(view -> {
            Intent intent = new Intent(MoveDoneActivity.this, MoveCollectActivity.class);
            startActivity(intent);
        });
    }
}

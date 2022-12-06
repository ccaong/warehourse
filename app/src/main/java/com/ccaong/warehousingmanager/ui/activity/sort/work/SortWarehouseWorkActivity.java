package com.ccaong.warehousingmanager.ui.activity.sort.work;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.SortWorkResponse;
import com.ccaong.warehousingmanager.databinding.ActivitySortWarehouserWorkBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * @author eyecool
 * @date 2022/9/21
 */
public class SortWarehouseWorkActivity extends BaseActivity<ActivitySortWarehouserWorkBinding, SortWarehouseWorkViewModel> {

    public static final String TAG = SortWarehouseWorkActivity.class.getSimpleName();
    String id;
    String code;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        code = intent.getStringExtra("CODE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sort_warehouser_work;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SortWarehouseWorkViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        actionBar.setTitle("出库拣货作业");
        mDataBinding.zj.setText(code);

        mDataBinding.tvSubmit.setOnClickListener(view -> submit());

        initRecyclerView();
    }


    private void initRecyclerView() {
        CommonAdapter<SortWorkResponse.DataDTO> commonAdapter =
                new CommonAdapter<SortWorkResponse.DataDTO>(R.layout.item_sort_work, BR.sortWork) {
                };

        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getData().observe(this, dataDTOS -> commonAdapter.onItemDatasChanged(dataDTOS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadData(code);
    }


    private void submit() {
        HttpRequest.getInstance()
                .finishPick(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        if (bean.getCode() == 200) {
                            Toast.makeText(SortWarehouseWorkActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, id + "错误" + bean.getMsg());
                            Toast.makeText(SortWarehouseWorkActivity.this, "操作失败:" + bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d(TAG, "onError: " + e.toString());
                        Log.d(TAG, "onError: " + id);
                    }
                });
    }
}

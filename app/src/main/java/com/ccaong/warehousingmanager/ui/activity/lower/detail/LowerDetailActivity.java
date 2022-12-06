package com.ccaong.warehousingmanager.ui.activity.lower.detail;

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
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.PullTaskDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivityLowerDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * 下架任务详情
 *
 * @author eyecool
 * @date 2022/9/21
 */
public class LowerDetailActivity extends BaseActivity<ActivityLowerDetailBinding, LowerDetailViewModel> {
    public static final String TAG = LowerDetailActivity.class.getSimpleName();

    String id;
    CommonAdapter<PullTaskDetailResponse.DataDTO.ContainerCodesDTO> commonAdapter;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lower_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(LowerDetailViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        actionBar.setTitle("下架任务详情");
        initRecyclerView();

        mViewModel.loadData(id);
//        getType();

        mViewModel.getData().observe(this, dataDTO -> {
                    if (dataDTO != null && dataDTO.getContainerCodes() != null) {
                        commonAdapter.onItemDatasChanged(dataDTO.getContainerCodes());
                    }
                }
        );

        mDataBinding.tvSubmit.setOnClickListener(view -> submit());
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<PullTaskDetailResponse.DataDTO.ContainerCodesDTO>(R.layout.item_lower_detail, BR.lowerDetail) {
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getType() {
        HttpRequest.getInstance()
                .getAreaType(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<AreaTypeResponse>() {
                    @Override
                    public void success(AreaTypeResponse bean) {
                        Log.e(TAG, "下架区域类型" + bean.getMsg());
                        if (bean.getCode() == 200) {
                            if ("0".equals(bean.getData()) || "1".equals(bean.getData())) {
                                // 智能区域
                                mDataBinding.tvSubmit.setVisibility(View.VISIBLE);
                            } else {
                                // 非智能区域，暂时不做处理
                                mDataBinding.tvSubmit.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "获取区域类型错误" + e.toString());
                    }
                });

    }

    /**
     * 提交下架操作
     */
    private void submit() {

        HttpRequest.getInstance()
                .autoUndercarriage(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        Log.e(TAG, "下架操作完成" + bean.getMsg());

                        if (bean.getCode() == 200) {
                            Toast.makeText(LowerDetailActivity.this, "下架成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(LowerDetailActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "下架出错" + e.toString());

                    }
                });
    }
}

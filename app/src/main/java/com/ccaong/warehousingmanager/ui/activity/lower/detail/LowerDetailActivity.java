package com.ccaong.warehousingmanager.ui.activity.lower.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.PullTaskDetailResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityLowerDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.lower.manual.LowerManualActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

/**
 * 下架任务详情
 *
 * @author caocong
 * @date 2022/9/21
 */
public class LowerDetailActivity extends BaseActivity<ActivityLowerDetailBinding, LowerDetailViewModel> {
    public static final String TAG = LowerDetailActivity.class.getSimpleName();

    String id;
    CommonAdapter<PullTaskDetailResponse.DataDTO.ResultDTO> commonAdapter;

    boolean smartArea = true;

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected boolean isSupportScan() {
        return true;
    }

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


    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    String zjId = result.getData().getStringExtra("ZJ_ID");
                    String wzId = result.getData().getStringExtra("WZ_ID");
                    bindLocation(zjId, wzId);
                }
            });


    Map<String, String> manualMap = new HashMap();

    /**
     * 绑定载具和位置
     *
     * @param zjId 载具id
     * @param wzId 位置id
     */
    private void bindLocation(String zjId, String wzId) {
        Log.e(TAG, "载具:" + zjId + "位置:" + wzId);

        for (PullTaskDetailResponse.DataDTO.ResultDTO dto : mViewModel.getData().getValue().getResult()) {
            if (dto.getLocationCode().equals(wzId)) {
                if (dto.getList().size() > 0) {
                    if (zjId.equals(dto.getList().get(0).getContainerCode())) {

                        for (PullTaskDetailResponse.DataDTO.ResultDTO.ListDTO listDTO : dto.getList()) {
                            listDTO.setContainerCode(zjId);
                            listDTO.setStatus("2");
                        }
                        manualMap.put(zjId, wzId);
                        mViewModel.data.postValue(mViewModel.getData().getValue());
                    } else {
                        Toast.makeText(this, "载具码不一致！请检查后重试！", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        }
        Toast.makeText(this, "扫描的位置不在本任务内，请重新扫码！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        if (CodeParseUtils.isLocalCode(result)) {

            for (PullTaskDetailResponse.DataDTO.ResultDTO dto : mViewModel.getData().getValue().getResult()) {
                if (dto.getLocationCode().equals(result)) {
                    Intent intent = new Intent(LowerDetailActivity.this, LowerManualActivity.class);
                    intent.putExtra("LOCATION_CODE", result);
                    resultLauncher.launch(intent);
                    return;
                }
            }
            Toast.makeText(this, "扫描的位置不在本任务内，请重新扫码！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);
        if (CodeParseUtils.rfidIsLocalCode(result)) {
            result = CodeParseUtils.getRfidLocalCode(result);

            for (PullTaskDetailResponse.DataDTO.ResultDTO dto : mViewModel.getData().getValue().getResult()) {
                if (dto.getLocationCode().equals(result)) {
                    Intent intent = new Intent(LowerDetailActivity.this, LowerManualActivity.class);
                    intent.putExtra("LOCATION_CODE", result);
                    resultLauncher.launch(intent);
                    return;
                }
            }
            Toast.makeText(this, "扫描的位置不在本任务内，请重新扫码！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void init() {

        actionBar.setTitle("下架任务详情");
        initRecyclerView();

        mViewModel.loadData(id);
        getType();

        mViewModel.getData().observe(this, dataDTO -> {
                    if (dataDTO.getStatus().equals("0")) {
                        mDataBinding.tvSubmit.setVisibility(View.VISIBLE);
                    } else {
                        mDataBinding.tvSubmit.setVisibility(View.GONE);
                    }
                    if (dataDTO.getResult() != null) {
                        commonAdapter.onItemDatasChanged(dataDTO.getResult());
                    }
                }
        );

        mDataBinding.tvSubmit.setOnClickListener(view -> {

            if (smartArea) {
                submit();
            } else {
                manualWarehouse();
            }
        });
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<PullTaskDetailResponse.DataDTO.ResultDTO>(R.layout.item_lower_detail, BR.lowerDetail) {
            @Override
            public void addListener(View root, PullTaskDetailResponse.DataDTO.ResultDTO itemData, int position) {
                super.addListener(root, itemData, position);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!smartArea) {
                            Intent intent = new Intent(LowerDetailActivity.this, LowerManualActivity.class);
                            intent.putExtra("LOCATION_CODE", itemData.getLocationCode());
                            resultLauncher.launch(intent);
                        }
                    }
                });
            }
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
                            if ("0".equals(bean.getData())) {
                                smartArea = true;
                            } else {
                                smartArea = false;
                            }
                        } else {
                            Toast.makeText(LowerDetailActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
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


    /**
     * 手动下架操作
     */
    public void manualWarehouse() {

        if (manualMap.size() != mViewModel.data.getValue().getResult().size()) {
            Toast.makeText(this, "还有储位码未绑定", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new HashMap();
        map.put("taskId", id);
        map.put("storehouseId", Hawk.get(Constant.STOREHOUSE_ID));
        map.put("datas", manualMap);

        HttpRequest.getInstance()
                .manualUndercarriage(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse resultResponse) {
                        Log.e(TAG, "手动下架结果" + resultResponse.getCode() + resultResponse.getMsg());
                        if (resultResponse.getCode() == 200) {
                            Toast.makeText(LowerDetailActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(LowerDetailActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "手动下架失败" + e.toString());
                    }
                });
    }

}

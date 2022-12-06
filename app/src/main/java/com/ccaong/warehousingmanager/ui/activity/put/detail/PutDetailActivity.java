package com.ccaong.warehousingmanager.ui.activity.put.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.PutTaskDetailResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityPutDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.put.manual.PutManualActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;
import com.uhf.api.cls.Reader;

import java.util.HashMap;
import java.util.Map;

/**
 * 上架任务详情
 *
 * @author eyecool
 * @date 2022/9/21
 */
public class PutDetailActivity extends BaseActivity<ActivityPutDetailBinding, PutDetailViewModel> {

    public static final String TAG = PutDetailActivity.class.getSimpleName();
    String id;
    String putType;
    CommonAdapter<PutTaskDetailResponse.DataDTO.ContainerCodesDTO> commonAdapter;

    boolean smartArea = true;
    Map<String, String> manualMap = new HashMap();

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        putType = intent.getStringExtra("PUT_TYPE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_put_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(PutDetailViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    String zjId = result.getData().getStringExtra("ZJ_ID");
                    String wzId = result.getData().getStringExtra("WZ_ID");
                    bindLocation(zjId, wzId);
                }
            });

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.scan) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void scanResult(String result) {
        if (CodeParseUtils.isCodeContainer(result)) {
            queryContainerCode(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        queryContainerCode(result);
    }


    @Override
    protected void init() {
        actionBar.setTitle("上架任务详情");

        initRecyclerView();
        mViewModel.loadData(id);

        if ("0".equals(putType)) {
            // 智能区域，自动上架
            smartArea = true;
            mDataBinding.tvSubmit.setEnabled(true);
        } else if ("1".equals(putType)) {
            // 智能区域，自动上架
            smartArea = false;
            mDataBinding.tvSubmit.setEnabled(true);
        } else {
            mDataBinding.tvSubmit.setEnabled(false);
            Toast.makeText(this, "无法获取上架区域类型！", Toast.LENGTH_SHORT).show();
        }

        mViewModel.getData().observe(this, dataDTO -> {
                    if (dataDTO != null && dataDTO.getContainerCodes() != null) {
                        commonAdapter.onItemDatasChanged(dataDTO.getContainerCodes());
                    }
                }
        );
        mDataBinding.tvSubmit.setOnClickListener(view -> submit());
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<PutTaskDetailResponse.DataDTO.ContainerCodesDTO>
                (R.layout.item_put_detail, BR.putDetail) {
            @Override
            public void addListener(View root, PutTaskDetailResponse.DataDTO.ContainerCodesDTO itemData, int position) {
                super.addListener(root, itemData, position);
                root.setOnClickListener(view -> {
                    if (!smartArea) {
                        Intent intent = new Intent(PutDetailActivity.this, PutManualActivity.class);
                        intent.putExtra("CONTAINER_CODE", itemData.getContainerCode());
                        intentActivityResultLauncher.launch(intent);
                    }
                });

            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void submit() {
        if (smartArea) {
            autoSubmit();
        } else {
            manualWarehouse();
        }
    }

    /**
     * 提交自动上架操作
     */
    private void autoSubmit() {
        // TODO: 2022/10/10 实际只有部分状态下需要自动上架操作，暂时不做判断
        HttpRequest.getInstance()
                .autoShelving(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        Log.e(TAG, "上架完成" + bean.getCode() + bean.getMsg());
                        if (bean.getCode() == 200) {
                            Toast.makeText(PutDetailActivity.this, "上架成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PutDetailActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "上架失败" + e.toString());
                    }
                });
    }


    /**
     * 绑定载具和位置
     *
     * @param zjId 载具id
     * @param wzId 位置id
     */
    private void bindLocation(String zjId, String wzId) {
        Log.e(TAG, "载具:" + zjId + "位置:" + wzId);

        for (PutTaskDetailResponse.DataDTO.ContainerCodesDTO dto : mViewModel.getData().getValue().getContainerCodes()) {
            if (dto.getContainerCode().equals(zjId)) {
                dto.setLocation(wzId);
                manualMap.put(zjId, wzId);
                mViewModel.data.postValue(mViewModel.getData().getValue());
                return;
            }
        }

        Toast.makeText(this, "扫描的载具不在本任务内，请重新扫码！", Toast.LENGTH_SHORT).show();
    }

    public void manualWarehouse() {

        if (manualMap.size() != mViewModel.data.getValue().getContainerCodes().size()) {
            Toast.makeText(this, "还有载具码未绑定位置", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new HashMap();
        map.put("taskId", id);
        map.put("storehouseId", Hawk.get(Constant.STOREHOUSE_ID));
        map.put("datas", manualMap);

        HttpRequest.getInstance()
                .manualWarehouse(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse resultResponse) {
                        Log.e(TAG, "手动上架结果" + resultResponse.getCode() + resultResponse.getMsg());
                        if (resultResponse.getCode() == 200) {
                            Toast.makeText(PutDetailActivity.this, "上架成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PutDetailActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "手动上架失败" + e.toString());
                    }
                });
    }


    /**
     * 查询扫描到的载具码是否存在
     *
     * @param code
     */
    private void queryContainerCode(String code) {

        if (mViewModel.getData().getValue() == null) {
            return;
        }
        for (PutTaskDetailResponse.DataDTO.ContainerCodesDTO data : mViewModel.getData().getValue().getContainerCodes()) {
            if (data.getContainerCode().equals(code)) {
                Intent intent = new Intent(PutDetailActivity.this, PutManualActivity.class);
                intent.putExtra("CONTAINER_CODE", code);
                intentActivityResultLauncher.launch(intent);
                return;
            }
        }
    }
}





package com.ccaong.warehousingmanager.ui.activity.inventory.detail;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.ArrayMap;
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
import com.ccaong.warehousingmanager.bean.InventoryDetailResponse;
import com.ccaong.warehousingmanager.bean.ResultResponse;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityInventoryDetailBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.inventory.work.InventoryWorkActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class InventoryDetailActivity extends BaseActivity<ActivityInventoryDetailBinding, InventoryDetailViewModel> {

    private static final String TAG = InventoryDetailActivity.class.getSimpleName();
    String id;
    CommonAdapter<InventoryDetailResponse.DataDTO.ListDTO> commonAdapter;

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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_inventory_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(InventoryDetailViewModel.class);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.scan) {
            startScanRFID();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void init() {
        actionBar.setTitle("盘点任务详情");
        mDataBinding.tvId.setText(id);
        mDataBinding.tvSource.setText("盘点单");
        initRecyclerView();
        mViewModel.loadData(id);

        mViewModel.getData().observe(this, dataDTO -> {
                    if (dataDTO != null && dataDTO.getList() != null) {
                        commonAdapter.onItemDatasChanged(dataDTO.getList());
                    }
                }
        );

        mDataBinding.tvSubmit.setOnClickListener(view -> submit());
    }

    @Override
    protected void scanResult(String result) {
        if (CodeParseUtils.isCodeContainer(result)) {
            scan(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        scan(result);
    }


    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<InventoryDetailResponse.DataDTO.ListDTO>(R.layout.item_inventor_detail, BR.inventorDetail) {
            @Override
            public void addListener(View root, InventoryDetailResponse.DataDTO.ListDTO itemData, int position) {
                super.addListener(root, itemData, position);
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    String zjId = "";

    Map<String, InventoryDetailResponse.DataDTO.ListDTO> resultMap = new ArrayMap<>();

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        //此处是跳转的result回调方法
        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
            //某载具盘点完成，修改其状态
            changeStatus();
        }
    });

    private void scan(String code) {

        for (InventoryDetailResponse.DataDTO.ListDTO data : mViewModel.getData().getValue().getList()) {
            if (code.equals(data.getContainerCode())) {
                zjId = code;
                Intent intent = new Intent(this, InventoryWorkActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("C_CODE", code);
                intentActivityResultLauncher.launch(intent);
                return;
            }
        }
        showWarningDialog("扫描的载具不在任务单内！");
    }

    private void changeStatus() {
        // 保存载具盘点后的数据
        InventoryDetailResponse.DataDTO.ListDTO data = Hawk.get(Constant.PANDIAN_ZAIJU_DATA);
        data.setStatus("1");
        resultMap.put(zjId, data);

        for (InventoryDetailResponse.DataDTO.ListDTO listDto : mViewModel.getData().getValue().getList()) {
            if (listDto.getContainerCode().equals(data.getContainerCode())) {
                listDto.setStatus("1");
            }
        }
        mViewModel.data.postValue(mViewModel.getData().getValue());
    }

    private void submit() {
        // 判断所有载具的状态
        for (InventoryDetailResponse.DataDTO.ListDTO data : mViewModel.getData().getValue().getList()) {
            if ("0".equals(data.getStatus())) {
                showWarningDialog("任务内还有载具没有盘点！");
                return;
            }
        }
        Log.e(TAG, "提交");
        submitInventoryResult();
    }


    /**
     * 警告弹窗
     */
    private void showWarningDialog(String msg) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(InventoryDetailActivity.this);
        normalDialog.setTitle("警告！");
        normalDialog.setMessage(msg);
        normalDialog.setPositiveButton("确定",
                (dialog, which) -> {
                });

        // 显示
        normalDialog.show();
    }

    public void submitInventoryResult() {
        List list = new ArrayList();
        for (InventoryDetailResponse.DataDTO.ListDTO entry : resultMap.values()) {
            for (InventoryDetailResponse.DataDTO.ListDTO.ListDTOProduct product : entry.getList()) {
                Map mapResult = new ArrayMap();
                mapResult.put("id", product.getId());
                mapResult.put("inventoryAmount", Integer.valueOf(product.getPandianNum()));
                list.add(mapResult);
            }
        }

        Map map = new HashMap();
        map.put("taskId", mViewModel.getData().getValue().getTaskId());
        map.put("results", list);

        Gson gson = new Gson();
        gson.toJson(map);
        Log.e(TAG, "请求参数：" + gson.toJson(map));

        HttpRequest.getInstance()
                .submitInventoryResult(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse) {
                        if (resultResponse.getCode().equals(200)) {
                            finish();
                        } else {
                            Toast.makeText(InventoryDetailActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

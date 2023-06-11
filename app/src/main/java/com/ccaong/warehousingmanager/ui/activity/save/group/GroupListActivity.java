package com.ccaong.warehousingmanager.ui.activity.save.group;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.Activity;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.CommonResponse;
import com.ccaong.warehousingmanager.bean.ContainerStatusResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.GroupGoodBean;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.bean.SpinnerTestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityGroupListBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.ccaong.warehousingmanager.util.StringUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupListActivity extends BaseActivity<ActivityGroupListBinding, GroupProductViewMode> {

    private String id;
    private String putType;
    private CommonAdapter<GroupGoodBean> commonAdapter;

    private Map<String, List<GroupGoodBean>> dataMap = new ArrayMap();
    private List<GroupGoodBean> showList = new ArrayList<>();

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_group_list;
    }

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(GroupProductViewMode.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        actionBar.setTitle("点收组盘");

        mViewModel.loadData(id);
        mDataBinding.tvSubmit.setOnClickListener(view -> dataVerification());

        initRecyclerView();

        initSp();

        // 判断上架类型 如果有类型，就直接选择，然后禁止手动选择
        putType = Hawk.get(Constant.STOREHOUSE_TYPE);
        switch (putType) {
            case "-1":
            case "2":
                // 需要手动选择
                mDataBinding.spPutType.setEnabled(true);
                break;
            case "0":
                // 自动
                mDataBinding.spPutType.setSelection(0);
                mDataBinding.spPutType.setEnabled(false);
                putType = "0";
                break;
            case "1":
                // 手动
                mDataBinding.spPutType.setSelection(1);
                mDataBinding.spPutType.setEnabled(false);
                putType = "1";
                break;
        }
    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<GroupGoodBean>(R.layout.item_group_product, BR.groupProduct) {
            @Override
            public void addListener(View root, GroupGoodBean itemData, int position) {
                super.addListener(root, itemData, position);

            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        if (showList == null || showList.isEmpty()) {
            mDataBinding.llNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initSp() {
        List<SpinnerTestBean> list = new ArrayList<>();
        list.add(new SpinnerTestBean("0", "自动上架"));
        list.add(new SpinnerTestBean("1", "手动上架"));

        ArrayAdapter<SpinnerTestBean> productAdapter = new ArrayAdapter<>(this, R.
                layout.item_spinner, list);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spPutType.setAdapter(productAdapter);
        mDataBinding.spPutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTestBean data = list.get(i);
                putType = data.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 判断上架类型 如果有类型，就直接选择，然后禁止手动选择
        putType = Hawk.get(Constant.STOREHOUSE_TYPE);
        switch (putType) {
            case "-1":
            case "2":
                // 需要手动选择
                mDataBinding.spPutType.setEnabled(true);
                break;
            case "0":
                // 自动
                mDataBinding.spPutType.setSelection(0);
                mDataBinding.spPutType.setEnabled(false);
                break;
            case "1":
                // 手动
                mDataBinding.spPutType.setSelection(1);
                mDataBinding.spPutType.setEnabled(false);
                break;
        }
    }

    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    // 获取载具，获取已经组盘完成的数据

                    String zjId = result.getData().getStringExtra("CONTAINER");
                    List<GroupGoodBean> tempList = Hawk.get("TEMP");
                    Hawk.delete("TEMP");

                    dataMap.put(zjId, tempList);
                    updateShowList(zjId);
                }
            });

    /**
     * 更新数据
     *
     * @param id   载具id
     * @param list 组盘完成的list
     */
    private void updateData(String id, List<GroupGoodBean> list) {

        for (Map.Entry<String, List<GroupGoodBean>> entry : dataMap.entrySet()) {
            // 先判断同载具下是否有数据
            if (entry.getKey().equals(id)) {
                // 需要再判断是否存在同物品
                for (GroupGoodBean groupGoodBean : list) {
                    boolean isReceived = false;
                    // 遍历刚刚点收的物品，查看已点收的列表中，是否存在该物品
                    for (GroupGoodBean receivedBean : entry.getValue()) {
                        if (groupGoodBean.getMaterialCode().equals(receivedBean.getMaterialCode())) {
                            // 同载具下有同物品，数量相加
                            receivedBean.setReceivedAmount(receivedBean.getReceivedAmount() + groupGoodBean.getReceivedAmount());
                            isReceived = true;
                            break;
                        }
                    }
                    if (!isReceived) {
                        // 同载具下，没有点收过该物品，在载具的list中添加该物品
                        List<GroupGoodBean> value = entry.getValue();
                        value.add(groupGoodBean);
                    }

                    updateShowList(id);
                }
                return;
            }
        }
        dataMap.put(id, list);
        updateShowList(id);
    }

    /**
     * 更新数据展示列表
     */
    private void updateShowList(String id) {
        showList.clear();

        for (Map.Entry<String, List<GroupGoodBean>> entry : dataMap.entrySet()) {
            if (entry.getKey().equals(id)) {
                showList.addAll(entry.getValue());
            }
        }

        for (Map.Entry<String, List<GroupGoodBean>> entry : dataMap.entrySet()) {
            if (!entry.getKey().equals(id)) {
                showList.addAll(entry.getValue());
            }
        }
        commonAdapter.onItemDatasChanged(showList);

        if (showList == null || showList.isEmpty()) {
            mDataBinding.llNoData.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.llNoData.setVisibility(View.GONE);
        }
    }

    @Override
    protected void scanResult(String result) {
        if (CodeParseUtils.isCodeContainer(result)) {
            checkContainerInTask(result);
        } else {
            Toast.makeText(this, "请扫描载具码", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void rfidResult(String result) {
        if (!CodeParseUtils.rfidIsLocalCode(result)) {
            checkContainerInTask(result);
        } else {
            Toast.makeText(this, "请扫描载具码", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断容器是否存在
     *
     * @param container
     */
    public void checkContainerInTask(String container) {

        HttpRequest.getInstance()
                .checkContainerCode(container)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ContainerStatusResponse>() {
                    @Override
                    public void success(ContainerStatusResponse bean) {
                        Log.e(TAG1, "容器是否存在：" + bean.getMsg() + bean.getCode());
                        if (bean.getCode() == 200) {
                            if (bean.getData().getExist().equals("0")) {
                                String msg = "扫描的容器:" + container + "在系统中不存在，是否将该容器添加到系统中？";
                                showInfoDialog("提示", msg, (dialogInterface, i) -> addContainer(container));
                            } else if (bean.getData().getUsed().equals("1")) {
                                Toast.makeText(GroupListActivity.this, "该容器已被占用！", Toast.LENGTH_SHORT).show();
                            } else {
                                List<GroupGoodBean> list = dataMap.get(container);
                                Hawk.put("TEMP", list);

                                Intent intent = new Intent(GroupListActivity.this, GroupProductActivity.class);
                                intent.putExtra("CONTAINER", container);
                                intent.putExtra("ID", id);
                                resultLauncher.launch(intent);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG1, "容器是否存在" + e.toString());
                    }
                });
    }

    /**
     * 新增容器
     *
     * @param container 容器id
     */
    public void addContainer(String container) {
        String typeId = "0";
        Map<String, String> map = new HashMap();
        map.put("barCode", container);
        map.put("typeId", typeId);

        HttpRequest.getInstance()
                .addContainer(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        Log.e(TAG1, "容器添加" + bean.getMsg() + bean.getCode());
                        if (bean.getCode() == 200) {
                            Toast.makeText(GroupListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(GroupListActivity.this, GroupProductActivity.class);
                            intent.putExtra("CONTAINER", container);
                            intent.putExtra("ID", id);
                            resultLauncher.launch(intent);
                        } else {
                            if (bean.getMsg() != null) {
                                Toast.makeText(GroupListActivity.this, "添加失败：" + bean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 校验待提交的数据是否合法
     */
    private void dataVerification() {
        if (StringUtils.isEmpty(putType)) {
            Toast.makeText(this, "请选中入库类型", Toast.LENGTH_SHORT).show();
            return;
        }
        // 应点收的物料，是否已经点收
        boolean isGroup;
        // 是否显示应收数量和已收数量不一致的dialog
        boolean showDialog = false;

        List<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> list = new ArrayList<>();
        if (mViewModel.getData().getValue() != null) {
            list = mViewModel.getData().getValue().getGoodsTypeDetail();
        }
        for (InboundDetailResponse.DataDTO.GoodsTypeDetailDTO detailDTO : list) {
            isGroup = false;
            int amount = 0;
            for (GroupGoodBean groupGoodBean : showList) {
                if (groupGoodBean.getMaterialCode().equals(detailDTO.getMaterialCode())) {
                    isGroup = true;
                    // 把所有载具上同materialCode的货物数量进行累加，判断应收数量和已收数量是否一致
                    amount = amount + groupGoodBean.getReceivedAmount();
                }
            }

            if (!isGroup) {
                Toast.makeText(this, "还有物资未点收，请继续点收!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (detailDTO.getAmount() != amount) {
                showDialog = true;
            }
        }

        if (showDialog) {
            showInfoDialog("提示", "是否确认按照目前仓库实际收货数里为准进行入库?", (dialogInterface, i) -> {
                submit();
            });
        } else {
            submit();
        }
    }

    /**
     * 提交数据
     */
    private void submit() {

        Map<String, Object> map = new HashMap();
        //仓库id
        map.put("storehouseId", Hawk.get(Constant.STOREHOUSE_ID));
        // 入库单id
        map.put("orderId", mViewModel.data.getValue().getId());
        // 入库单单号
        map.put("orderNumber", mViewModel.data.getValue().getOrderNumber());
        // 上架类型
        map.put("putType", putType);
        // 详细数据
        map.put("putTaskGoodsDetails", dataMap);

        HttpRequest.getInstance()
                .submitGroupDisk(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<CommonResponse>() {
                    @Override
                    public void success(CommonResponse resultResponse) {
                        Log.e(TAG1, "组盘入库提交：" + resultResponse.getMsg() + resultResponse.getCode());
                        Toast.makeText(GroupListActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        if (resultResponse.getCode().equals(200)) {
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (dataMap == null || dataMap.isEmpty()) {
            finish();
        } else {
            showInfoDialog("提示", "正在组盘中，是否退出？", (dialogInterface, i) -> finish());
        }
    }
}

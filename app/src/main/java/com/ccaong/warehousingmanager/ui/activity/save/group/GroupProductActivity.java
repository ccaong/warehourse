package com.ccaong.warehousingmanager.ui.activity.save.group;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.CommonResponse;
import com.ccaong.warehousingmanager.bean.ContainerStatusResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.GroupGoodBean;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.bean.SpinnerTestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityGroupProductBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.quick.QuickWarehousingActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class GroupProductActivity extends BaseActivity<ActivityGroupProductBinding, GroupProductViewMode> {


    String id;
    CommonAdapter<GroupGoodBean> commonAdapter;

    List<GroupGoodBean> list = new ArrayList<>();

    String putType;

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
        return R.layout.activity_group_product;
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

        mViewModel.getData().observe(this, dataDTO -> setGoodList(dataDTO.getGoodsTypeDetail()));

        initRecyclerView();

        initSp();

        mDataBinding.btnConfirm.setOnClickListener(view -> confirm());
        mDataBinding.tvSubmit.setOnClickListener(view -> judgeNum());

        // 监听载具输入完成事件
        mDataBinding.etVehicle.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                checkContainerInTask(mDataBinding.etVehicle.getText().toString());
            }
            return false;
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.scan) {
            scanCode();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void startScan() {
        super.startScan();
        mDataBinding.etFo.requestFocus();
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        // 判断结果是否为物品码
        if (CodeParseUtils.isGoodsCode(result)) {
            selectedGood(CodeParseUtils.getGoodSkuCode(result));
        } else if (CodeParseUtils.isCodeContainer(result)) {
            checkContainerInTask(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        checkContainerInTask(result);
    }

    /**
     * 根据扫码结果，选中某个物品
     *
     * @param id 物品id
     */
    private void selectedGood(String id) {
        List<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> list = new ArrayList();
        list = Objects.requireNonNull(mViewModel.getData().getValue()).getGoodsTypeDetail();
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i).getMaterialCode())) {
                mDataBinding.spProductInfo.setSelection(i, true);
                setGoodsData(list.get(i));
                autoAdd();
                return;
            }
        }
        Toast.makeText(this, "扫描的物品不在本入库单中！", Toast.LENGTH_SHORT).show();
    }

    private InboundDetailResponse.DataDTO.GoodsTypeDetailDTO selectGoods;

    /**
     * 设置选中的物品
     *
     * @param goods 选中的物品
     */
    private void setGoodsData(InboundDetailResponse.DataDTO.GoodsTypeDetailDTO goods) {
        selectGoods = goods;
        mDataBinding.tvUnit.setText(goods.getUnit());
        mDataBinding.tvSNum.setText(String.valueOf(goods.getAmount()));
        mDataBinding.etANum.setText("1");
    }


    private void setGoodList(List<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> list) {

        ArrayAdapter<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> productAdapter = new ArrayAdapter<>(this, R.
                layout.item_spinner, list);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spProductInfo.setAdapter(productAdapter);
        mDataBinding.spProductInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InboundDetailResponse.DataDTO.GoodsTypeDetailDTO data = list.get(i);
                Log.e(TAG1, "选择了" + data.getMetarialName());
                setGoodsData(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    }

    private void autoAdd() {
        String zj = mDataBinding.etVehicle.getText().toString();
        if ("".equals(zj)) {
            return;
        }
        if ("".equals(putType) || "-1".equals(putType)) {
            return;
        }

        confirm();
    }


    private Map<String, List<GroupGoodBean>> dataMap = new ArrayMap();

    /**
     * 添加一个组盘的数据
     */
    private void confirm() {

        try {
            Integer.parseInt(mDataBinding.etANum.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的数量", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(putType) || "-1".equals(putType)) {
            Toast.makeText(this, "请选择上架类型", Toast.LENGTH_SHORT).show();
            return;
        }

        String zj = mDataBinding.etVehicle.getText().toString();
        if ("".equals(zj)) {
            Toast.makeText(this, "请扫载具码或输入载具编码", Toast.LENGTH_SHORT).show();
            return;
        }


        int num;
        num = Integer.parseInt(mDataBinding.etANum.getText().toString());
        for (GroupGoodBean groupGoodBean : list) {
            // 物品已经点收过，只需要更新数量
            if (selectGoods.getMaterialCode().equals(groupGoodBean.getMaterialCode())) {
                // TODO: 2022/11/21  先判断一下当前的已收数量和应收数量
                if (groupGoodBean.getReceivedAmount() >= groupGoodBean.getNeedAmount()) {
                    showDialog("提示", "是否继续点收?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            groupGoodBean.setReceivedAmount(groupGoodBean.getReceivedAmount() + num);
                            updateGoodList();
                        }
                    });
                } else {
                    groupGoodBean.setReceivedAmount(groupGoodBean.getReceivedAmount() + num);
                    updateGoodList();
                }

                return;
            }
        }

        list.add(new GroupGoodBean(selectGoods.getMaterialCode(), selectGoods.getAmount(), num));

        updateGoodList();
    }

    private void updateGoodList() {

        int num;
        num = Integer.parseInt(mDataBinding.etANum.getText().toString());

        for (Map.Entry<String, List<GroupGoodBean>> entry : dataMap.entrySet()) {
            // 判断当前载具下有没有数据
            if (mDataBinding.etVehicle.getText().toString().equals(entry.getKey())) {
                Log.e(TAG1, "当前载具下已有数据");
                // 当前载具下，已有数据，需要在原有数据上添加
                List<GroupGoodBean> list = entry.getValue();
                for (GroupGoodBean bean : list) {
                    // 判断物料是否已经添加，如已经添加，数量+1否则，添加一个数据
                    if (bean.getMaterialCode().equals(selectGoods.getMaterialCode())) {
                        bean.setReceivedAmount(bean.getReceivedAmount() + num);
                        updateList();
                        return;
                    }
                }
                Log.e(TAG1, "在已有载具上（" + entry.getKey() + "）添加一个物品信息");
                list.add(getInputBean());
                updateList();
                return;
            }
        }


        Log.e(TAG1, "在新的载具上（" + mDataBinding.etVehicle.getText().toString() + "）添加一个物品信息");
        // 添加数据
        List<GroupGoodBean> list = new ArrayList();
        list.add(getInputBean());
        dataMap.put(mDataBinding.etVehicle.getText().toString(), list);
        updateList();

        mDataBinding.spPutType.setEnabled(false);
    }


    private GroupGoodBean getInputBean() {
        GroupGoodBean groupGoodBean = new GroupGoodBean();
        groupGoodBean.setZjId(mDataBinding.etVehicle.getText().toString());
        groupGoodBean.setMaterialCode(selectGoods.getMaterialCode());
        groupGoodBean.setGoodsTypeId(selectGoods.getGoodsTypeId());
        groupGoodBean.setMetarialName(selectGoods.getMetarialName());
        groupGoodBean.setSkuName(selectGoods.getSkuName());
        groupGoodBean.setSpecificationDesc(selectGoods.getSpecificationDesc());
        groupGoodBean.setUnit(selectGoods.getUnit());
        groupGoodBean.setVolume(selectGoods.getVolume());
        groupGoodBean.setWeight(selectGoods.getWeight());
        groupGoodBean.setNeedAmount(selectGoods.getAmount());
        groupGoodBean.setReceivedAmount(Integer.parseInt(mDataBinding.etANum.getText().toString()));
        return groupGoodBean;
    }

    /**
     * 更新下方的已入库的物品列表
     */
    private void updateList() {
        List<GroupGoodBean> showList = new ArrayList<>();
        for (Map.Entry<String, List<GroupGoodBean>> entry : dataMap.entrySet()) {
            showList.addAll(entry.getValue());
        }
        commonAdapter.onItemDatasChanged(showList);
    }

    /**
     * 判断容器是否存在
     *
     * @param id
     */
    public void checkContainerInTask(String id) {

        HttpRequest.getInstance()
                .checkContainerCode(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ContainerStatusResponse>() {
                    @Override
                    public void success(ContainerStatusResponse bean) {
                        Log.e(TAG1, "容器是否存在：" + bean.getMsg() + bean.getCode());
                        if (bean.getCode() == 200) {
                            if (bean.getData().getExist().equals("0")) {
//                                showWarningDialog(id);
                                String msg = "扫描的容器:" + id + "在系统中不存在，是否将该容器添加到系统中？";
                                showDialog("警告！", msg, (dialogInterface, i) -> addContainer(id));

                            } else if (bean.getData().getUsed().equals("1")) {
                                Toast.makeText(GroupProductActivity.this, "该容器已被占用！", Toast.LENGTH_SHORT).show();
                            } else {
                                mDataBinding.etVehicle.setText(id);
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
     * 添加容器弹窗
     */
    private void showWarningDialog(String id) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(GroupProductActivity.this);
        normalDialog.setTitle("警告！");
        normalDialog.setMessage("扫描的容器:" + id + "在系统中不存在，是否将该容器添加到系统中？");
        normalDialog.setPositiveButton("确定",
                (dialog, which) -> addContainer(id));

        normalDialog.setNegativeButton("取消", (dialogInterface, i) -> {

        });
        // 显示
        normalDialog.show();
    }

    /**
     * 弹窗提示
     *
     * @param title    title
     * @param message  提示信息
     * @param listener 确定按钮点击事件
     */
    private void showDialog(String title, String message, DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(GroupProductActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("确定", listener);
        normalDialog.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        normalDialog.show();
    }

    /**
     * 新增容器
     *
     * @param barCode 容器id
     */
    public void addContainer(String barCode) {

        String typeId = "0";

        Map<String, String> map = new HashMap();
        map.put("barCode", barCode);
        map.put("typeId", typeId);

        HttpRequest.getInstance()
                .addContainer(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        Log.e(TAG1, "容器添加" + bean.getMsg() + bean.getCode());
                        if (bean.getCode() == 200) {
                            mDataBinding.etVehicle.setText(barCode);
                            Toast.makeText(GroupProductActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            if (bean.getMsg() != null) {
                                Toast.makeText(GroupProductActivity.this, "添加失败：" + bean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 先判断已收和应收数量再提交
     */
    private void judgeNum() {
        boolean showDialog = false;
        for (GroupGoodBean groupGoodBean : list) {
            // 如果实际点收数量<入库单数量
            if (groupGoodBean.getReceivedAmount() < groupGoodBean.getNeedAmount()) {
                showDialog = true;
            }
        }

        if (showDialog) {
            showDialog("", "你是否确认按照目前仓库实际收货数里为准进行入库?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    submit();
                }
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
        //上架类型
        map.put("putType", putType);
        // 详细数据
        map.put("putTaskGoodsDetails", dataMap);

        // 类型，废弃
        map.put("groupDiskType", "");
        // flag，废弃
        map.put("groupDiskFlag", "");


        HttpRequest.getInstance()
                .submitGroupDisk(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<CommonResponse>() {
                    @Override
                    public void success(CommonResponse resultResponse) {
                        Log.e(TAG1, "组盘入库提交：" + resultResponse.getMsg() + resultResponse.getCode());
                        Toast.makeText(GroupProductActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        if (resultResponse.getCode().equals(200)) {
                            finish();
                        }
                    }
                });
    }
}

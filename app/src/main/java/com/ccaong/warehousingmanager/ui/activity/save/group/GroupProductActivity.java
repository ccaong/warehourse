package com.ccaong.warehousingmanager.ui.activity.save.group;

import static com.ccaong.warehousingmanager.App.getContext;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.ChildGoodBean;
import com.ccaong.warehousingmanager.bean.GroupGoodBean;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivityGroupProductBinding;
import com.ccaong.warehousingmanager.util.ChildGoodsUtils;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.ccaong.warehousingmanager.util.StringUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class GroupProductActivity extends BaseActivity<ActivityGroupProductBinding, GroupProductViewMode> {
    String id;
    String container;

    CommonAdapter<GroupGoodBean> commonAdapter;

    // 点收组盘完成的数据
    List<GroupGoodBean> list = new ArrayList<>();

    // 扫码选中的物品
    private InboundDetailResponse.DataDTO.GoodsTypeDetailDTO selectGoods;

    // 多箱一套还未成套的物品列表
    List<ChildGoodBean> tempChildList = new ArrayList<>();

    // 所有点收的多箱一套的子物品列表
    List<ChildGoodBean> tempShowChildList = new ArrayList<>();

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        id = intent.getStringExtra("ID");
        container = intent.getStringExtra("CONTAINER");

        List<GroupGoodBean> tempList = Hawk.get("TEMP");
        Hawk.delete("TEMP");

        if (tempList != null) {
            list.addAll(tempList);
        }
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

        mDataBinding.etVehicle.setText(container);

        mViewModel.loadData(id);

        mViewModel.getData().observe(this, dataDTO -> setGoodList2Spinner(dataDTO.getGoodsTypeDetail()));

        initRecyclerView();

        mDataBinding.btnConfirm.setOnClickListener(view -> confirm());

        mDataBinding.tvSubmit.setOnClickListener(view -> submit());
    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<GroupGoodBean>(list, R.layout.item_group_product, BR.groupProduct) {
            @Override
            public void addListener(View root, GroupGoodBean itemData, int position) {
                super.addListener(root, itemData, position);

                root.findViewById(R.id.item).setOnClickListener(view -> {
                    if ("1".equals(itemData.getSupportInformation()) && itemData.getQuantity() > 1) {
                        Hawk.put("TEMP", tempShowChildList);
                        // 显示详情
                        Intent intent = new Intent(GroupProductActivity.this, ChildGoodsListActivity.class);
                        intent.putExtra("CODE", itemData.getMaterialCode());
                        intent.putExtra("NAME", itemData.getSkuName());
                        intent.putExtra("NUM", itemData.getReceivedAmount());
                        intent.putExtra("QUANTITY", itemData.getQuantity());
                        intent.putExtra("SUPPORT_INFO", itemData.getSupportInformation());
                        startActivity(intent);
                    }
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 初始化物品的Spinner
     *
     * @param list
     */
    private void setGoodList2Spinner(List<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> list) {

        ArrayAdapter<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> productAdapter = new ArrayAdapter<>(this, R.
                layout.item_spinner, list);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spProductInfo.setAdapter(productAdapter);
    }

    // 扫描物品的序列号
    private String tempSerialNo = "XXXX";

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        // 判断结果是否为物品码
        if (CodeParseUtils.isGoodsCode(result)) {
            String str = mDataBinding.etVehicle.getText().toString();
            if (StringUtils.isEmpty(str)) {
                Toast.makeText(this, "请先扫描载具码", Toast.LENGTH_SHORT).show();
                return;
            }
            tempSerialNo = CodeParseUtils.getSno(result);
            selectedGood(result);
        }
    }

    /**
     * 根据扫码结果，选中某个物品
     *
     * @param result 物品码
     */
    private void selectedGood(String result) {
        String sku = CodeParseUtils.getGoodSkuCode(result);
        String childCode = CodeParseUtils.getGoodChildCode(result);
        String sNo = CodeParseUtils.getSno(result);
        String taskNumber = CodeParseUtils.getTaskNumber(result);

        List<InboundDetailResponse.DataDTO.GoodsTypeDetailDTO> list = new ArrayList();
        list = Objects.requireNonNull(mViewModel.getData().getValue()).getGoodsTypeDetail();

        String ykId = mViewModel.getData().getValue().getOrderNumber();
        String inboundType = mViewModel.getData().getValue().getInboundType();

        for (int i = 0; i < list.size(); i++) {
            if (sku.equals(list.get(i).getMaterialCode())) {
                // 判断单号是否一致 [移库上架组盘时需要判断]
                // 先在接口中获取类型，判断是否为物资移库操作
                // 如果有移库单号，就需要判断
                if (inboundType.equals("4")) {
                    if (!taskNumber.equals(ykId)) {
                        // 单号不一致，不处理
                        continue;
                    }
                }

                // 判断序列号是否对应
                if ("1".equals(list.get(i).getSupportInformation())) {
                    // 多箱一套的数据，需要判断序列号
                    String[] sNoList = list.get(i).getSerialNumber().split(",");
                    for (String str : sNoList) {
                        if (sNo.equals(str)) {
                            mDataBinding.spProductInfo.setSelection(i, true);
                            setGoodsData(list.get(i), sku, childCode, sNo);
                            return;
                        }
                    }
                } else {
                    mDataBinding.spProductInfo.setSelection(i, true);
                    setGoodsData(list.get(i), sku, childCode, sNo);
                    return;
                }
            }
        }
        Toast.makeText(this, "扫描的物品不在本入库单中！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置选中的物品
     *
     * @param goods 选中的物品
     */
    private void setGoodsData(InboundDetailResponse.DataDTO.GoodsTypeDetailDTO goods, String sku, String code, String sNo) {
        //  判断当前物品是否由多个子物品构成
        if ("1".equals(goods.getSupportInformation()) && goods.getQuantity() != null && goods.getQuantity() > 1) {
            //相同序列号相同子项的物品，不能重复添加
            for (ChildGoodBean childGoodBean : tempShowChildList) {
                if (sku.equals(childGoodBean.getSku()) &&
                        code.equals(childGoodBean.getCode()) &&
                        sNo.equals(childGoodBean.getSerialNo())) {
                    // 相同序列号相同子项的物品，不能重复添加，当作无效操作。
                    return;
                }
            }

            //  当前物品由多个子获取构成，需要组合,
            //  把当前扫码的物品添加到待组合的列表中
            tempChildList.add(new ChildGoodBean(sku, code, sNo));
            tempShowChildList.add(new ChildGoodBean(sku, code, sNo));

            Map<String, Object> result;
            // 判断所有待组合的子物品是否能组合成一套完整物品
            result = ChildGoodsUtils.getList(tempChildList, sku, sNo, goods.getQuantity());
            Boolean a = (Boolean) result.get("result");
            if (a) {
                // 如果待组合的子物品组合成一套物品，就继续后续添加操作

                // 更新未组合成套的子物品列表
                tempChildList = (List<ChildGoodBean>) result.get("list");

                selectGoods = goods;
                mDataBinding.tvUnit.setText(goods.getUnit());
                mDataBinding.tvSNum.setText(String.valueOf(goods.getAmount()));
                mDataBinding.etANum.setText("1");
                confirm();
            } else {
                // 判断结果，如果为false，说明还未组合成一套，提示用户继续扫码
                Toast.makeText(this, "请继续扫码", Toast.LENGTH_SHORT).show();
            }
        } else {
            selectGoods = goods;
            mDataBinding.tvUnit.setText(goods.getUnit());
            mDataBinding.tvSNum.setText(String.valueOf(goods.getAmount()));
            mDataBinding.etANum.setText("1");
            confirm();
        }
    }

    /**
     * 判断当前的已收数量是否大于等于应收数量
     */
    private void confirm() {

        try {
            Integer.parseInt(mDataBinding.etANum.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的数量", Toast.LENGTH_SHORT).show();
            return;
        }

        for (GroupGoodBean groupGoodBean : list) {
            // 物品已经点收过，只需要更新数量
            if (selectGoods.getMaterialCode().equals(groupGoodBean.getMaterialCode())) {
                // 先判断一下当前的已收数量和应收数量
                if (groupGoodBean.getReceivedAmount() >= groupGoodBean.getNeedAmount()) {
                    showInfoDialog("提示", "是否继续点收?", (dialogInterface, i) -> {
                        updateGoodList();
                    });
                } else {
                    updateGoodList();
                }
                return;
            }
        }
        updateGoodList();
    }

    /**
     * 更新已经点收的物品
     */
    private void updateGoodList() {

        int num = Integer.parseInt(mDataBinding.etANum.getText().toString());

        for (GroupGoodBean bean : list) {
            // 判断物料是否已经添加，如已经添加，就更新数量，否则，添加一个新的数据到list中
            if (bean.getMaterialCode().equals(selectGoods.getMaterialCode())) {
                // 更新数量
                bean.setReceivedAmount(bean.getReceivedAmount() + num);
                if (!"XXXX".equals(tempSerialNo)) {
                    String serialNo = bean.getSerialNumber();
                    if (StringUtils.isEmpty(serialNo)) {
                        bean.setSerialNumber(tempSerialNo);
                    } else {
                        bean.setSerialNumber(serialNo + "," + tempSerialNo);
                    }
                }
                commonAdapter.onItemDatasChanged(list);
                return;
            }
        }

        // 当前载具上还没有点收过该物品，添加一个该物品到点收列表中
        list.add(getInputBean());
        commonAdapter.onItemDatasChanged(list);
    }


    /**
     * 获取当前点收的物品的详细信息
     *
     * @return
     */
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

        groupGoodBean.setQuantity(selectGoods.getQuantity());
        groupGoodBean.setSupportInformation(selectGoods.getSupportInformation());

        if (!"XXXX".equals(tempSerialNo)) {
            groupGoodBean.setSerialNumber(tempSerialNo);
        }
        return groupGoodBean;
    }

    /**
     * 先判断已收和应收数量再提交
     */
    private void submit() {

        Intent intent = new Intent();
        intent.putExtra("CONTAINER", container);
        Hawk.put("TEMP", list);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (list == null || list.isEmpty()) {
            finish();
        } else {
            showInfoDialog("提示", "正在组盘中，是否退出？", (dialogInterface, i) -> finish());
        }
    }
}

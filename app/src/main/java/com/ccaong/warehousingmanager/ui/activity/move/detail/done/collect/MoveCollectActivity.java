package com.ccaong.warehousingmanager.ui.activity.move.detail.done.collect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.bean.GroupGoodBean;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.databinding.ActivityMoveDoneCollectDetailBinding;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoveCollectActivity extends BaseActivity<ActivityMoveDoneCollectDetailBinding, MoveCollectViewModel> {

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_move_done_collect_detail;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MoveCollectViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        actionBar.setTitle("移库单点收作业");

        mDataBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeList();
            }
        });
        initRecyclerView();
    }


    private void initRecyclerView() {


    }

    @Override
    protected void startScan() {
        super.startScan();
        mDataBinding.etFo.requestFocus();
    }

    @Override
    protected void rfidResult(String result) {
        super.rfidResult(result);
        if (CodeParseUtils.isCodeContainer(result)) {
//            checkContainerInTask(result);
        }
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        // 判断结果是否为物品码
        if (CodeParseUtils.isGoodsCode(result)) {
//            selectedGood(CodeParseUtils.getGoodSkuCode(result));
        } else if (CodeParseUtils.isCodeContainer(result)) {
//            checkContainerInTask(result);
        }

    }

    private void changeList() {

    }

    List<GroupGoodBean> list = new ArrayList<>();
    private Map<String, List<GroupGoodBean>> dataMap = new ArrayMap();
    private InboundDetailResponse.DataDTO.GoodsTypeDetailDTO selectGoods;

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
                // 先判断一下当前的已收数量和应收数量
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
//        commonAdapter.onItemDatasChanged(showList);
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
                new AlertDialog.Builder(MoveCollectActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("确定", listener);
        normalDialog.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        normalDialog.show();
    }

}

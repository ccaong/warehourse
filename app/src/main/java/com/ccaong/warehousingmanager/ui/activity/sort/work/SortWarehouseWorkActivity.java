package com.ccaong.warehousingmanager.ui.activity.sort.work;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.ChildGoodBean;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.SortWorkResponse;
import com.ccaong.warehousingmanager.databinding.ActivitySortWarehouserWorkBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.util.ChildGoodsUtils;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.ccaong.warehousingmanager.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caocong
 * @date 2022/9/21
 */
public class SortWarehouseWorkActivity extends BaseActivity<ActivitySortWarehouserWorkBinding, SortWarehouseWorkViewModel> {

    public static final String TAG = SortWarehouseWorkActivity.class.getSimpleName();

    String code;
    String orderNumber;
    String relNumber;
    String relSource;


    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);

        code = intent.getStringExtra("CODE");

        orderNumber = intent.getStringExtra("ORDER_NUMBER");
        relNumber = intent.getStringExtra("REL_NUMBER");
        relSource = intent.getStringExtra("REL_SOURCE");
    }

    @Override
    protected boolean isSupportScan() {
        if ("typeHou".equals(relSource)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected boolean isSupportRfid() {
        if ("typeHou".equals(relSource)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void startScan() {
        super.startScan();
    }

    List<ChildGoodBean> tempChildList = new ArrayList<>();

    boolean isContinue = false;

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        if (CodeParseUtils.isGoodsCode(result)) {
            String sku = CodeParseUtils.getGoodSkuCode(result);
            String ownerCode = CodeParseUtils.getOwnerCode(result);
            String childCode = CodeParseUtils.getGoodChildCode(result);
            String sNo = CodeParseUtils.getSno(result);

            for (SortWorkResponse.DataDTO dataDTO : mViewModel.getData().getValue()) {
                //  PDA扫码物品编码，根据编码上的 权属单位+SKU 判断是否是要出库的物品，
                if (sku.equals(dataDTO.getGoodsInfo().getMaterialCode())) {
                    // 判断权属单位
                    if (ownerCode.equals(dataDTO.getOwner().getOwnerCode())) {
                        // SKU和权属单位都符合。
                        SortWorkResponse.DataDTO.GoodsInfoDTO.GoodsTypeDTO goodsType = dataDTO.getGoodsInfo().getGoodsType();
                        // 判断是否为多箱一套的情况
                        if ("1".equals(goodsType.getSupportInformation())) {
                            // 需要判断序列号是否对应
                            String[] serialNums = dataDTO.getSerialNumber().split(",");
                            if (StringUtils.isContain(serialNums, sNo)) {

                                if (goodsType.getQuantity() != null && goodsType.getQuantity() > 1) {
                                    int num = goodsType.getQuantity();
                                    // 如果是多箱一套的情况，就额外处理
                                    // 首先把数据添加到tempList中
                                    tempChildList.add(new ChildGoodBean(sku, childCode, sNo));
                                    //然后判断temp中，是否可以组成一整套
                                    Map<String, Object> resultGroup;
                                    resultGroup = ChildGoodsUtils.getList(tempChildList, sku, sNo, num);
                                    Boolean a = (Boolean) resultGroup.get("result");
                                    if (a) {
                                        // 如果组合成功，就更新缓存列表，同时继续执行后续的添加操作
                                        tempChildList = (List<ChildGoodBean>) resultGroup.get("list");
                                    } else {
                                        // 判断结果，如果为false，就不处理
                                        Toast.makeText(this, "请继续扫码", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                            } else {
                                // 序列号不对，继续判断和列表中的下一个物品比对
                                continue;
                            }
                        }


                        isContinue = false;
                        // 获取数量，然后+1
                        int count = 0;
                        try {
                            if (dataDTO.getCollectCount() == null || dataDTO.getCollectCount().equals("")) {
                                count = 0;
                            } else {
                                count = Integer.parseInt(dataDTO.getCollectCount());
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        // 判断数量是不是大于，如果大于，就提示
                        if (count >= dataDTO.getGoodsCount()) {
                            showErrorDialog("警告", "拣货数量不能超出载具上已有物品数量！");
//                                showDialog("提示", "是否继续点收?", (dialogInterface, i) -> {
//                                    isContinue = true;
//                                });
                        } else {
//                                isContinue = true;
                            count = count + 1;
                            dataDTO.setCollectCount(String.valueOf(count));

                            List<String> serialList = new ArrayList<>();
                            if (dataDTO.getSerialList() != null) {
                                serialList = dataDTO.getSerialList();
                            }
                            // 添加序列号
                            serialList.add(sNo);
                            dataDTO.setSerialList(serialList);

                            mViewModel.data.postValue(mViewModel.getData().getValue());
                        }
//                        if (isContinue) {
//                            // 需要加上序列号
//                            count = count + 1;
//                            dataDTO.setCollectCount(String.valueOf(count));
//
//                            List<String> serialList = new ArrayList<>();
//                            if (dataDTO.getSerialList() != null) {
//                                serialList = dataDTO.getSerialList();
//                            }
//                            // 添加序列号
//                            serialList.add(sNo);
//                            dataDTO.setSerialList(serialList);
//
//                            mViewModel.data.postValue(mViewModel.getData().getValue());
//                        }
                        return;
                    }
                }
            }
            Toast.makeText(this, "不是任务出库物资", Toast.LENGTH_SHORT).show();
        }
    }


    private void parseGoodInfo(String good) {

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
                new AlertDialog.Builder(SortWarehouseWorkActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("确定", listener);
        normalDialog.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        normalDialog.show();
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

        mDataBinding.tvSubmit.setOnClickListener(view -> submitJudge());

        initRecyclerView();
    }


    private void initRecyclerView() {
        CommonAdapter<SortWorkResponse.DataDTO> commonAdapter;
        if ("typeHou".equals(relSource)) {
            commonAdapter = new CommonAdapter<SortWorkResponse.DataDTO>(R.layout.item_sort_work_yk, BR.sortWorkYk) {
            };
        } else {
            commonAdapter = new CommonAdapter<SortWorkResponse.DataDTO>(R.layout.item_sort_work, BR.sortWork) {
            };
        }

        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getData().observe(this, dataDTOS -> {
            commonAdapter.onItemDatasChanged(dataDTOS);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadData(code, relSource);
    }


    private void submitJudge() {

        List<Map<String, Object>> list = new ArrayList();


        if ("typeHou".equals(relSource)) {
            // 物资移库
            for (SortWorkResponse.DataDTO dataDTO : mViewModel.getData().getValue()) {
                Map<String, Object> map = new HashMap();

                map.put("relId", dataDTO.getId());
                map.put("goodsAmount", dataDTO.getGoodsCount());
                map.put("realCount", dataDTO.getGoodsCount());
                list.add(map);
            }

            submit(list);
        } else {

            boolean allowSubmit = true;
            String illegalGoodName = "";
            for (SortWorkResponse.DataDTO dataDTO : mViewModel.getData().getValue()) {
                Map<String, Object> map = new HashMap();
                int count = 0;
                try {
                    count = Integer.parseInt(dataDTO.getCollectCount());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (count > dataDTO.getGoodsCount()) {
                    String errorMsg = "物料【" + dataDTO.getGoodsInfo().getGoodsType().getParentName()
                            + "_" + dataDTO.getGoodsInfo().getGoodsType().getName()
                            + "】的实际拣货数量已大于应拣货数量，请核对后重新拣货！";
                    showErrorDialog("警告", errorMsg);
                    return;
                }

                if (count < dataDTO.getGoodsCount()) {
                    allowSubmit = false;
                    illegalGoodName = illegalGoodName + "," + dataDTO.getGoodsInfo().getGoodsType().getParentName()
                            + "_" + dataDTO.getGoodsInfo().getGoodsType().getName();
                }
                map.put("relId", dataDTO.getId());
                map.put("goodsAmount", dataDTO.getGoodsCount());
                map.put("realCount", count);
                // 多箱一套的情况，需要传入序列号
                if ("1".equals(dataDTO.getGoodsInfo().getGoodsType().getSupportInformation())) {
                    if (dataDTO.getGoodsInfo().getGoodsType().getQuantity() != null && dataDTO.getGoodsInfo().getGoodsType().getQuantity() > 1) {
                        List<String> serialNumbers = dataDTO.getSerialList();
                        map.put("serialNumbers", serialNumbers);
                    }
                }
                list.add(map);
            }

            if (allowSubmit) {
                submit(list);
            } else {
                String Msg = "物料【" + illegalGoodName.substring(1) + "】的拣货数量小于载具上应拣货物品数量，是否继续提交";
                showDialog("提示", Msg, (dialogInterface, i) -> submit(list));
            }
        }
    }


    private void submit(List<Map<String, Object>> list) {

        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("orderNumber", orderNumber);
        paramsMap.put("relNumber", relNumber);
        paramsMap.put("relSource", relSource);

        paramsMap.put("relList", list);

        HttpRequest.getInstance()
                .finishPick(paramsMap)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse bean) {
                        if (bean.getCode() == 200) {
                            Toast.makeText(SortWarehouseWorkActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SortWarehouseWorkActivity.this, "操作失败:" + bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d(TAG, "onError: " + e.toString());
                    }
                });
    }

}

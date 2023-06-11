package com.ccaong.warehousingmanager.ui.activity.quick;

import static com.ccaong.warehousingmanager.App.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import com.ccaong.warehousingmanager.bean.ContainerStatusResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.GoodsTypeListResponse;
import com.ccaong.warehousingmanager.bean.QuickGoodBean;
import com.ccaong.warehousingmanager.bean.SpinnerTestBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.ActivityQuickWarehousingBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.quick.select.SelectDeptActivity;
import com.ccaong.warehousingmanager.ui.activity.quick.select.SelectFactureActivity;
import com.ccaong.warehousingmanager.util.CodeParseUtils;
import com.ccaong.warehousingmanager.util.StringUtils;
import com.orhanobut.hawk.Hawk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快速入库
 *
 * @author caocong
 * @date 2022/9/19
 */
public class QuickWarehousingActivity extends BaseActivity<ActivityQuickWarehousingBinding, QuickWarehousingViewModel> {

    //入库类型
//    private String type = "0";
    private String receiveType = "0";

    private String goodsTypeId = "";
    private String goodsName = "";
    private String unit = "";
    private String sku = "";
    private String goodsGrade = "";
    private String supportInformation = "";

    private String customerName = "";
    private String customerId = "";


    private String manufacturerId = "";
    private String manufacturerName = "";

    private Date factureDate;


    CommonAdapter<QuickGoodBean> commonAdapter;


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
        return R.layout.activity_quick_warehousing;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(QuickWarehousingViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }


    @Override
    protected void rfidResult(String result) {
        if (CodeParseUtils.rfidIsLocalCode(result)) {
            mDataBinding.etWz.setText(CodeParseUtils.getRfidLocalCode(result));
        } else {
            checkContainerInTask(result);
        }
    }

    @Override
    protected void scanResult(String result) {
        super.scanResult(result);
        // 判断位置编码和物料编码
        if (CodeParseUtils.isGoodsCode(result)) {
            getGoodsDetails(result);
        } else if (CodeParseUtils.isLocalCode(result)) {
            // 将数据填写到位置编码中
            mDataBinding.etWz.setText(result);
        } else if (CodeParseUtils.isCodeContainer(result)) {
            // 将数据填写到载具编码中
            checkContainerInTask(result);
        } else {
            Toast.makeText(this, "扫码结果匹配失败！请重新扫码！", Toast.LENGTH_SHORT).show();
        }

    }

    ActivityResultLauncher<Intent> deptResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    customerName = result.getData().getStringExtra("NAME");
                    customerId = result.getData().getStringExtra("ID");

                    mDataBinding.tvDept.setText(customerName);
                }
            });

    ActivityResultLauncher<Intent> factureResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    manufacturerId = result.getData().getStringExtra("ID");
                    manufacturerName = result.getData().getStringExtra("NAME");

                    mDataBinding.tvFacture.setText(manufacturerName);

                }
            });


    @Override
    protected void init() {
        actionBar.setTitle("快速入库");
        mViewModel.getGoodType();
        mDataBinding.tvCk.setText(Hawk.get(Constant.STOREHOUSE_NAME));
        initRecyclerView();
        initSp();

        mDataBinding.ivZj.setOnClickListener(view -> startScanRFID());

        mDataBinding.ivWz.setOnClickListener(view -> scanCode());
        mDataBinding.ivWl.setOnClickListener(view -> scanCode());

        // 生成入库单id
        mDataBinding.tvGenerate.setOnClickListener(view -> mViewModel.getOrderNumber());

        //选择权属单位
        mDataBinding.tvDept.setOnClickListener(view -> deptResultLauncher.launch(new Intent(QuickWarehousingActivity.this, SelectDeptActivity.class)));

        mDataBinding.tvFacture.setOnClickListener(view -> factureResultLauncher.launch(new Intent(QuickWarehousingActivity.this, SelectFactureActivity.class)));

        mDataBinding.tvFactureDate.setOnClickListener(view -> selectDate());

        // 添加一条数据
        mDataBinding.btnAdd.setOnClickListener(view -> add());
        // 提交数据
        mDataBinding.tvSubmit.setOnClickListener(view -> submit());

        // 监听载具输入完成事件
        mDataBinding.etZj.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                checkContainerInTask(mDataBinding.etZj.getText().toString());
            }
            return false;
        });
    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<QuickGoodBean>(R.layout.item_quick, BR.quickData) {
            @Override
            public void addListener(View root, QuickGoodBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.btn_del).setOnClickListener(view -> {
                    // 删除当前数据,先得判断载具，然后再更新载具对应的list
                    for (Map.Entry<String, List<QuickGoodBean>> entry : dataMap.entrySet()) {
                        if (entry.getKey().equals(itemData.getZj())) {
                            entry.getValue().remove(itemData);
                            updateList();
                            return;
                        }
                    }
                });
            }
        };
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initSp() {

        List<SpinnerTestBean> listSl = new ArrayList<>();
        listSl.add(new SpinnerTestBean("0", "合同收料"));
        listSl.add(new SpinnerTestBean("1", "上级来料"));
        listSl.add(new SpinnerTestBean("2", "上交收料"));
        listSl.add(new SpinnerTestBean("3", "转库收料"));
        listSl.add(new SpinnerTestBean("4", "其他收料"));

        ArrayAdapter<SpinnerTestBean> slTypeAdapter = new ArrayAdapter<>(this, R.
                layout.item_spinner, listSl);
        slTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spSlType.setAdapter(slTypeAdapter);
        mDataBinding.spSlType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTestBean data = listSl.get(i);
                receiveType = data.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        List<SpinnerTestBean> listGrade = new ArrayList<>();
        listGrade.add(new SpinnerTestBean("0", "新品"));
        listGrade.add(new SpinnerTestBean("1", "堪用品"));
        listGrade.add(new SpinnerTestBean("2", "待修品"));
        listGrade.add(new SpinnerTestBean("3", "报废品"));

        ArrayAdapter<SpinnerTestBean> gradeAdapter = new ArrayAdapter<>(this, R.
                layout.item_spinner, listGrade);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spGrade.setAdapter(gradeAdapter);
        mDataBinding.spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTestBean data = listGrade.get(i);
                goodsGrade = data.getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void selectDate() {

        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(QuickWarehousingActivity.this, (view, year, month, dayOfMonth) -> {
            String day = "";
            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            } else {
                day = String.valueOf(dayOfMonth);
            }

            String strMonth = "";
            month = month + 1;
            if (month < 10) {
                strMonth = "0" + month;
            } else {
                strMonth = String.valueOf(month);
            }


            final String data = year + "-" + strMonth + "-" + day;
            mDataBinding.tvFactureDate.setText(data);
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            try {
                factureDate = ft.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    /**
     * 获取物品信息
     */
    private void getGoodsDetails(String id) {
        String skuCode = CodeParseUtils.getGoodSkuCode(id);

        // 根据物料id获取物料的详细信息（名称，sdk，单位，其他）
        for (GoodsTypeListResponse.DataDTO good : mViewModel.listType.getValue()) {
            if (skuCode.equals(good.getMaterialCode())) {
                mDataBinding.etWl.setText(skuCode);
                //非必填项，先填空
                goodsTypeId = good.getId();
                goodsName = good.getName();
                unit = good.getGoodsUnit();
                sku = good.getSkuCode();

                supportInformation = good.getSupportInformation();
                switch (supportInformation) {
                    case "0":
                        mDataBinding.tvOther.setText("一箱多套");
                        break;
                    case "1":
                        mDataBinding.tvOther.setText("多箱一套");
                        break;
                    case "2":
                        mDataBinding.tvOther.setText("一箱一套");
                        break;
                }
                mDataBinding.etNum.setText("1");
                autoAdd();
                return;
            }
        }
        Toast.makeText(this, "扫描到的物料码在系统中不存在！", Toast.LENGTH_SHORT).show();
    }

    private void autoAdd() {

        if ("".equals(mDataBinding.et1.getText().toString())) {
            return;
        }

        if ("".equals(mDataBinding.etZj.getText().toString())) {
            return;
        }

        if ("".equals(mDataBinding.etWz.getText().toString())) {
            return;
        }

        add();
    }

    private Map<String, List<QuickGoodBean>> dataMap = new ArrayMap();

    /**
     * 添加一个快速入库的数据
     */
    private void add() {
        int num;
        try {
            num = Integer.parseInt(mDataBinding.etNum.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的数量", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(mDataBinding.etZj.getText().toString())) {
            Toast.makeText(this, "请扫描或输入载具编码！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(mDataBinding.etWl.getText().toString())) {
            Toast.makeText(this, "请扫描或输入物料编码！", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(mDataBinding.etWz.getText().toString())) {
            Toast.makeText(this, "请扫描或输入位置编码！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(mDataBinding.et1.getText().toString())) {
            Toast.makeText(this, "请生成或输入入库单号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(mDataBinding.tvFactureDate.getText().toString())) {
            Toast.makeText(this, "请选择生产日期！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(manufacturerId)) {
            Toast.makeText(this, "请选择生产厂商！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(goodsGrade)) {
            Toast.makeText(this, "请选择品级！", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Map.Entry<String, List<QuickGoodBean>> entry : dataMap.entrySet()) {
            // 判断当前载具下有没有数据
            if (mDataBinding.etZj.getText().toString().equals(entry.getKey())) {
                Log.e(TAG1, "当前载具下已有数据");
                // 当前载具下，已有数据，需要在原有数据上添加
                List<QuickGoodBean> list = entry.getValue();
                for (QuickGoodBean bean : list) {
                    // 判断物料是否已经添加，如已经添加，再判断位置是否相同，否则，添加一个数据
                    if (bean.getGoodsTypeCode().equals(mDataBinding.etWl.getText().toString())) {
                        if (bean.getWz().equals(mDataBinding.etWz.getText().toString())) {
                            Log.e(TAG1, "在已有载具上（" + entry.getKey() + "）更新物品数量");
                            bean.setReceivedAmount(bean.getReceivedAmount() + num);
                            updateList();
                            return;
                        }
                    }
                }
                Log.e(TAG1, "在已有载具上（" + entry.getKey() + "）添加一个物品信息");
                QuickGoodBean quickGoodBean = getInputBean();
                if (quickGoodBean != null) {
                    list.add(quickGoodBean);
                    updateList();
                }
                return;
            }
        }


        Log.e(TAG1, "在新的载具上（" + mDataBinding.etZj.getText().toString() + "）添加一个物品信息");
        // 添加数据
        List<QuickGoodBean> list = new ArrayList();

        QuickGoodBean quickGoodBean = getInputBean();
        if (quickGoodBean != null) {
            list.add(quickGoodBean);
            dataMap.put(mDataBinding.etZj.getText().toString(), list);
            updateList();
            // 添加之后禁止再次更新
            mDataBinding.tvGenerate.setEnabled(false);
            mDataBinding.et1.setEnabled(false);
        }
    }

    /**
     * 获取当前入库的物品信息
     *
     * @return 物品
     */
    private QuickGoodBean getInputBean() {
        QuickGoodBean bean = new QuickGoodBean();
        bean.setGoodsTypeCode(mDataBinding.etWl.getText().toString());
        bean.setGoodsTypeId(goodsTypeId);
        bean.setGoodsUnit(unit);
        bean.setLocationCode(mDataBinding.etWz.getText().toString());
        bean.setLocationId("");
        bean.setReceivedAmount(1);


        bean.setManufacturerDate(mDataBinding.tvFactureDate.getText().toString());
        bean.setManufacturerId(manufacturerId);
        bean.setManufacturerName(manufacturerName);

        bean.setGoodsGrade(goodsGrade);

        if (!StringUtils.isEmpty(mDataBinding.etPrice.getText().toString())) {
            bean.setUnitPrice(Double.parseDouble(mDataBinding.etPrice.getText().toString()));
        }
        bean.setSerialNumber(mDataBinding.etSn.getText().toString());
        switch (supportInformation) {
            case "0":
                bean.setSupportInformation("一箱多套");
                break;
            case "1":
                bean.setSupportInformation("多箱一套");

                if (StringUtils.isEmpty(mDataBinding.etSn.getText().toString())) {
                    Toast.makeText(this, "请输入序列号！", Toast.LENGTH_SHORT).show();
                    return null;
                }
                break;
            case "2":
                bean.setSupportInformation("一箱一套");
                break;
        }

        bean.setGoodsName(goodsName);
        bean.setSku(sku);
        bean.setZj(mDataBinding.etZj.getText().toString());
        bean.setWz(mDataBinding.etWz.getText().toString());

        return bean;
    }

    /**
     * 更新下方的已入库的物品列表
     */
    private void updateList() {
        List<QuickGoodBean> showList = new ArrayList<>();
        for (Map.Entry<String, List<QuickGoodBean>> entry : dataMap.entrySet()) {
            showList.addAll(entry.getValue());
        }
        commonAdapter.onItemDatasChanged(showList);
    }

    /**
     * 提交
     */
    private void submit() {
        if (StringUtils.isEmpty(mDataBinding.etVoucherNo.getText().toString())) {
            Toast.makeText(this, "请输入凭证号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(mDataBinding.etPerson.getText().toString())) {
            Toast.makeText(this, "请输入送货人", Toast.LENGTH_SHORT).show();

            return;
        }
        if (StringUtils.isEmpty(mDataBinding.etTel.getText().toString())) {
            Toast.makeText(this, "请输入联系方式", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isEmpty(customerId)) {
            Toast.makeText(this, "请选择权属单位", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> map = new HashMap();
        //入库仓库id
        map.put("storehouseId", Hawk.get(Constant.STOREHOUSE_ID));
        map.put("orderNumber", mDataBinding.et1.getText().toString());
        //入库类型 用不到了
//        map.put("inboundType", type);
        //权属单位
        map.put("ownershipUnitId", customerId);
//        map.put("customerName", customerName);
        // 收料类型
        map.put("receiveType", receiveType);
        // 联系人
        map.put("shipper", mDataBinding.etPerson.getText().toString());
        // 联系方式
        map.put("contactInformation", mDataBinding.etTel.getText().toString());
        // 凭证号
        map.put("voucherNo", mDataBinding.etVoucherNo.getText().toString());

        //具体物料信息
        map.put("putTaskGoodsDetails", dataMap);

        HttpRequest.getInstance()
                .quickInbound(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<EmptyResponse>() {
                    @Override
                    public void success(EmptyResponse resultResponse) {

                        if (resultResponse.getCode().equals(200)) {
                            Toast.makeText(QuickWarehousingActivity.this, "入库成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(QuickWarehousingActivity.this, resultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                                showWarningDialog(id);
                            } else if (bean.getData().getUsed().equals("1")) {
                                Toast.makeText(QuickWarehousingActivity.this, "该容器已被占用！", Toast.LENGTH_SHORT).show();
                            } else {
                                mDataBinding.etZj.setText(id);
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
                new AlertDialog.Builder(QuickWarehousingActivity.this);
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
                            mDataBinding.etZj.setText(barCode);
                            Toast.makeText(QuickWarehousingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            if (bean.getMsg() != null) {
                                Toast.makeText(QuickWarehousingActivity.this, "添加失败：" + bean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

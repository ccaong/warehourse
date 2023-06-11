package com.ccaong.warehousingmanager.ui.activity.sort.work;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.SortWorkResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/9/21
 */
public class SortWarehouseWorkViewModel extends BaseViewModel {

    public MutableLiveData<List<SortWorkResponse.DataDTO>> data;

    public MutableLiveData<String> submitText;


    public SortWarehouseWorkViewModel() {
        data = new MutableLiveData<>();
        submitText = new MutableLiveData<>("完成");
    }

    public LiveData<List<SortWorkResponse.DataDTO>> getData() {
        return data;
    }


    public void loadData(String id, String relSource) {

        HttpRequest.getInstance()
                .outboundRelInfo(id, relSource)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<SortWorkResponse>() {
                    @Override
                    public void success(SortWorkResponse bean) {
                        if (bean.getCode() == 200) {
                            data.setValue(bean.getData());
                            // outbound：出库拣货，typeHou：物资移库；locHou：载具移库； locCont：载具移位
                            /*if ("locHou".equals(relSource)) {
                                // 载具移库使用一键出库功能
                                setDefaultData();
                                submitText.postValue("一键出库");
                            } else*/ if (bean.getAllOut()) {
                                setDefaultData();
                                submitText.postValue("一键出库");
                            } else {
                                submitText.postValue("完成");
                            }
                        } else {
                            Log.e("拣货详情错误", "错误信息" + bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("出库", e.toString());
                    }
                });
    }


    private void setDefaultData() {
        for (SortWorkResponse.DataDTO dataDTO : data.getValue()) {
            SortWorkResponse.DataDTO.GoodsInfoDTO.GoodsTypeDTO goodsType = dataDTO.getGoodsInfo().getGoodsType();
            if ("1".equals(goodsType.getSupportInformation())) {
                if (goodsType.getQuantity() != null && goodsType.getQuantity() > 1) {
                    // 多箱一套的情况，手动赋值序列号
                    String[] serialNums = dataDTO.getSerialNumber().split(",");
                    List<String> serialList = new ArrayList<>();
                    for (String sNo : serialNums) {
                        serialList.add(sNo);
                    }
                    // 添加序列号
                    dataDTO.setSerialList(serialList);
                }
            }
            dataDTO.setCollectCount(String.valueOf(dataDTO.getGoodsCount()));
        }
    }
}

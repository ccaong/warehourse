package com.ccaong.warehousingmanager.ui.activity.quick;

import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.GoodsTypeListResponse;
import com.ccaong.warehousingmanager.bean.InOrderNumberResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class QuickWarehousingViewModel extends BaseViewModel {

    public MutableLiveData<String> orderNumber;


    public MutableLiveData<List<GoodsTypeListResponse.DataDTO>> listType;

    public QuickWarehousingViewModel() {
        listType = new MutableLiveData<>();
        orderNumber = new MutableLiveData<>();
    }

    /**
     * 入库单号规则生成
     */
    public void getOrderNumber() {
        HttpRequest.getInstance()
                .getInboundOrderNumber()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InOrderNumberResponse>() {
                    @Override
                    public void success(InOrderNumberResponse bean) {
                        if (bean.getCode() == 200) {
                            orderNumber.postValue(bean.getData());
                        }
                    }
                });
    }

    /**
     * 获取物品类型
     */
    public void getGoodType() {
        HttpRequest.getInstance()
                .getGoodsTypeList()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<GoodsTypeListResponse>() {
                    @Override
                    public void success(GoodsTypeListResponse bean) {
                        if (bean.getCode() == 200) {
                            listType.postValue(bean.getData());
                        }
                    }
                });
    }
}

package com.ccaong.warehousingmanager.ui.activity.inventory.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.InventoryDetailResponse;
import com.ccaong.warehousingmanager.bean.ResultResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eyecool
 * @date 2022/9/23
 */
public class InventoryDetailViewModel extends BaseViewModel {


    public MutableLiveData<InventoryDetailResponse.DataDTO> data;


    public InventoryDetailViewModel() {
        data = new MutableLiveData<>();
    }

    public LiveData<InventoryDetailResponse.DataDTO> getData() {
        return data;
    }

    public void loadData(String id) {

        HttpRequest.getInstance()
                .getInventoryTaskDetailById(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InventoryDetailResponse>() {
                    @Override
                    public void success(InventoryDetailResponse bean) {
                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        } else {
                            Log.e("盘点详情加载出错", bean.getMsg());
                        }
                    }
                });
    }


    public void updateInventoryResult() {
        Map map = new HashMap();
        HttpRequest.getInstance()
                .changeInventoryTaskStatus(map)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse) {

                    }
                });
    }
}

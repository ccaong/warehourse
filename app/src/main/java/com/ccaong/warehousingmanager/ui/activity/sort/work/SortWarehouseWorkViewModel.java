package com.ccaong.warehousingmanager.ui.activity.sort.work;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.SortDetailResponse;
import com.ccaong.warehousingmanager.bean.SortWorkResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.List;

/**
 * @author eyecool
 * @date 2022/9/21
 */
public class SortWarehouseWorkViewModel extends BaseViewModel {

    private MutableLiveData<List<SortWorkResponse.DataDTO>> data;


    public SortWarehouseWorkViewModel() {
        data = new MutableLiveData<>();

    }

    public LiveData<List<SortWorkResponse.DataDTO>> getData() {
        return data;
    }


    public void loadData(String id) {

        HttpRequest.getInstance()
                .outboundRelInfo(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<SortWorkResponse>() {
                    @Override
                    public void success(SortWorkResponse bean) {
                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        } else {
                            Log.e("拣货详情错误", "错误信息" + bean.getMsg());
                        }
                    }
                });
    }
}

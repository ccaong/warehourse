package com.ccaong.warehousingmanager.ui.activity.put.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.PutTaskDetailResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eyecool
 * @date 2022/9/21
 */
public class PutDetailViewModel extends BaseViewModel {

    public MutableLiveData<PutTaskDetailResponse.DataDTO> data;


    public PutDetailViewModel() {
        data = new MutableLiveData<>();
    }

    public LiveData<PutTaskDetailResponse.DataDTO> getData() {
        return data;
    }

    public void loadData(String id) {

        HttpRequest.getInstance()
                .getPutTaskDetail(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<PutTaskDetailResponse>() {
                    @Override
                    public void success(PutTaskDetailResponse bean) {
                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        }
                    }
                });
    }
}

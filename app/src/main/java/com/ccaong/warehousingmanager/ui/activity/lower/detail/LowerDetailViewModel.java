package com.ccaong.warehousingmanager.ui.activity.lower.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.PullTaskDetailResponse;
import com.ccaong.warehousingmanager.bean.PutTaskDetailResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * 下架
 *
 * @author caocong
 * @date 2022/9/21
 */
public class LowerDetailViewModel extends BaseViewModel {


    public MutableLiveData<PullTaskDetailResponse.DataDTO> data;


    public LowerDetailViewModel() {
        data = new MutableLiveData<>();
    }

    public LiveData<PullTaskDetailResponse.DataDTO> getData() {
        return data;
    }

    public void loadData(String id) {

        HttpRequest.getInstance()
                .getPullTaskDetail(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<PullTaskDetailResponse>() {
                    @Override
                    public void success(PullTaskDetailResponse bean) {
                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        }
                    }
                });
    }
}

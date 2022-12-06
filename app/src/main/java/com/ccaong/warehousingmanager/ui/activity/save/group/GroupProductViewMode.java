package com.ccaong.warehousingmanager.ui.activity.save.group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * @author eyecool
 * @date 2022/9/19
 */
public class GroupProductViewMode extends BaseViewModel {

    public MutableLiveData<InboundDetailResponse.DataDTO> data;

    public GroupProductViewMode() {
        data = new MutableLiveData<>();

    }


    public LiveData<InboundDetailResponse.DataDTO> getData() {
        return data;
    }

    public void loadData(String id) {

        HttpRequest.getInstance()
                .getInboundInfoById(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InboundDetailResponse>() {
                    @Override
                    public void success(InboundDetailResponse bean) {
                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        }
                    }
                });
    }
}

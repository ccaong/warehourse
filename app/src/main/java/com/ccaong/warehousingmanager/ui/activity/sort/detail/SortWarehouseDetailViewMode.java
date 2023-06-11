package com.ccaong.warehousingmanager.ui.activity.sort.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.SortDetailResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

import java.util.List;

/**
 * @author caocong
 * @date 2022/9/19
 */
public class SortWarehouseDetailViewMode extends BaseViewModel {

    private MutableLiveData<List<SortDetailResponse.DataDTO>> data;


    public SortWarehouseDetailViewMode() {
        data = new MutableLiveData<>();
    }

    public LiveData<List<SortDetailResponse.DataDTO>> getData() {
        return data;
    }


    public void loadData(String id) {

        HttpRequest.getInstance()
                .getOutboundRelInfo(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<SortDetailResponse>() {
                    @Override
                    public void success(SortDetailResponse bean) {

                        if (bean.getCode() == 200) {
                            data.postValue(bean.getData());
                        } else {
                            Log.e("拣货详情错误", "" + bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}

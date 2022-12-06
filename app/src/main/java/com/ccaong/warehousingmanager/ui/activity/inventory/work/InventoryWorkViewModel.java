package com.ccaong.warehousingmanager.ui.activity.inventory.work;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.InventoryDetailResponse;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;

/**
 * @author eyecool
 * @date 2022/9/21
 */
public class InventoryWorkViewModel extends BaseViewModel {


    public MutableLiveData<InventoryDetailResponse.DataDTO.ListDTO> productData;


    public InventoryWorkViewModel() {
        productData = new MutableLiveData<>();
    }

    public LiveData<InventoryDetailResponse.DataDTO.ListDTO> getData() {
        return productData;
    }

    /**
     * @param id   任务id
     * @param code 载具编码
     */
    public void loadData(String id, String code) {

        HttpRequest.getInstance()
                .getInventoryTaskDetailById(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<InventoryDetailResponse>() {
                    @Override
                    public void success(InventoryDetailResponse bean) {
                        if (bean.getCode() == 200) {
                            for (InventoryDetailResponse.DataDTO.ListDTO data : bean.getData().getList()) {
                                if (data.getContainerCode().equals(code)) {
                                    productData.postValue(data);
                                }
                            }
                        }
                    }
                });
    }
}

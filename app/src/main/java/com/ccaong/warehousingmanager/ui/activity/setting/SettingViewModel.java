package com.ccaong.warehousingmanager.ui.activity.setting;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.config.Constant;
import com.orhanobut.hawk.Hawk;

/**
 * @author caocong
 * @date 2022/10/22
 */
public class SettingViewModel extends BaseViewModel {

    public MutableLiveData<String> goodsRules;
    public MutableLiveData<String> localRules;
    public MutableLiveData<String> containerRules;

    public SettingViewModel() {
        goodsRules = new MutableLiveData<>();
        containerRules = new MutableLiveData<>();
        localRules = new MutableLiveData<>();

    }


    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);
        init();
    }

    public void init() {
        goodsRules.postValue(Hawk.get(Constant.RULES_SHOW_GOOD, "0000_SKU_0000_A00"));
        localRules.postValue(Hawk.get(Constant.RULES_SHOW_LOCAL, "0_0_0_0"));
        containerRules.postValue(Hawk.get(Constant.RULES_SHOW_CONTAINER, "无"));
    }
}

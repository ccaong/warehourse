package com.ccaong.warehousingmanager.ui.activity.login;

import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.config.Constant;
import com.orhanobut.hawk.Hawk;

/**
 * @author caocong
 * @date 2022/9/16
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<String> userName;
    public MutableLiveData<String> userPwd;

    public LoginViewModel() {
        userName = new MutableLiveData<>();
        userPwd = new MutableLiveData<>();
        init();
    }

    private void init() {
        userName.postValue(Hawk.get(Constant.LOGIN_NAME, ""));
        userPwd.postValue(Hawk.get(Constant.LOGIN_PWD, ""));
    }
}

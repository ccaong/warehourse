package com.ccaong.warehousingmanager.ui.activity.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;

/**
 * @author caocong
 * @date 2022/9/18
 */
public class MainViewModel extends BaseViewModel {
    private MutableLiveData<String> status;


    public LiveData<String> getStatus() {
        return status;
    }

    public void setRfidStatus(String s) {
        Log.e("MainActivity", "RFID状态" + s);
        status.postValue(s);
    }

    public MainViewModel() {
        status = new MutableLiveData<>();
    }
}

package com.ccaong.warehousingmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;


/**
 * Fragment的基类
 *
 * @param <DB> data binding
 * @param <VM> view model
 * @author ccaong
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends Fragment {

    private static final String TAG = BaseFragment.class.getName();

    public FragmentActivity mActivity;
    public Context mContext;

    protected DB mDataBinding;

    protected VM mViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");

        mActivity = (FragmentActivity) context;
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "【onCreate】");

        Bundle args = getArguments();
        if (args != null) {
            Log.e(TAG, "【onCreate】" + "handleArguments");
            handleArguments(args);
        }

        Log.e(TAG, "【onCreate】" + "initViewModel");
        initViewModel();

        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "【onCreateView】");

        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        Log.e(TAG, "【onCreateView】" + "bindViewModel");
        bindViewModel();

        mDataBinding.setLifecycleOwner(this);

        Log.e(TAG, "【onCreateView】" + "init");
        init();

        return mDataBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
        }
    }


    /**
     * 处理参数
     *
     * @param args 参数容器
     */
    protected void handleArguments(Bundle args) {

    }


    /**
     * 获取当前页面的布局资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化ViewModel
     */
    protected abstract void initViewModel();

    /**
     * 绑定ViewModel
     */
    protected abstract void bindViewModel();

    /**
     * 初始化
     */
    protected abstract void init();
}

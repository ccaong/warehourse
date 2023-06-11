package com.ccaong.warehousingmanager.ui.activity.main.mine;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseFragment;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.UserInfoResponse;
import com.ccaong.warehousingmanager.bean.WareHouseResponseBean;
import com.ccaong.warehousingmanager.config.Constant;
import com.ccaong.warehousingmanager.databinding.FragmentMineBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.ui.activity.login.LoginActivity;
import com.ccaong.warehousingmanager.ui.activity.setting.SettingActivity;
import com.ccaong.warehousingmanager.util.AppUtils;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * @author caocong
 * @date 2022/9/18
 */
public class MineFragment extends BaseFragment<FragmentMineBinding, BaseViewModel> {

    private static final String TAG = MineFragment.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        mDataBinding.tvRemark.setText("当前仓库：" + Hawk.get(Constant.STOREHOUSE_NAME));

        mDataBinding.tvVersion.setText("V_" + AppUtils.getVersionName(mActivity));
        mDataBinding.btnLogout.setOnClickListener(view -> logout());
        mDataBinding.btnChoose.setOnClickListener(view -> getWarehouse());
        mDataBinding.btnSetting.setOnClickListener(view -> startActivity(new Intent(mActivity, SettingActivity.class)));
        loadUserInfo();
    }

    private void loadUserInfo() {

        HttpRequest.getInstance()
                .getUserInfo(Hawk.get(Constant.USER_NAME))
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<UserInfoResponse>() {
                    @Override
                    public void success(UserInfoResponse bean) {
                        mDataBinding.tvName.setText(bean.getData().getNickname());
                        mDataBinding.tvPhone.setText(bean.getData().getPhone());
                        mDataBinding.tvEmail.setText(bean.getData().getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    /**
     * 获取当前账户的仓库列表
     */
    private void getWarehouse() {
        String userName = Hawk.get(Constant.LOGIN_NAME, "");


        HttpRequest.getInstance()
                .getStorehouse(userName)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<WareHouseResponseBean>() {
                    @Override
                    public void success(WareHouseResponseBean bean) {
                        Log.e(TAG, bean.getMsg());

                        if (bean.getCode() == 200) {
                            if (bean.getData().size() == 1) {
                                Toast.makeText(mActivity, "不需要选择", Toast.LENGTH_SHORT).show();
                            } else {
                                showSingleChoiceDialog(bean.getData());
                            }
                        } else {
                            Toast.makeText(mActivity, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, e.toString());
                    }
                });
    }

    int yourChoice;

    /**
     * 选择仓库的dialog
     */
    private void showSingleChoiceDialog(List<WareHouseResponseBean.DataDTO> list) {

        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getName();
        }

        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(mActivity);
        singleChoiceDialog.setTitle("请选择仓库");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                (dialog, which) -> yourChoice = which);
        singleChoiceDialog.setPositiveButton("确定",
                (dialog, which) -> {
                    if (yourChoice != -1) {
                        Toast.makeText(mActivity,
                                "您选择了" + items[yourChoice],
                                Toast.LENGTH_SHORT).show();
                        Hawk.put(Constant.STOREHOUSE_ID, list.get(yourChoice).getId());
                        Hawk.put(Constant.STOREHOUSE_NAME, list.get(yourChoice).getName());
                        Hawk.put(Constant.STOREHOUSE_TYPE, list.get(yourChoice).getType());
                    }
                });
        singleChoiceDialog.show();
    }


    private void logout() {

        Map request = new HashMap<String, String>();
        request.put("username", Hawk.get(Constant.USER_NAME));
        HttpRequest.getInstance()
                .logout(request)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Response<Void>>() {
                    @Override
                    public void success(Response<Void> bean) {
                        Log.e(TAG, "SUCCESS");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Hawk.delete(Constant.TOKEN);
                        startActivity(new Intent(mActivity, LoginActivity.class));
                        mActivity.finish();
                    }
                });
    }
}

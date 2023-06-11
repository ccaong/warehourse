package com.ccaong.warehousingmanager.ui.activity.main.menu;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseFragment;
import com.ccaong.warehousingmanager.databinding.FragmentMenuBinding;
import com.ccaong.warehousingmanager.ui.activity.goods.GoodsQueryActivity;
import com.ccaong.warehousingmanager.ui.activity.inventory.InventoryActivity;
import com.ccaong.warehousingmanager.ui.activity.lower.LowerShelvesActivity;
import com.ccaong.warehousingmanager.ui.activity.lower.LowerShelvesOrderListActivity;
import com.ccaong.warehousingmanager.ui.activity.main.MainActivity;
import com.ccaong.warehousingmanager.ui.activity.main.MainViewModel;
import com.ccaong.warehousingmanager.ui.activity.move.MoveActivity;
import com.ccaong.warehousingmanager.ui.activity.put.PutShelvesActivity;
import com.ccaong.warehousingmanager.ui.activity.put.PutShelvesOrderListActivity;
import com.ccaong.warehousingmanager.ui.activity.quick.QuickWarehousingActivity;
import com.ccaong.warehousingmanager.ui.activity.save.SaveWareHouseActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.SortWareHouseActivity;
import com.ccaong.warehousingmanager.ui.activity.sort.SortWareHouseOrderListActivity;
import com.ccaong.warehousingmanager.ui.activity.vehicle.VehicleQueryActivity;

/**
 * @author caocong
 * @date 2022/9/18
 */
public class MenuFragment extends BaseFragment<FragmentMenuBinding, MainViewModel> {

    boolean show = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(mActivity).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        mDataBinding.ll1.setOnClickListener(view -> startActivity(new Intent(mActivity, SaveWareHouseActivity.class)));

        mDataBinding.ll2.setOnClickListener(view -> startActivity(new Intent(mActivity, SortWareHouseOrderListActivity.class)));

        mDataBinding.ll3.setOnClickListener(view -> startActivity(new Intent(mActivity, InventoryActivity.class)));

        mDataBinding.ll4.setOnClickListener(view -> startActivity(new Intent(mActivity, PutShelvesOrderListActivity.class)));

        mDataBinding.ll5.setOnClickListener(view -> startActivity(new Intent(mActivity, LowerShelvesOrderListActivity.class)));

        mDataBinding.ll6.setOnClickListener(view -> startActivity(new Intent(mActivity, MoveActivity.class)));

        // TODO: 2022/10/10 没有文档
        mDataBinding.ll7.setOnClickListener(view -> Toast.makeText(mActivity, "todo", Toast.LENGTH_SHORT).show());

        mDataBinding.ll8.setOnClickListener(view -> startActivity(new Intent(mActivity, QuickWarehousingActivity.class)));

        // 载具码扫码
        mDataBinding.ll9.setOnClickListener(view -> startActivity(new Intent(mActivity, VehicleQueryActivity.class)));

        mDataBinding.ll10.setOnClickListener(view -> startActivity(new Intent(mActivity, GoodsQueryActivity.class)));

        // 重新连接RFID
        mDataBinding.connect.setOnClickListener(view -> ((MainActivity) mActivity).connectRfid());

        initStatus();
        show = true;
    }

    public void setRfidStatus(String s) {
        if (!show) {
            return;
        }
        switch (s) {
            case "0":
                Log.e("MainActivity", "正在连接");
                mActivity.runOnUiThread(() -> {
                    mDataBinding.tvStatus.setText("RFID正在连接···");
                    mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.theme));
                    mDataBinding.connect.setVisibility(View.GONE);
                });
                break;
            case "1":
                Log.e("MainActivity", "RFID已连接");
                mActivity.runOnUiThread(() -> {
                    mDataBinding.tvStatus.setText("RFID已连接");
                    mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.color_78c257));
                    mDataBinding.connect.setVisibility(View.GONE);
                });

                break;
            case "2":
                Log.e("MainActivity", "RFID未连接");
                mActivity.runOnUiThread(() -> {
                    mDataBinding.tvStatus.setText("RFID未连接");
                    mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.red));
                    mDataBinding.connect.setVisibility(View.VISIBLE);
                    Log.e("MainActivity", "获取" + mDataBinding.tvStatus.getText().toString());
                });
                break;
            default:
                break;
        }

    }


    /**
     * 设置RFID连接状态
     */
    public void initStatus() {
        mViewModel.getStatus().observe(this, s -> {
            Log.e("MainActivity", "获取到的状态" + s);
            switch (s) {
                case "0":
                    Log.e("MainActivity", "正在连接");
                    mActivity.runOnUiThread(() -> {
                        mDataBinding.tvStatus.setText("RFID正在连接···");
                        mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.theme));
                        mDataBinding.connect.setVisibility(View.GONE);
                    });
                    break;
                case "1":
                    Log.e("MainActivity", "RFID已连接");
                    mActivity.runOnUiThread(() -> {
                        mDataBinding.tvStatus.setText("RFID已连接");
                        mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.color_78c257));
                        mDataBinding.connect.setVisibility(View.GONE);
                    });

                    break;
                case "2":
                    Log.e("MainActivity", "RFID未连接");
                    mActivity.runOnUiThread(() -> {
                        mDataBinding.tvStatus.setText("RFID未连接");
                        mDataBinding.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.red));
                        mDataBinding.connect.setVisibility(View.VISIBLE);
                        Log.e("MainActivity", "获取" + mDataBinding.tvStatus.getText().toString());
                    });
                    break;
                default:
                    break;
            }
        });


    }
}

package com.ccaong.warehousingmanager.ui.activity.vehicle;

import static com.ccaong.warehousingmanager.App.getContext;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccaong.warehousingmanager.BR;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.adapter.CommonAdapter;
import com.ccaong.warehousingmanager.bean.ContainerInfoResponse;
import com.ccaong.warehousingmanager.databinding.ActivityVehicleQueryBinding;
import com.ccaong.warehousingmanager.http.HttpDisposable;
import com.ccaong.warehousingmanager.http.HttpFactory;
import com.ccaong.warehousingmanager.http.HttpRequest;
import com.ccaong.warehousingmanager.util.CodeParseUtils;

/**
 * 载具查询
 *
 * @author eyecool
 * @date 2022/9/19
 */
public class VehicleQueryActivity extends BaseActivity<ActivityVehicleQueryBinding, VehicleQueryViewModel> {
    private static final String TAG = VehicleQueryActivity.class.getSimpleName();
    CommonAdapter<ContainerInfoResponse.DataDTO> commonAdapter;

    @Override
    protected boolean isSupportRfid() {
        return true;
    }

    @Override
    protected boolean isSupportScan() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_vehicle_query;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(VehicleQueryViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        actionBar.setTitle("载具查询");
        initRecycler();

        mDataBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.scan) {
            startScanRFID();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void scanResult(String result) {
        if (CodeParseUtils.isCodeContainer(result)){
            query(result);
        }
    }

    @Override
    protected void rfidResult(String result) {
        query(result);
    }


    private void initRecycler() {
        commonAdapter = new CommonAdapter<>(R.layout.item_vehicle, BR.vehicleData);
        mDataBinding.rvList.setAdapter(commonAdapter);
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void query(String code) {
        mDataBinding.tvVehicle.setText(code);

        HttpRequest.getInstance()
                .getGoodsByContainerCode(code)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ContainerInfoResponse>() {
                    @Override
                    public void success(ContainerInfoResponse bean) {
                        Log.e(TAG, bean.getMsg());
                        if (bean.getCode() == 200) {
                            commonAdapter.onItemDatasChanged(bean.getData());
                        } else {
                            commonAdapter.onItemDatasChanged(null);
                            Toast.makeText(VehicleQueryActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



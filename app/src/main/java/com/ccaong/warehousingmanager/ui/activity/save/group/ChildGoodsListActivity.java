package com.ccaong.warehousingmanager.ui.activity.save.group;

import android.content.Intent;

import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.base.viewmodel.BaseViewModel;
import com.ccaong.warehousingmanager.bean.ChildGoodBean;
import com.ccaong.warehousingmanager.bean.GroupGoodBean;
import com.ccaong.warehousingmanager.databinding.ActivityChildGoodListBinding;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildGoodsListActivity extends BaseActivity<ActivityChildGoodListBinding, BaseViewModel> {

    String code;
    String name;
    int num;

    String info;
    int quantity;
    List<ChildGoodBean> tempList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_child_good_list;
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        code = intent.getStringExtra("CODE");
        name = intent.getStringExtra("NAME");
        num = intent.getIntExtra("NUM", 1);

        info = intent.getStringExtra("SUPPORT_INFO");
        quantity = intent.getIntExtra("QUANTITY", 1);

        tempList = Hawk.get("TEMP");
        Hawk.delete("TEMP");


    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        actionBar.setTitle("点收组盘详情");

        mDataBinding.tvCode.setText(code);
        mDataBinding.tvName.setText(name);
        mDataBinding.tvNum.setText("配套数量：" + quantity);
        switch (info) {
            case "0":
                mDataBinding.tvInfo.setText("一箱多套");
                break;
            case "1":
                mDataBinding.tvInfo.setText("多箱一套");
                break;
            case "2":
                mDataBinding.tvInfo.setText("一箱一套");
                break;
        }

        info();
    }

    private void info() {

        List<ChildGoodBean> list = new ArrayList<>();

        for (ChildGoodBean childGoodBean : tempList) {
            if (childGoodBean.getSku().equals(code)) {
                list.add(childGoodBean);
            }
        }

        Map<String, List<ChildGoodBean>> map = splitGroup(list);

        String nameStr = "";
        String numStr = "";

        for (Map.Entry<String, List<ChildGoodBean>> entry : map.entrySet()) {


            for (int i = 1; i <= quantity; i++) {

                String codeName = "";
                if (i < 10) {
                    codeName = "00" + i;
                } else if (i < 100) {
                    codeName = "0" + i;
                } else {
                    codeName = "" + i;
                }

                int childNum = 0;
                for (ChildGoodBean childGoodBean : entry.getValue()) {
                    if (childGoodBean.getCode().equals(codeName) && childGoodBean.getSerialNo().equals(entry.getKey())) {
                        childNum++;
                    }
                }

                nameStr = nameStr + "配套子项：" + codeName + ",序列号" + entry.getKey() + "\n";
                numStr = numStr + "已点数量：" + childNum + "\n";
            }

            nameStr = nameStr + "\n";

            numStr = numStr + "\n";
        }


        mDataBinding.name.setText(nameStr);
        mDataBinding.num.setText(numStr);
    }


    public static Map<String, List<ChildGoodBean>> splitGroup(List<ChildGoodBean> list) {
        //初始化一个map
        Map<String, List<ChildGoodBean>> map = new HashMap<>();
        for (ChildGoodBean childGoodBean : list) {
            String key = childGoodBean.getSerialNo();
            if (map.containsKey(key)) {
                //如果map中存在以此id作为的key,将数据元素存放当前key的list集合中
                map.get(key).add(childGoodBean);
            } else {
                //map中不存在以此id作为的key，新建key用来存放数据
                List<ChildGoodBean> userList = new ArrayList<>();
                userList.add(childGoodBean);
                map.put(key, userList);
            }
        }
        return map;
    }

}

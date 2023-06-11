import android.util.ArrayMap;

import com.ccaong.warehousingmanager.bean.ChildGoodBean;
import com.ccaong.warehousingmanager.test.TestBean;
import com.ccaong.warehousingmanager.util.ChildGoodsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    static List<ChildGoodBean> list;

    public static void main(String[] args) {
        System.out.println("哈哈哈哈");
        init();
    }


    public static void init() {

        int num = 2;
        list = new ArrayList<>();

        list.add(new ChildGoodBean("0011", "001","001"));
        list.add(new ChildGoodBean("0011", "002","001"));
        list.add(new ChildGoodBean("0011", "001","001"));
        list.add(new ChildGoodBean("0011", "001","001"));
        list.add(new ChildGoodBean("0011", "003","001"));
//        list.add(new ChildGoodBean("0022", "001"));
//        list.add(new ChildGoodBean("0022", "002"));

        Map<String, Object> result = new HashMap<>();

        result = ChildGoodsUtils.getList(list, list.get(list.size() - 1).getSku(),list.get(list.size() - 1).getSerialNo(), 3);

        System.out.println("结果" + result.get("result"));
        List<ChildGoodBean> aaa = (List<ChildGoodBean>) result.get("list");
        for (ChildGoodBean childGoodBean : aaa) {
            System.out.println("结果" + childGoodBean.getSku() + childGoodBean.getCode());
        }
//
//        Map<String, List<TestBean>> map = list.stream().collect(Collectors.groupingBy(t -> t.getSku()));
//
//        System.out.println(map);
//
//        for (Map.Entry<String, List<TestBean>> entry : map.entrySet()) {
//            System.out.println("===========================");
//            System.out.println("SKU：" + entry.getKey());
//
//            List<String> codeList = new ArrayList();
//            for (int i = 1; i <= num; i++) {
//                if (i < 10) {
//                    codeList.add("00" + i);
//                } else if (i < 100) {
//                    codeList.add("0" + i);
//                } else {
//                    codeList.add("" + i);
//                }
//            }
//            List<Integer> aaa = new ArrayList<>();
//            for (String code : codeList) {
//                int i = 0;
//                for (TestBean testBean : entry.getValue()) {
//                    if (testBean.getCode().equals(code)) {
//                        i++;
//                    }
//                }
//                aaa.add(i);
//            }
//
//            int tempNum = 0;
//            for (int a : aaa) {
//                if (a == 0) {
//                    System.out.println("SKU：" + entry.getKey() + "错误1，有的没有扫码");
//                    break;
//                } else {
//                    if (tempNum == 0) {
//                        tempNum = a;
//                    } else {
//                        if (tempNum == a) {
//                            tempNum = a;
//                        } else {
//                            System.out.println("SKU：" + entry.getKey() + "错误2数量不一致");
//                            tempNum = 0;
//                            break;
//                        }
//                    }
//                }
//            }
//            System.out.println("SKU：" + entry.getKey() + "有" + tempNum + "套");
//        }
    }
}

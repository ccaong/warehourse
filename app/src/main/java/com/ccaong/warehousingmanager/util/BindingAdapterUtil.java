package com.ccaong.warehousingmanager.util;


import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.ccaong.warehousingmanager.bean.ContainerInfoResponse;
import com.ccaong.warehousingmanager.bean.PullTaskDetailResponse;
import com.ccaong.warehousingmanager.bean.SortListResponse;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {


    @BindingAdapter("int2String")
    public static void int2String(TextView textView, int i) {
        textView.setText("" + i);
    }

    @BindingAdapter("double2String")
    public static void double2String(TextView textView, double i) {
        textView.setText("" + i);
    }


    /**
     * 入库单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("inbound_status")
    public static void inboundStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "未提交";
                break;
            case "1":
                str = "待点收";
                break;
            case "2":
                str = "待组盘";
                break;
            case "3":
                str = "待上架";
                break;
            case "4":
                str = "上架中";
                break;
            case "5":
                str = "入库完成";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    /**
     * 入库单来源
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("inbound_source")
    public static void inboundSource(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "按计划";
                break;
            case "1":
                str = "按收货单";
                break;
            case "2":
                str = "直接入库";
                break;
            default:
                str = "未知";
        }
        textView.setText(str);
    }


    /**
     * 入库单类型
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("inbound_type")
    public static void inboundType(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }

        String str;
        switch (i) {
            case "0":
                str = "生产入库";
                break;
            case "1":
                str = "采购入库";
                break;
            case "2":
                str = "退货入库";
                break;
            case "3":
                str = "快速入库";
                break;
            case "4":
                str = "移库入库";
                break;
            default:
                str = "未知";
        }
        textView.setText(str);
    }

    /**
     * 盘点单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("inventory_status")
    public static void inventoryStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "1":
                str = "待下架";
                break;
            case "2":
                str = "正在下架";
                break;
            case "3":
                str = "待盘点";
                break;
            case "4":
                str = "正在盘点";
                break;
            case "5":
                str = "等待上架";
                break;
            case "6":
                str = "正在上架";
                break;
            case "7":
                str = "盘点完成";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    /**
     * 盘点单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("inventory_product_status")
    public static void inventoryProductStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "1":
                str = "已盘点";
                break;
            case "0":
                str = "未盘点";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    /**
     * 上架任务状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("put_status")
    public static void putStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "1":
                str = "待点收";
                break;
            case "2":
                str = "待组盘";
                break;
            case "3":
                str = "待上架";
                break;
            case "4":
                str = "上架中";
                break;
            case "5":
                str = "入库完成";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }

    /**
     * 上架任务来源
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("put_source")
    public static void putSource(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "入库单";
                break;
            case "1":
                str = "盘点单";
                break;
            case "2":
                str = "拣货单";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    /**
     * 下架任务状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("pull_status")
    public static void pullStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "待下架";
                break;
            case "1":
                str = "正在下架";
                break;
            case "2":
                str = "正在拣货";
                break;
            case "3":
                str = "下架完成";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }

    /**
     * 下架任务状态
     *
     * @param textView
     * @param dto
     */
    @BindingAdapter("lower_status")
    public static void lowerStatus(TextView textView, PullTaskDetailResponse.DataDTO.ResultDTO dto) {
        if (dto == null || dto.getList() == null || dto.getList().size() == 0) {
            textView.setText("");
            return;
        }
        String str = "";
        for (PullTaskDetailResponse.DataDTO.ResultDTO.ListDTO listDTO : dto.getList()) {
            switch (listDTO.getStatus()) {
                case "0":
                    str = str + "未下架" + "\n";
                    break;
                case "1":
                    str = str + "已下架" + "\n";
                    break;
                case "2":
                    str = str + "下架完成" + "\n";
                    break;
                default:
                    str = str + "其他" + "\n";
            }
        }
        if (str.length() > 2) {
            str = str.substring(0, str.length() - 1);
        }
        textView.setText(str);
    }


    /**
     * 载具编码
     *
     * @param textView
     * @param dto
     */
    @BindingAdapter("lower_container")
    public static void lowerContainer(TextView textView, PullTaskDetailResponse.DataDTO.ResultDTO dto) {
        if (dto == null || dto.getList() == null || dto.getList().size() == 0) {
            textView.setText("");
            return;
        }
        String str = "";
        for (PullTaskDetailResponse.DataDTO.ResultDTO.ListDTO listDTO : dto.getList()) {
            str = str + listDTO.getContainerCode() + "　\n";
        }
        if (str.length() > 2) {
            str = str.substring(0, str.length() - 1);
        }
        textView.setText(str);
    }

    /**
     * 下架任务来源
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("pull_source")
    public static void pullSource(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "分拣单";
                break;
            case "1":
                str = "盘点单";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    @BindingAdapter("container_title")
    public static void zaiJuTitle(TextView textView, ContainerInfoResponse.DataDTO bean) {
        textView.setText(bean.getGoodsName() + bean.getSpecificationDesc());
    }

    @BindingAdapter("sort_id")
    public static void sortId(TextView textView, SortListResponse.RowsDTO.ListDTO bean) {

        if (bean.getRelNumber() != null) {
            textView.setText(bean.getOrderNumber() + "\n" + bean.getRelNumber());
        } else {
            textView.setText(bean.getOrderNumber());
        }
    }

    @BindingAdapter("sort_location")
    public static void sortLocation(TextView textView, SortListResponse.RowsDTO.ListDTO bean) {
        if (bean.getRelList() == null) {
            return;
        }
        String text = "";
        for (SortListResponse.RowsDTO.ListDTO.RelListDTO relsDTO : bean.getRelList()) {
            text = text + relsDTO.getLocationCode() + "  " + relsDTO.getContainerSerialNumber() + "\n";
        }
        if (text.length() > 2) {
            text = text.substring(0, text.length() - 1);
        }
        textView.setText(text);
    }

    /**
     * 拣货单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("sort_status")
    public static void sortStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "未生成";
                break;
            case "1":
                str = "未提交";
                break;
            case "2":
                str = "待下架";
                break;
            case "3":
                str = "拣货中";
                break;
            case "4":
                str = "拣货完成";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    /**
     * 移库货单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("move_status")
    public static void moveStatus(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "未提交";
                break;
            case "1":
                str = "已提交";
                break;
            case "2":
                str = "正在下架";
                break;
            case "3":
                str = "出库完成";
                break;
            case "4":
                str = "扫码组盘";
                break;
            case "5":
                str = "正在上架";
                break;
            case "6":
                str = "待审核";
                break;
            case "7":
                str = "已审核";
                break;

            default:
                str = "其他";
        }
        textView.setText(str);
    }

    /**
     * 拣货单状态
     *
     * @param textView
     * @param i
     */
    @BindingAdapter("move_type")
    public static void moveType(TextView textView, String i) {
        if (i == null) {
            textView.setText("");
            return;
        }
        String str;
        switch (i) {
            case "0":
                str = "物资移库";
                break;
            case "1":
                str = "载具移库";
                break;
            case "2":
                str = "载具移位";
                break;
            case "3":
                str = "物资移位";
                break;
            default:
                str = "其他";
        }
        textView.setText(str);
    }


    @BindingAdapter("visibility")
    public static void setViewVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }


}

package com.ccaong.warehousingmanager.http;


import com.ccaong.warehousingmanager.bean.AreaTypeResponse;
import com.ccaong.warehousingmanager.bean.CommonResponse;
import com.ccaong.warehousingmanager.bean.ContainerInfoResponse;
import com.ccaong.warehousingmanager.bean.ContainerStatusResponse;
import com.ccaong.warehousingmanager.bean.DeptListResponse;
import com.ccaong.warehousingmanager.bean.EmptyResponse;
import com.ccaong.warehousingmanager.bean.GoodsTypeListResponse;
import com.ccaong.warehousingmanager.bean.InOrderNumberResponse;
import com.ccaong.warehousingmanager.bean.InboundDetailResponse;
import com.ccaong.warehousingmanager.bean.InboundListResponse;
import com.ccaong.warehousingmanager.bean.InventoryDetailResponse;
import com.ccaong.warehousingmanager.bean.InventoryListResponse;
import com.ccaong.warehousingmanager.bean.LoginResponseBean;
import com.ccaong.warehousingmanager.bean.PullTaskDetailResponse;
import com.ccaong.warehousingmanager.bean.PullTaskListResponse;
import com.ccaong.warehousingmanager.bean.PutTaskDetailResponse;
import com.ccaong.warehousingmanager.bean.PutTaskListResponse;
import com.ccaong.warehousingmanager.bean.ResultResponse;
import com.ccaong.warehousingmanager.bean.SortDetailResponse;
import com.ccaong.warehousingmanager.bean.SortListResponse;
import com.ccaong.warehousingmanager.bean.SortWorkResponse;
import com.ccaong.warehousingmanager.bean.UserInfoResponse;
import com.ccaong.warehousingmanager.bean.WareHouseResponseBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ccaong
 */
public interface Api {

    /**
     * 普通登陆
     *
     * @param map loginName : 用户名
     *            password  : 密码
     * @return 用户信息
     */
    @POST("login")
    Observable<LoginResponseBean> login(@Body Map<String, String> map);


    /**
     * 退出登录
     *
     * @param map userName 登录用户id
     * @return 结果
     */
    @POST("logout")
    Observable<Response<Void>> logout(@Body Map<String, String> map);

    /**
     * 获取当前登录用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GET("getPersonInfo")
    Observable<UserInfoResponse> getUserInfo(@Query("username") String username);


    /**
     * 获取仓库列表
     *
     * @return 仓库信息
     */
    @GET("getStorehouse")
    Observable<WareHouseResponseBean> getStorehouse();


    /**
     * 获取入库单列表
     *
     * @param pageNum  页码
     * @param pageSize 数量
     * @return 列表
     */
    @GET("getInboundOrder")
    Observable<InboundListResponse> getInboundOrder(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 获取入库单详情
     *
     * @param inboundId
     * @return
     */
    @GET("getInboundInfoById")
    Observable<InboundDetailResponse> getInboundInfoById(@Query("inboundId") String inboundId);


    /**
     * 点收组盘入库
     *
     * @param map
     * @return
     */
    @POST("groupDisk")
    Observable<CommonResponse> submitGroupDisk(@Body Map<String, Object> map);

    /**
     * 校验容器在任务中是否存在
     *
     * @param taskId
     * @param containerCode
     * @return
     */
    @GET("checkContainerInTask")
    Observable<AreaTypeResponse> checkContainerInTask(@Query("taskId") String taskId, @Query("containerCode") String containerCode);


    /**
     * 获取容器是否存在
     *
     * @param containerCode
     * @return
     */
    @GET("checkContainerCode")
    Observable<ContainerStatusResponse> checkContainerCode(@Query("containerCode") String containerCode);


    /**
     * 新增容器
     *
     * @param map
     * @return
     */
    @POST("addContainer")
    Observable<EmptyResponse> addContainer(@Body Map<String, String> map);


    /**
     * 获取拣货单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("outboundRelNotStartList")
    Observable<SortListResponse> outboundRelNotStartList(@Query("storehouseInfoId") String id, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 获取拣货单详情
     *
     * @param id
     * @return
     */
    @GET("getOutboundRelInfo/{id}")
    Observable<SortDetailResponse> getOutboundRelInfo(@Path("id") String id);

    /**
     * 根据载具id获取详情
     *
     * @param id
     * @return
     */
    @GET("outboundRelInfo")
    Observable<SortWorkResponse> outboundRelInfo(@Query("containerSerialNum") String id);

    /**
     * 完成拣货
     *
     * @param id
     * @return
     */
    @GET("finishPick")
    Observable<EmptyResponse> finishPick(@Query("relId") String id);


    /**
     * 获取盘点单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("getInventoryTask")
    Observable<InventoryListResponse> getInventoryTask(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 获取盘点单详情
     *
     * @param inboundId
     * @return
     */
    @GET("getInventoryTaskDetailById")
    Observable<InventoryDetailResponse> getInventoryTaskDetailById(@Query("taskId") String inboundId);


    /**
     * 盘点结果上送
     *
     * @param map
     * @return
     */
    @POST("submitInventoryResult")
    Observable<ResultResponse> submitInventoryResult(@Body Map<String, String> map);


    /**
     * 修改盘点单状态（暂时不需要）
     *
     * @param map
     * @return
     */
    @POST("changeInventoryTaskStatus")
    Observable<ResultResponse> changeInventoryTaskStatus(@Body Map<String, String> map);


    /**
     * 获取上架单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("getPutTaskList")
    Observable<PutTaskListResponse> getPutTaskList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 获取上架单详情
     *
     * @param inboundId
     * @return
     */
    @GET("getPutTaskDetail")
    Observable<PutTaskDetailResponse> getPutTaskDetail(@Query("taskId") String inboundId);


    /**
     * 自动上架
     *
     * @param taskId
     * @return
     */
    @GET("autoShelving")
    Observable<EmptyResponse> autoShelving(@Query("taskId") String taskId);


    /**
     * 手动上架
     *
     * @param map
     * @return
     */
    @POST("manualWarehouse")
    Observable<EmptyResponse> manualWarehouse(@Body Map<String, Object> map);


    /**
     * 获取区域类型，用于判断库区是否为智能化区域。
     *
     * @param taskId
     * @return
     */
    @GET("getAreaType")
    Observable<AreaTypeResponse> getAreaType(@Query("taskId") String taskId);


    /**
     * 获取下架单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("getPullTaskList")
    Observable<PullTaskListResponse> getPullTaskList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 获取下架单详情
     *
     * @param taskId 下架任务id
     * @return 结果
     */
    @GET("getPullTaskDetail")
    Observable<PullTaskDetailResponse> getPullTaskDetail(@Query("taskId") String taskId);


    /**
     * 自动下架
     *
     * @param taskId 下架任务id
     * @return 结果
     */
    @GET("autoUndercarriage")
    Observable<EmptyResponse> autoUndercarriage(@Query("taskId") String taskId);

    /**
     * 快速入库模块-快速入库
     *
     * @param map
     * @return
     */
    @POST("quickInbound")
    Observable<EmptyResponse> quickInbound(@Body Map<String, Object> map);

    /**
     * 快速入库模块-查询货物类型
     *
     * @return 所有货物类型列表
     */
    @GET("getGoodsTypeList")
    Observable<GoodsTypeListResponse> getGoodsTypeList();


    /**
     * 快速入库模块-获取入库单号
     *
     * @return
     */
    @GET("getInboundOrderNumber")
    Observable<InOrderNumberResponse> getInboundOrderNumber();

    /**
     * 快速入库模块-获取单位列表
     *
     * @return
     */
    @GET("getCustomerList")
    Observable<DeptListResponse> getCustomerList();


    /**
     * 载具查询模块-载具查询
     *
     * @param containerCode
     * @return
     */
    @GET("getGoodsByContainerCode")
    Observable<ContainerInfoResponse> getGoodsByContainerCode(@Query("containerCode") String containerCode);


}

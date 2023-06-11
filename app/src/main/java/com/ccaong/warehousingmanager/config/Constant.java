package com.ccaong.warehousingmanager.config;

/**
 * @author caocong
 * @date 2022/9/17
 */
public class Constant {

    public static final String USER_NAME = "userName";

    public static final String LOGIN_NAME = "loginName";

    public static final String LOGIN_PWD = "login_pwd";

    public static final String REMEMBER_PWD = "remember";

    public static final String BASE_URL = "baseUrl";

    public static final String TOKEN = "token";

    public static final String STOREHOUSE_ID = "storehouseId";

    public static final String STOREHOUSE_NAME = "storehouseName";

    public static final String STOREHOUSE_TYPE = "storehouseType";

    public static final String DEVICE_IMEI = "device_imei";


    public static final String RULES_SHOW_GOOD = "rules_show_good";
    public static final String RULES_SHOW_LOCAL = "rules_show_local";
    public static final String RULES_SHOW_CONTAINER = "rules_show_container";


    public static final String RULES_CODE_LOCAL = "rules_code_local";
    public static final String RULES_CODE_GOOD = "rules_code_good";
    public static final String RULES_CODE_CONTAINER = "rules_code_container";

    public static final String RULES_SIZE = "rules_code_size";
    public static final String RULES_UNIT_LENGTH = "rules_code_unit_length";
    public static final String RULES_UNIT_A_INDEX = "rules_code__unit_a_index";

    public static final String RULES_SKU_INDEX = "rules_code_sku_index";


    /**
     * 盘点单某载具的详情信息
     */
    public static final String PANDIAN_ZAIJU_DATA = "pandian_zaiju_data";

    /**
     * 按下扳机按钮广播
     */
    public static final String START_SCAN_CODE_ACTION = "com.android.action.keyevent.KEYCODE_KEYCODE_SCAN_F_DOWN";

    /**
     * 扫码结果广播
     */
    public static final String SCAN_CODE_RESULT_ACTION = "com.android.serial.BARCODEPORT_RECEIVEDDATA_ACTION";

    public static final String SCANNER_SERVICE_PACKAGE_NAME = "com.zebra.scanner";
    public static final String SCANNER_SERVICE_NAME = "com.zebra.scanner.ScannerJsbService";

    public static final String GPIO_PATH = "/sys/class/EMDEBUG/gpio_ctrl";
    public static final int UART_FOR_UFH = 2;
    public static final int UART_FOR_SCAN = 3;


}

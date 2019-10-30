package com.fkty.mobileiq.distribution.constant;

/**
 * Created by frank_tracy on 2017/12/18.
 */

public class HttpConstant {
    public static String HTTP_COMMON = "http://114.242.25.188:3012";
    public static String HTTP_GET_FOREIGN;
    public static String HTTP_POST_CREATESESSION = HTTP_COMMON + "/createSession";
    public static String HTTP_POST_LOGIN = HTTP_COMMON + "/login";
    public static String HTTP_POST_UPLOAD_FILE;
    public static final String MANUAL_TEST_FIELD = "nts://localhost/?module=forward.data.manager.installuploadresult&type=manual-test";
    public static final String OPEN_TEST_FIELD = "nts://localhost/?module=forward.data.manager.installuploadresult&type=openup-test";
    public static final String UNBLOCK_TEST_FIELD = "nts://localhost/?module=forward.data.manager.installuploadresult&type=unblock-test";

    static
    {
        HTTP_GET_FOREIGN = HTTP_COMMON + "/getRelatedSystemPropertyList";
        HTTP_POST_UPLOAD_FILE = "https://114.242.25.188:4401/uploadMobileResourceFile";
    }
}

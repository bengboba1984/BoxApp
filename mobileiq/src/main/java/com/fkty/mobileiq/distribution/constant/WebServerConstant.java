package com.fkty.mobileiq.distribution.constant;

/**
 * Created by frank_tracy on 2018/3/5.
 */

public class WebServerConstant {

    public static final boolean LC_TEST_SERVER=false;
    public static final String REPORT_WEB_TEST_RESULT = "/reportWebTestResult";
    public static String WEB_POST_SET_SSID ;
    public static String HTTP_GET_BOX_VERSION_URL = "http://120.204.112.182/";//http://ngrok.judecumt.win:10010/box/";
    public static String WEB_COMMON = LC_TEST_SERVER?"http://ngrok.judecumt.win:10023":"http://192.168.188.251";//"http://192.168.43.1:19999";//
//    public static String WEB_COMMON ="http://ngrok.judecumt.win:10023";
    public static String WEB_GET_DOWNLOAD;
    public static String WEB_GET_FILELIST;
    public static String WEB_GET_REBOOT;
    public static String WEB_GET_RESTORE;
    public static String WEB_GET_SET_ACCOUNT;
    public static String WEB_GET_SET_DHCP;
    public static String WEB_GET_SET_BRIDGE;
    public static String WEB_GET_SET_PPPOE;
    public static String WEB_GET_START_CAPTURE;
    public static String WEB_GET_STOP_CAPTURE;
    public static String WEB_POST_DELETFILES;
    public static String WEB_POST_GETTEMPLET;
    public static String WEB_POST_GET_UPLOAD_RESULT;
    public static String WEB_POST_SET_STATIC;
    public static String WEB_POST_SET_URL;
    public static String WEB_POST_START_TEST = WEB_COMMON + "/cgi-bin/box/startTest";
//    public static String WEB_POST_START_TEST = WEB_COMMON + "/startTest";
    public static String WEB_POST_STOP_TEST = WEB_COMMON + "/cgi-bin/box/stopTest";
//    public static String WEB_POST_STOP_TEST = WEB_COMMON + "/stopTest";
    public static String WEB_GET_TESTRESULT;
    public static String WEB_GET_CAPTURE_FILE;
    public static final String HTTP_GET_BOX_VERSION = WEB_COMMON+"/cgi-bin/box/getBoxVersion";
    public static final String UPDATE_FIRMWARE = WEB_COMMON+"/cgi-bin/box/updateFirmware";


    static
    {
        WEB_GET_CAPTURE_FILE=WEB_COMMON;
        WEB_POST_GETTEMPLET = "/getItestorTemplateList";
        WEB_GET_TESTRESULT = WEB_COMMON + "/cgi-bin/box/heartbeat";
//        WEB_GET_TESTRESULT = WEB_COMMON + "/heartbeat";
        WEB_POST_SET_URL = WEB_COMMON + "/cgi-bin/box/setUrl";
        WEB_POST_SET_SSID = WEB_COMMON + "/cgi-bin/box/setSSID";
//        WEB_POST_SET_URL = WEB_COMMON + "/setUrl";
        WEB_POST_GET_UPLOAD_RESULT = WEB_COMMON + "/getInstallUploadResult";
        WEB_GET_SET_ACCOUNT = WEB_COMMON + "/cgi-bin/box/setAccount";
//        WEB_GET_SET_ACCOUNT = WEB_COMMON + "/setAccount";
        WEB_GET_START_CAPTURE = WEB_COMMON + "/cgi-bin/box/startCapture";
        WEB_GET_STOP_CAPTURE = WEB_COMMON + "/cgi-bin/box/stopCapture";
//        WEB_GET_FILELIST = WEB_COMMON + "/getFileList";
//        WEB_POST_DELETFILES = WEB_COMMON + "/deletFiles";
//        WEB_GET_DOWNLOAD = WEB_COMMON + "/download";
        WEB_GET_SET_PPPOE = WEB_COMMON + "/cgi-bin/box/setPPPoe";
//        WEB_GET_SET_PPPOE = WEB_COMMON + "/setPPPoe";
        WEB_POST_SET_STATIC = WEB_COMMON + "/cgi-bin/box/setStaticIp";
//        WEB_POST_SET_STATIC = WEB_COMMON + "/setStaticIp";
        WEB_GET_SET_DHCP = WEB_COMMON + "/cgi-bin/box/setDHCP";
//        WEB_GET_SET_DHCP = WEB_COMMON + "/setDHCP";
        WEB_GET_SET_BRIDGE = WEB_COMMON + "/cgi-bin/box/setBridge";
//        WEB_GET_SET_BRIDGE = WEB_COMMON + "/setBridge";
        WEB_GET_RESTORE = WEB_COMMON + "/cgi-bin/box/restore";
//        WEB_GET_RESTORE = WEB_COMMON + "/restore";
        WEB_GET_REBOOT = WEB_COMMON + "/cgi-bin/box/reboot";
//        WEB_GET_REBOOT = WEB_COMMON + "/reboot";
    }
}

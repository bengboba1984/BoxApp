package com.fkty.mobileiq.distribution.constant;

import android.os.Environment;

/**
 * Created by frank_tracy on 2017/12/7.
 */

public class CommonField {
    public static final String ACTION_MESSAGE = "com.vixtel.mobileiq.distribution.MessageAction";
    public static final String SSID_PRE = "OpenWrt";
    public static final String DHCP = "dhcp";
    public static String DNS;
    public static final int DOWNLOAD_NOT = 4;
    public static final int DOWNLOAD_YES = 3;
    public static final String CAPTURE_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fkty/capture";//"/sdcard/vixtel/distribution";
    public static final String VIDEO_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fkty/video";//"/sdcard/vixtel/distribution";
    public static final String REMOTE_BOX_UPGRADE_URL = "http://mirrors.edge.kernel.org/pub/linux/kernel/v1.2";
    public static final String DEFAULT_PLATEFORM_URL = "http://113.214.250.44/wasu";//"http://218.108.168.177:4100"; //"http://211.136.99.12:4100"

    public static String FORIG_URL_DEFAULT;
    public static String FORIG_URL_LIST;
    public static String FTP;
    public static String HTTP;
    public static final int MODLE_STATUS_FINISH = 3;
    public static final int MODLE_STATUS_READY = 1;
    public static final int MODLE_STATUS_STOP = 4;
    public static final int MODLE_STATUS_TESTING = 2;
    public static final String MODLE_TYPE_KEY = "modeType";
    public static final int MODLE_TYPE_MALFUNCTION_ELIMINATION = 1;
    public static final int MODLE_TYPE_MANUAL_TEST = 3;
    public static final int MODLE_TYPE_OPEN_TEST = 2;
    public static final int MODLE_TYPE_TEST_RECORE = 4;
    public static final String MSG_MODLE_STATUS = "MsgModleStatus";
    public static final String MSG_MODLE_TYPE = "MsgModleType";
    public static final String MSG_TEST_RESULT = "MsgTestResult";
    public static final String MSG_TEST_STATUS = "MsgTestStatus";
    public static final String MSG_TEST_TYPE = "MsgTestType";
    public static final String MSG_TYPE = "MsgType";
    public static String PING = "ping";
    public static final String PPPOE = "pppoe";
    public static String SPEED;
    public static final String STATICIP = "staticIp";
    public static final String BRIDGE = "bridge";
    public static final int TEST_STATUS_FINISH = 3;
    public static final int TEST_STATUS_READY = 1;
    public static final int TEST_STATUS_TESTING = 2;
    public static final int TYPE_DHCP = 2;
    public static final int TYPE_PPPOE = 1;
    public static final int TYPE_STATIC = 3;
    public static final int UPLOAD_NOT = 2;
    public static final int UPLOAD_YES = 1;
    public static final int GROUP_ID_TROUBLE = 1;
    public static final int GROUP_ID_OPEN = 2;
    public static final int GROUP_ID_MANUAL = 3;
    public static final int TEST_TYPE_PING = 1;
    public static final int TEST_TYPE_TRACE = 10;//2;
    public static final int TEST_TYPE_TRACE_SUB = 12;//2;
    public static final int TEST_TYPE_TRACE_HOP = 13;//2;
    public static final int TEST_TYPE_DNS = 5;
    public static final int TEST_TYPE_HTTP = 11;
    public static final int TEST_TYPE_SPEED = 1000;
    public static final String SHOW_SUB_DATA = "路由跳数";

    public static final String[][] UNIT_SERVER={{"省公司","113.214.250.44","21","wasuftp","wasu@2019@","http://113.214.250.44/wasu"}};

    public static String URL_DEFAULT;
    public static String URL_LIST;




    static
    {
        DNS = "dns";
        FTP = "ftp";
        HTTP = "http";
        SPEED = "speed";
        URL_LIST = "urlList";
        URL_DEFAULT = "urlDefault";
        FORIG_URL_LIST = "forigUrlList";
        FORIG_URL_DEFAULT = "forigUrlDefault";
    }
}

package com.fkty.mobileiq.distribution.constant;

/**
 * Created by frank_tracy on 2018/4/10.
 */

public interface NetWorkConstant {
    int UPLOAD_FILE_FAILED = 6;
    int UPLOAD_FILE_ID = 23;
    int UPLOAD_FILE_MSG = 13;
    int UPLOAD_FILE_SUCCESS = 5;
    int START_CAPTURE_FAILED = 1;
    int START_CAPTURE_ID = 21;
    //    int START_CAPTURE_ING = 2;
    int START_CAPTURE_MSG = 11;
    int START_CAPTURE_SUCCESS = 0;
    int STOP_CAPTURE_FAILED = 4;
    int STOP_CAPTURE_ID = 22;
    int STOP_CAPTURE_MSG = 12;
    int STOP_CAPTURE_SUCCESS = 3;
    int GET_CAPTURE_FILE = 24;
    int GET_CAPTURE_FILE_ID = 25;
    int GET_CAPTURE_FILE_SUCCESS = 26;
    String CAPTURE_HTTP_CODE = "Capture_Htttp_code";
    String[][] httpCode = new String[][]{
            {"-3", "等待超时"},
            {"-2", "加密方式不匹配"},
            {"-1", "Init"},
            {"0", "pppoe连接成功"},
            {"646", "当前时间不允许该帐户登录"},
            {"647", "该账户已被禁用"},
            {"648", "该帐户的密码已过期"},
            {"649", "帐户没有拨入的权限"},
            {"691", "原因一：输入帐号和密码错误。核对输入的帐号和密码，注意区分英文的大小写。\n原因二：宽带账户因欠费停机或交费后，帐号没有及时开放。请与客服联系核对帐号状态。"},
            {"709", "更改域上的密码时发生错误。密码可能太短或者与以前使用的密码相匹配。"}
    };
}

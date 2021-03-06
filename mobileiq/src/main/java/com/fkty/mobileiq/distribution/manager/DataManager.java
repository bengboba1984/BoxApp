package com.fkty.mobileiq.distribution.manager;

import android.widget.Toast;

import com.fkty.mobileiq.distribution.bean.LoginInfo;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestShowFieldBean;
import com.fkty.mobileiq.distribution.constant.CommonField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_10;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_11;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_12;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_4;

/**
 * Created by frank_tracy on 2017/12/18.
 */

public class DataManager {
    private static DataManager dataManager;
    private String url;
    private String account;
    private String stbID;
    private String woNumber;
    private String cpu;
    private String hardDisk;
    private String deviceSeq;
    private String appVersion;
    private String memory;
    private String mschapErrcode;
    private String ottConnectReason;
    private String staticIP;
    private String staticSubNet;
    private String staticGate;
    private String staticDNS;
    private String pppoePwd;
    private String pppoeUser;
    private String ootConnectType = CommonField.DHCP;
    private String device;
    private HashMap ftpInfo=new HashMap();
    private List<TestParamsBean> openData = new ArrayList();
    private List<TestParamsBean> manualData = new ArrayList();
    private List<TestParamsBean> troubleData = new ArrayList();
    private LoginInfo loginInfo;
    private List<TestShowFieldBean> dnsField = new ArrayList();
    private List<TestShowFieldBean> traceField = new ArrayList();
    private List<TestShowFieldBean> traceSubField = new ArrayList();
    private List<TestShowFieldBean> httpField = new ArrayList();
    private List<TestShowFieldBean> pingField = new ArrayList();
    private List<TestShowFieldBean> speedField = new ArrayList();

    private List<TestShowFieldBean> dnsUploadField = new ArrayList();
    private List<TestShowFieldBean> traceUploadField = new ArrayList();
    private List<TestShowFieldBean> httpUploadField = new ArrayList();
    private List<TestShowFieldBean> pingUploadField = new ArrayList();
    private List<TestShowFieldBean> speedUploadField = new ArrayList();

    private JSONObject uploadResult=new JSONObject();
    private String resultSeq;
    private boolean showTwice4PPPOE=false;

    public String getResultSeq() {
        return resultSeq;
    }

    public void setResultSeq(String resultSeq) {
        this.resultSeq = resultSeq;
    }

    public JSONObject getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(JSONObject uploadResult) {
        this.uploadResult = uploadResult;
    }

    public static DataManager getInstance()
    {
        if (dataManager == null){
            dataManager = new DataManager();
            dataManager.initialField();
            dataManager.initialUploadFields();
        }
        return dataManager;
    }

    private void initialField(){
        pingField.add(new TestShowFieldBean("avgDelay",true,"平均时延",0,"string","毫秒"));
        pingField.add(new TestShowFieldBean("avgJitter",true,"平均抖动",0,"string","毫秒"));
        pingField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        pingField.add(new TestShowFieldBean("lossPercent",true,"丢包率",0,"string","%"));

        traceField.add(new TestShowFieldBean("avgDelay",true,"平均时延",0,"string","毫秒"));
        traceField.add(new TestShowFieldBean("avgJitter",true,"平均抖动",0,"string","毫秒"));
        traceField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        traceField.add(new TestShowFieldBean("lossPercent",true,"丢包率",0,"int","%"));
        traceField.add(new TestShowFieldBean("hopCount",true,"路由跳数",0,"int",""));
        traceSubField.add(new TestShowFieldBean("timeToLive",true,"时间",0,"string","毫秒"));
        traceSubField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        traceSubField.add(new TestShowFieldBean("avgDelay",true,"平均时延",0,"string","毫秒"));
        traceSubField.add(new TestShowFieldBean("lossPercent",true,"丢包率",0,"int","%"));
        traceSubField.add(new TestShowFieldBean("avgJitter",true,"平均抖动",0,"string","毫秒"));

        httpField.add(new TestShowFieldBean("connectTime",true,"连接时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("firstByteTime",true,"首字节时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
//        httpField.add(new TestShowFieldBean("firstPageTime",true,"首页加载时间",0,"int","毫秒"));
//        httpField.add(new TestShowFieldBean("meanQuality",true,"综合质量",0,"int",""));
        httpField.add(new TestShowFieldBean("pageLoadTime",true,"页面加载时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"string",""));
        httpField.add(new TestShowFieldBean("throughput",true,"吞吐率",0,"int","KB/S"));

        dnsField.add(new TestShowFieldBean("avgReplyTime",true,"平均响应时间",0,"string","毫秒"));
        dnsField.add(new TestShowFieldBean("numberOfAnswers",true,"记录数",0,"int",""));
        dnsField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"string","毫秒"));
//        dnsField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"int",""));
        dnsField.add(new TestShowFieldBean("successPercent",true,"成功率",0,"int","%"));

        speedField.add(new TestShowFieldBean("customerIp",true,"测速客户端IP",0,"string",""));
        speedField.add(new TestShowFieldBean("downloadMaxThroughput",true,"下载峰值速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("downloadThroughput",true,"下载平均速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("uploadMaxThroughput",true,"上传峰值速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("uploadThroughput",true,"上传平均速率",0,"int","Mbps"));

    }

    private void initialUploadFields(){
        pingUploadField.add(new TestShowFieldBean("avgDelay",true,"平均时延",0,"string","毫秒"));
        pingUploadField.add(new TestShowFieldBean("avgJitter",true,"平均抖动",0,"string","毫秒"));
        pingUploadField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("hostIpv4",true,"主机Ipv4",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("lossPackets",true,"丢包数",0,"int",""));
        pingUploadField.add(new TestShowFieldBean("lossPercent",true,"丢包率",0,"string","%"));
        pingUploadField.add(new TestShowFieldBean("maxDelay",true,"最大时延",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("maxJitter",true,"最大抖动",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("minDelay",true,"最小时延",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("minJitter",true,"最小抖动",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("oooPackets",true,"乱序数",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("sendPackets",true,"发包数",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("stdDelay",true,"STD时延",0,"string",""));
        pingUploadField.add(new TestShowFieldBean("stdJitter",true,"STD抖动",0,"string",""));

        traceUploadField.add(new TestShowFieldBean("avgDelay",true,"平均时延",0,"string","毫秒"));
        traceUploadField.add(new TestShowFieldBean("avgJitter",true,"平均抖动",0,"string","毫秒"));
        traceUploadField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        traceUploadField.add(new TestShowFieldBean("lossPercent",true,"丢包率",0,"int","%"));
        traceUploadField.add(new TestShowFieldBean("hopCount",true,"路由跳数",0,"int",""));

        httpUploadField.add(new TestShowFieldBean("connectTime",true,"连接时间",0,"int","毫秒"));
        httpUploadField.add(new TestShowFieldBean("firstByteTime",true,"首字节时间",0,"int","毫秒"));
        httpUploadField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        httpUploadField.add(new TestShowFieldBean("firstPageTime",true,"首页加载时间",0,"int","毫秒"));
        httpUploadField.add(new TestShowFieldBean("meanQuality",true,"综合质量",0,"int",""));
        httpUploadField.add(new TestShowFieldBean("pageLoadTime",true,"页面加载时间",0,"int","毫秒"));
        httpUploadField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"int","毫秒"));
        httpUploadField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"int",""));
        httpUploadField.add(new TestShowFieldBean("throughput",true,"吞吐率",0,"int","Byte/S"));

        dnsUploadField.add(new TestShowFieldBean("avgReplyTime",true,"平均响应时间",0,"string","毫秒"));
        dnsUploadField.add(new TestShowFieldBean("numberOfAnswers",true,"记录数",0,"int",""));
        dnsUploadField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"string","毫秒"));
        dnsUploadField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"int",""));
        dnsUploadField.add(new TestShowFieldBean("successPercent",true,"成功率",0,"int","%"));

        speedUploadField.add(new TestShowFieldBean("customerIp",true,"测速客户端IP",0,"string",""));
        speedUploadField.add(new TestShowFieldBean("downloadMaxThroughput",true,"下载峰值速率",0,"int","Mbps"));
        speedUploadField.add(new TestShowFieldBean("downloadThroughput",true,"下载平均速率",0,"int","Mbps"));
        speedUploadField.add(new TestShowFieldBean("uploadMaxThroughput",true,"上传峰值速率",0,"int","Mbps"));
        speedUploadField.add(new TestShowFieldBean("uploadThroughput",true,"上传平均速率",0,"int","Mbps"));

    }
    public JSONObject getAllInfo()
    {
        JSONObject localJSONObject1 = new JSONObject();
        try
        {
            localJSONObject1.put("device", getDevice());
            localJSONObject1.put("platform", 2);
//            localJSONObject1.put("orderId", this.orderId);
            JSONObject localJSONObject2 = new JSONObject();
//            localJSONObject2.put("username", this.loginInfo.getUserName());
//            localJSONObject2.put("jobId", this.loginInfo.getJobnumber());
//            localJSONObject2.put("companyName", this.loginInfo.getUnit());
            JSONObject localJSONObject3 = new JSONObject();
            localJSONObject3.put("account", this.account);
            localJSONObject1.put("userInfo", localJSONObject2);
            localJSONObject1.put("customerInfo", localJSONObject3);
            return localJSONObject1;
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return localJSONObject1;
    }

    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public String getStbID() {
        return stbID;
    }

    public void setStbID(String stbID) {
        this.stbID = stbID;
    }

    public String getOotConnectType() {
        return ootConnectType;
    }

    public void setOotConnectType(String ootConnectType) {
        this.ootConnectType = ootConnectType;
    }

    public String getPppoePwd() {
        return pppoePwd;
    }

    public void setPppoePwd(String pppoePwd) {
        this.pppoePwd = pppoePwd;
    }

    public String getPppoeUser() {
        return pppoeUser;
    }

    public void setPppoeUser(String pppoeUser) {
        this.pppoeUser = pppoeUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getDeviceSeq() {
        return deviceSeq;
    }

    public void setDeviceSeq(String deviceSeq) {
        this.deviceSeq = deviceSeq;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getStaticIP() {
        return staticIP;
    }

    public void setStaticIP(String staticIP) {
        this.staticIP = staticIP;
    }

    public String getStaticSubNet() {
        return staticSubNet;
    }

    public void setStaticSubNet(String staticSubNet) {
        this.staticSubNet = staticSubNet;
    }

    public String getStaticGate() {
        return staticGate;
    }

    public void setStaticGate(String staticGate) {
        this.staticGate = staticGate;
    }

    public String getStaticDNS() {
        return staticDNS;
    }

    public void setStaticDNS(String staticDNS) {
        this.staticDNS = staticDNS;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<TestParamsBean> getOpenData() {
        return openData;
    }

    public void setOpenData(List<TestParamsBean> openData) {
        this.openData = openData;
    }

    public List<TestParamsBean> getManualData() {
        return manualData;
    }

    public void setManualData(List<TestParamsBean> manualData) {
        this.manualData = manualData;
    }

    public List<TestParamsBean> getTroubleData() {
        return troubleData;
    }

    public void setTroubleData(List<TestParamsBean> troubleData) {
        this.troubleData = troubleData;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public List<TestShowFieldBean> getDnsField() {
        return dnsField;
    }

    public void setDnsField(List<TestShowFieldBean> dnsField) {
        this.dnsField = dnsField;
    }

    public List<TestShowFieldBean> getTraceField() {
        return traceField;
    }

    public void setTraceField(List<TestShowFieldBean> traceField) {
        this.traceField = traceField;
    }

    public List<TestShowFieldBean> getHttpField() {
        return httpField;
    }

    public void setHttpField(List<TestShowFieldBean> httpField) {
        this.httpField = httpField;
    }

    public List<TestShowFieldBean> getPingField() {
        return pingField;
    }

    public void setPingField(List<TestShowFieldBean> pingField) {
        this.pingField = pingField;
    }

    public List<TestShowFieldBean> getSpeedField() {
        return speedField;
    }

    public void setSpeedField(List<TestShowFieldBean> speedField) {
        this.speedField = speedField;
    }

    public List<TestShowFieldBean> getTraceSubField() {
        return traceSubField;
    }

    public void setTraceSubField(List<TestShowFieldBean> traceSubField) {
        this.traceSubField = traceSubField;
    }

    public HashMap getFtpInfo() {
        return ftpInfo;
    }

    public void setFtpInfo(HashMap ftpInfo) {
        this.ftpInfo = ftpInfo;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMschapErrcode() {
        return mschapErrcode;
    }

    public void setMschapErrcode(String mschapErrcode) {
        this.mschapErrcode = mschapErrcode;
    }

    public String getOttConnectReason() {
        return ottConnectReason;
    }

    public void setOttConnectReason(String ottConnectReason) {
        this.ottConnectReason = ottConnectReason;
    }

    public String getPPPOEErrorMessage(String mschapErrcode){
        String message="";
        if(MSCHAP_ERROR_CODE_10.equals(DataManager.getInstance().getMschapErrcode())){
            message="PPPOE密码错误!";
        }else if(MSCHAP_ERROR_CODE_11.equals(DataManager.getInstance().getMschapErrcode())){
            message=DataManager.getInstance().getOttConnectReason();
        }else if(MSCHAP_ERROR_CODE_4.equals(DataManager.getInstance().getMschapErrcode())){
            message="PPPOE连接成功!";
        }else if(MSCHAP_ERROR_CODE_12.equals(DataManager.getInstance().getMschapErrcode())){
            message="PPPOE未设置!";
        }else{
            message="未知错误!";
        }
        return message;
    }

    public boolean isShowTwice4PPPOE() {
        return showTwice4PPPOE;
    }

    public void setShowTwice4PPPOE(boolean showTwice4PPPOE) {
        this.showTwice4PPPOE = showTwice4PPPOE;
    }
}

package com.fkty.mobileiq.distribution.manager;

import com.fkty.mobileiq.distribution.bean.LoginInfo;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestShowFieldBean;
import com.fkty.mobileiq.distribution.constant.CommonField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2017/12/18.
 */

public class DataManager {
    private static DataManager dataManager;
    private String url;
    private String account;
    private String cpu;
    private String hardDisk;
    private String deviceSeq;
    private String memory;
    private String staticIP;
    private String staticSubNet;
    private String staticGate;
    private String staticDNS;
    private String pppoePwd;
    private String pppoeUser;
    private String ootConnectType = CommonField.DHCP;
    private String device;
    private List<TestParamsBean> openData = new ArrayList();
    private List<TestParamsBean> manualData = new ArrayList();
    private List<TestParamsBean> troubleData = new ArrayList();
    private LoginInfo loginInfo;
    private List<TestShowFieldBean> dnsField = new ArrayList();
    private List<TestShowFieldBean> traceField = new ArrayList();
    private List<TestShowFieldBean> httpField = new ArrayList();
    private List<TestShowFieldBean> pingField = new ArrayList();
    private List<TestShowFieldBean> speedField = new ArrayList();



    public static DataManager getInstance()
    {
        if (dataManager == null){
            dataManager = new DataManager();
            dataManager.initialField();
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

        httpField.add(new TestShowFieldBean("connectTime",true,"连接时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("firstByteTime",true,"首字节时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("hostIp",true,"主机IP",0,"string",""));
        httpField.add(new TestShowFieldBean("firstPageTime",true,"首页加载时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("meanQuality",true,"综合质量",0,"int",""));
        httpField.add(new TestShowFieldBean("pageLoadTime",true,"页面加载时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"int","毫秒"));
        httpField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"int",""));
        httpField.add(new TestShowFieldBean("throughput",true,"吞吐率",0,"int","Byte/S"));

        dnsField.add(new TestShowFieldBean("avgReplyTime",true,"平均响应时间",0,"string","毫秒"));
        dnsField.add(new TestShowFieldBean("numberOfAnswers",true,"记录数",0,"int",""));
        dnsField.add(new TestShowFieldBean("resolveTime",true,"解析时间",0,"string","毫秒"));
        dnsField.add(new TestShowFieldBean("responseCode",true,"响应码",0,"int",""));
        dnsField.add(new TestShowFieldBean("successPercent",true,"成功率",0,"int","%"));

        speedField.add(new TestShowFieldBean("customerIp",true,"测速客户端IP",0,"string",""));
        speedField.add(new TestShowFieldBean("downloadMaxThroughput",true,"下载峰值速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("downloadThroughput",true,"下载平均速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("uploadMaxThroughput",true,"上传峰值速率",0,"int","Mbps"));
        speedField.add(new TestShowFieldBean("uploadThroughput",true,"上传平均速率",0,"int","Mbps"));

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
}

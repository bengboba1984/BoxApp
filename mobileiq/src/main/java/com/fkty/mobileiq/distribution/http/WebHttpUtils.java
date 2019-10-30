package com.fkty.mobileiq.distribution.http;

import android.nfc.Tag;
import android.util.Log;

import com.fkty.mobileiq.distribution.app.activity.NewQuestionActivity;
import com.fkty.mobileiq.distribution.app.activity.SSIDActivity;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.OpenTestConstant;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;
import com.fkty.mobileiq.distribution.constant.WebServerConstant;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by frank_tracy on 2018/3/5.
 */

public class WebHttpUtils implements Runnable{
    private static WebHttpUtils httpUtil;
    private Thread thread;
    private int id;
    public INetNotify notify;
    private String path;
    private String pppoePwd;
    private String pppoeUser;
    private String staticDns;
    private String staticGate;
    private String staticIp;
    private String staticSubnet;
    private String account;
    private List<TestParamsBean> testParams;
    private int modelType;
    private String fileName;
    private String filePath;
    private String localPath;
    private String boxUpgradeVersionFP;
    private String ssid;
    private int bridgeFlag;

    public static WebHttpUtils getInstance()
    {
            if (httpUtil == null)
                httpUtil = new WebHttpUtils();
            return httpUtil;
    }

    public void run() {
        String str = Thread.currentThread().getName();
        if (("set_url").equals(str)){
            setUrlTask();
        }
//        else if("get_templet".equals(str)){
//            getTempletTask();
//        }
        else if (("set_dhcp").equals(str)){
            setDHCPTask();
        }else if (("set_bridge").equals(str)){
            setBridgeTask();
        }else if (("set_pppoe").equals(str)){
            setPPPOETask();
        }else if (("exit_pppoe").equals(str)){
            exitPPPOETask();
        }else if (("set_staticip").equals(str)){
            setStaticIPTask();
        }else if(("set_account").equals(str)){
            setAccountTask();
        }else if("thread_name_startTest".equals(str)){
            startTestTask(this.testParams, this.modelType);
        }else if (("thread_name_stopTest").equals(str)){
            stopTestTask(this.modelType);
        }else if(("get_templet_4test").equals(str)){
            getTempletTask4Test();
        }else if (("reboot").equals(str)){
            setRebootTask();
        }else if (("restore").equals(str)){
            setRestoreTask();
        }else if (("start_capture").equals(str)){
            startCaptureTask();
        }else if("get_capture_file".equals(str)){
            getCaptureFileTask();
        }else if("stop_capture".equals(str)){
            stopCaptureTask();
        }else if("get_box_version".equals(str)) {
            getBoxVersionTask();
        }else if("get_box_version_on_server".equals(str)) {
            getBoxVersionOnServerTask();
        }else if ("upgrade_box_Version".equals(str)){
            upgradeBoxVersionTask();
        }else if("upload_result2_plateform".equals(str)){
            uploadResult2PlateformTask();
        }else if("set_ssid".equals(str)){
            setSSIDTask();
        }else if("submitWOByResultSeq".equals(str)){
            submitWOByResultSeqTask();
        }

    }
    public boolean setReboot(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "reboot");
        this.thread.start();
        return true;
    }

    public void setRebootTask()
    {
        OkHttpUtils.get().url(WebServerConstant.WEB_GET_REBOOT).id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean setRestore(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "restore");
        this.thread.start();
        return true;
    }

    public void setRestoreTask()
    {
        OkHttpUtils.get().url(WebServerConstant.WEB_GET_RESTORE).id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean stopTest(int paramInt1, INetNotify paramINetNotify, int paramInt2)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt2;
        this.modelType = paramInt1;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "thread_name_stopTest");
        this.thread.start();
        return true;
    }
    public void stopTestTask(int paramInt)
    {
        String param="";
//        JSONObject localJSONObject = new JSONObject();
//        try
//        {
//            localJSONObject.put("modelType", paramInt);
//            localJSONObject.put("device", DataManager.getInstance().getDevice());
            if(this.id== QuestionConstant.TEST_STOP_AND_START || this.id== OpenTestConstant.TEST_STOP_AND_START){
//                localJSONObject.put("nonblock", 1);
                param="?nonblock=1";
            }

            OkHttpUtils.postString().id(this.id).url(WebServerConstant.WEB_POST_STOP_TEST+param).content("").build().execute(new StringCallback()
            {
                public void onError(Call paramCall, Exception e, int paramInt)
                {
                    Log.d("WebHttpUtils","stopTestTask:onError="+e.getMessage());
                    e.printStackTrace();
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
                }

                public void onResponse(String paramString, int paramInt)
                {
                    Log.d("WebHttpUtils","stopTestTask:onResponse,paramString="+paramString);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
//        }
//        catch (JSONException e){
//            e.printStackTrace();
//        }
    }
    public void startTestTask(List<TestParamsBean> paramList, int modelType)
    {Log.e("startTestTask","startTestTask:modelType="+modelType);
//        JSONObject localJSONObject1 = DataManager.getInstance().getAllInfo();
//        try {
//            localJSONObject1.put("modelType", modelType);
//            JSONArray localJSONArray = new JSONArray();
//            if(paramList!=null && paramList.size()>0){
//                Iterator localIterator = paramList.iterator();
//                TestParamsBean localTestParamsBean;
//                while (localIterator.hasNext()){
//                    localTestParamsBean = (TestParamsBean)localIterator.next();
//                }
//            }
//            localJSONObject1.put("parameters", localJSONArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



        JSONObject pJson=new JSONObject();
        try {
            pJson.put("testTemplateType",modelType);
          //  if(modelType==CommonField.MODLE_TYPE_MANUAL_TEST){
                Iterator<TestParamsBean> it=paramList.iterator();
                JSONArray parametersJson=new JSONArray();
                TestParamsBean temp;
                while(it.hasNext()){
                    temp=it.next();
                    JSONObject tpbJson=new JSONObject();
                    tpbJson.put("testName",temp.getTestName());
                    tpbJson.put("testType",temp.getTestType());
                    if(modelType==CommonField.MODLE_TYPE_MANUAL_TEST){
                        JSONArray nodeIpJsonArray=new JSONArray();
                        JSONObject nodeIpJson=new JSONObject();
                        nodeIpJson.put("url",temp.getDestNodeIp());
                        nodeIpJsonArray.put(nodeIpJson);
                        tpbJson.put("nodeIp",nodeIpJsonArray);
                    }

                    Log.d("WebHttpUtil","startTest:temp.getTestName="+temp.getTestName()+"/temp.getDestNodeIp()="+temp.getDestNodeIp());
                    parametersJson.put(tpbJson);
                }
                pJson.put("parameters",parametersJson);
           // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WebHttpUtil","pJson="+pJson.toString());
        OkHttpUtils.postString().url(WebServerConstant.WEB_POST_START_TEST).content(pJson.toString()).id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
                Log.e("WebHttpUtils","startTestTask:onError"+e.getMessage());
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                Log.d("WebHttpUtils","startTestTask:onResponse="+paramString+"/paramInt="+paramInt);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public boolean startTest(List<TestParamsBean> paramList, int paramInt1, INetNotify paramINetNotify, int paramInt2)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt2;
        if (paramList != null){
            this.testParams = paramList;
        }else{
            this.testParams = new ArrayList();
        }
        this.modelType = paramInt1;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "thread_name_startTest");
        this.thread.start();
        return true;
    }
    public boolean setUrl(INetNotify paramINetNotify, int paramInt, String paramString)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.path = paramString;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "set_url");
        this.thread.start();
        return true;
    }
//    public boolean getTemplet(INetNotify paramINetNotify, int paramInt)
//    {
//        if (this.thread != null)
//        {
//            if (this.thread.isAlive())
//                return false;
//            this.thread = null;
//        }
//        this.id = paramInt;
//        this.notify = paramINetNotify;
//        this.thread = new Thread(this, "get_templet");
//        this.thread.start();
//        return true;
//    }
    public boolean getTemplet4Test(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "get_templet_4test");
        this.thread.start();
        return true;
    }
    public void getTempletTask4Test()
    {

        OkHttpUtils.postString().url(DataManager.getInstance().getUrl()+WebServerConstant.WEB_POST_GETTEMPLET).content("")
                .id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public void setUrlTask()
    {
        if ((this.path != null) && (this.path.length() > 0)){
            ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_POST_SET_URL + "?url=" + this.path)).id(this.id)).build().execute(new StringCallback()
            {
                public void onError(Call paramCall, Exception e, int paramInt)
                {
                    Log.e("WebHttpUtils","setUrlTask:onError="+e.getMessage());
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
                }

                public void onResponse(String paramString, int paramInt)
                {
                    Log.e("WebHttpUtils","setUrlTask:onResponse="+paramString);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
        }
    }
//    public void getTempletTask()
//    {
//        ((PostStringBuilder)((PostStringBuilder)OkHttpUtils.postString().url(WebServerConstant.WEB_POST_GETTEMPLET)).content("")
//                .id(this.id)).build().execute(new StringCallback()
//        {
//            public void onError(Call paramCall, Exception paramException, int paramInt)
//            {
//                if (WebHttpUtils.this.notify != null)
//                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
//            }
//
//            public void onResponse(String paramString, int paramInt)
//            {
//                if (WebHttpUtils.this.notify != null)
//                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
//            }
//        });
//    }

    public boolean setDHCP(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "set_dhcp");
        this.thread.start();
        return true;
    }

    public void setDHCPTask()
    {
        Log.d("WebHttpUtils","setDHCPTask:url="+WebServerConstant.WEB_GET_SET_DHCP);

        ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_GET_SET_DHCP)).id(this.id)).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                DataManager.getInstance().setOotConnectType(CommonField.DHCP);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean setBridge(INetNotify paramINetNotify, int paramInt,int flag)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.bridgeFlag=flag;
        this.thread = new Thread(this, "set_bridge");
        this.thread.start();
        return true;
    }

    public void setBridgeTask()
    {
        Log.d("WebHttpUtils","setBridgeTask:url="+WebServerConstant.WEB_GET_SET_BRIDGE+"?mode="+this.bridgeFlag);
        ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_GET_SET_BRIDGE+"?mode="+this.bridgeFlag)).id(this.id)).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
//                Log.d("setBridgeTask","exception="+);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public void setPPPOETask()
    {
        ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_GET_SET_PPPOE + "?userName=" + this.pppoeUser + "&password=" + this.pppoePwd)).id(this.id)).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                DataManager.getInstance().setOotConnectType(CommonField.PPPOE);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public boolean setPPPoe(String pppoeUserP, String pppoePwdP, INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.pppoePwd = pppoePwdP;
        this.pppoeUser = pppoeUserP;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "set_pppoe");
        this.thread.start();
        return true;
    }
    public void exitPPPOETask()
    {
        ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_GET_SET_PPPOE + "?disable" )).id(this.id)).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean exitPPPoe(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "exit_pppoe");
        this.thread.start();
        return true;
    }

    public boolean setStaticIP(String staticIpP, String staticGateP, String staticDnsP, String staticSubnetP, INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.staticDns = staticDnsP;
        this.staticGate = staticGateP;
        this.staticIp = staticIpP;
        this.staticSubnet = staticSubnetP;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "set_staticip");
        this.thread.start();
        return true;
    }

    public void setStaticIPTask()
    {
        JSONObject params = new JSONObject();
        try
        {
            params.put("ipAddress", this.staticIp);
            params.put("subNetMask", this.staticSubnet);
            params.put("gateway", this.staticGate);
            params.put("dns", this.staticDns);
            Log.e("ddddddd",params.toString());
            ((PostStringBuilder)((PostStringBuilder)OkHttpUtils.postString().id(this.id)).url(WebServerConstant.WEB_POST_SET_STATIC)).content(params.toString()).build().execute(new StringCallback()
            {
                public void onError(Call paramCall, Exception paramException, int paramInt)
                {
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
                }

                public void onResponse(String paramString, int paramInt)
                {
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
        }
        catch (JSONException e)
        {
           e.printStackTrace();
        }
    }

    public boolean setAccount(INetNotify paramINetNotify, int paramInt, String strAccount)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.account = strAccount;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "set_account");
        this.thread.start();
        return true;
    }

    public void setAccountTask() {
        if ((this.account != null) && (this.account.length() > 0)) {
            OkHttpUtils.get().url(WebServerConstant.WEB_GET_SET_ACCOUNT + "?account=" + this.account).id(this.id).build().execute(new StringCallback() {
                public void onError(Call paramCall, Exception paramException, int paramInt) {
                    DataManager.getInstance().setAccount(account);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
                }

                public void onResponse(String paramString, int paramInt) {
                    DataManager.getInstance().setAccount(account);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
        }
    }

    public boolean startCapture(String fileName, INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.fileName = fileName;

        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "start_capture");
        this.thread.start();
        return true;
    }

    public void startCaptureTask()
    {
        String userName = DataManager.getInstance().getLoginInfo().getUserName();
        userName="root";
        int count=10000;
        String ootConnectType=DataManager.getInstance().getOotConnectType();
        int captureType=2;
        if(CommonField.BRIDGE.equals(ootConnectType)){
            captureType=3;
        }
        Log.d("WebHttp","url="+WebServerConstant.WEB_GET_START_CAPTURE + "?count="+count+"&captureType="+captureType+"&userName=" + userName + "&fileName=" + this.fileName);
//        LogUtils.e("-----", "url:" + WebServerConstant.WEB_GET_START_CAPTURE + "?userName=" + str + "&fileName=" + this.fileName);
        OkHttpUtils.get().id(this.id).url(WebServerConstant.WEB_GET_START_CAPTURE + "?count="+count+"&captureType="+captureType+"&userName=" + userName + "&fileName=" + this.fileName).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
                Log.d("WebHttp","startCaptureTask:onError");
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                Log.d("WebHttp","startCaptureTask:onResponse");
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public boolean getCaptureFile(String filePath, String localPath,INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.filePath = filePath;
        this.localPath=localPath;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "get_capture_file");
        this.thread.start();
        return true;
    }

    public void getCaptureFileTask() {
        Log.d("WebHttp","url="+WebServerConstant.WEB_GET_CAPTURE_FILE +this.filePath);
        OkHttpUtils.get().id(this.id).url(WebServerConstant.WEB_GET_CAPTURE_FILE +this.filePath).build().execute(new FileCallBack(this.localPath, this.filePath.substring(this.filePath.lastIndexOf("/"))+".pcap") {
            public void onError(Call paramCall, Exception paramException, int paramInt) {
                Log.d("WebHttp","getCaptureFileTask:onError");
                paramException.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
            }

            public void onResponse(File paramFile, int paramInt) {
                Log.d("WebHttp","getCaptureFileTask:onResponse");
                if ((WebHttpUtils.this.notify != null) && (paramFile != null))
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramFile.getAbsolutePath());
            }
        });
    }

    public boolean stopCapture(INetNotify paramINetNotify, int paramInt)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "stop_capture");
        this.thread.start();
        return true;
    }

    public void stopCaptureTask()
    {
        Log.d("WebHttpUtils","stopCaptureTask:url="+WebServerConstant.WEB_GET_STOP_CAPTURE);
        OkHttpUtils.get().id(this.id).url(WebServerConstant.WEB_GET_STOP_CAPTURE).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                Log.d("WebHttpUtils","stopCaptureTask:paramString="+paramString);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public boolean getBoxVersion(int paramInt, INetNotify paramINetNotify){
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "get_box_version");
        this.thread.start();
        return true;
    }
    public void getBoxVersionTask()
    {
        Log.d(this.getClass().getName(),"start getBoxVersionTask=============");

        OkHttpUtils.get().url(WebServerConstant.HTTP_GET_BOX_VERSION ).id(this.id).build().execute(new StringCallback() {
            public void onError(Call paramCall, Exception e, int paramInt) {
                Log.d(this.getClass().getName(),"e="+e.getMessage());
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt) {
                Log.d(this.getClass().getName(),"paramString="+paramString);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean getBoxVersionOnServer(int paramInt, INetNotify paramINetNotify){
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "get_box_version_on_server");
        this.thread.start();
        return true;
    }
    public void getBoxVersionOnServerTask()
    {
        Log.d(this.getClass().getName(),"start getBoxVersionOnServerTask=============");

        OkHttpUtils.get().url(WebServerConstant.HTTP_GET_BOX_VERSION_URL+"update.json" ).id(this.id).build().execute(new StringCallback() {
            public void onError(Call paramCall, Exception e, int paramInt) {
                Log.d(this.getClass().getName(),"e="+e.getMessage());
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt) {
                Log.d(this.getClass().getName(),"paramString="+paramString);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean upgradeBoxVersion(INetNotify paramINetNotify, int paramInt, String paramString)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.boxUpgradeVersionFP = paramString;
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "upgrade_box_Version");
        this.thread.start();
        return true;
    }

    public void upgradeBoxVersionTask() {
        if ((this.boxUpgradeVersionFP != null) && (this.boxUpgradeVersionFP.length() > 0)) {
            JSONObject pJson=new JSONObject();
            try {
                pJson.put("filepath",this.boxUpgradeVersionFP);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("update box","WebServerConstant.UPDATE_FIRMWARE="+WebServerConstant.UPDATE_FIRMWARE+"|json="+pJson.toString());
            OkHttpUtils.postString().url(WebServerConstant.UPDATE_FIRMWARE).content(pJson.toString()).id(this.id).build().execute(new StringCallback(){
            public void onError(Call paramCall, Exception paramException, int paramInt) {
                Log.d("WebHttpUtils","paramException="+paramException);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, paramException.getMessage());
                }

                public void onResponse(String paramString, int paramInt) {
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
        }
    }

    public boolean submitWOByResultSeq(INetNotify paramINetNotify, int paramInt) {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "submitWOByResultSeq");
        this.thread.start();
        return true;
    }
    public void submitWOByResultSeqTask()
    {
        String resultSeq= DataManager.getInstance().getResultSeq();
        String woNumber= DataManager.getInstance().getWoNumber();
        JSONObject params=new JSONObject();
        try {
            params.put("resultSeq",resultSeq);
            params.put("woNumber",woNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("WebHttpUtil","##### resultSeq="+resultSeq+"|woNumber="+woNumber+" ######");
        OkHttpUtils.postString().mediaType(MediaType.parse("application/json; charset=utf-8")).url(DataManager.getInstance().getUrl()+WebServerConstant.REPORT_WEB_SUBMIT_WO).content(params.toString()).id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
                Log.e("WebHttpUtil","submitWOByResultSeqTask:onError"+e.getMessage());
                DataManager.getInstance().setResultSeq(null);
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                Log.d("WebHttpUtil","submitWOByResultSeqTask:onResponse="+paramString);
                DataManager.getInstance().setResultSeq(null);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }
    public boolean uploadResult2Plateform(INetNotify paramINetNotify, int paramInt) {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, "upload_result2_plateform");
        this.thread.start();
        return true;
    }

    public void uploadResult2PlateformTask()
    {
        JSONObject pJson= DataManager.getInstance().getUploadResult();
//        try {
//            pJson = new JSONObject("{\n" +
//                    "    \"items\": [\n" +
//                    "        {\n" +
//                    "            \"testType\": 1000,\n" +
//                    "            \"sourceIp\": \"180.173.33.188\",\n" +
//                    "            \"bandwidth\": 50000000,\n" +
//                    "            \"account\": \"111111\",\n" +
//                    "            \"applicationType\": 1,\n" +
//                    "            \"speedTestType\": \"ENOM\",\n" +
//                    "            \"errorCode\": 0,\n" +
//                    "            \"downloadThroughput\": 12177536,\n" +
//                    "            \"downloadMaxThrought\": 12283776,\n" +
//                    "            \"uploadThroughput\": 566400,\n" +
//                    "            \"uploadMaxThroughput\": 679040,\n" +
//                    "            \"downloadSize\": 5000000,\n" +
//                    "            \"macAddress\": \"\",\n" +
//                    "            \"cellName\": \"\"\n" +
//                    "        }\n" +
//                    "    ]\n" +
//                    "}");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        Log.d("WebHttpUtil","pJson="+pJson.toString());
        Log.d("WebHttpUtil","url="+DataManager.getInstance().getUrl()+WebServerConstant.REPORT_WEB_TEST_RESULT);
        OkHttpUtils.postString().mediaType(MediaType.parse("application/json; charset=utf-8")).url(DataManager.getInstance().getUrl()+WebServerConstant.REPORT_WEB_TEST_RESULT).content(pJson.toString()).id(this.id).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception e, int paramInt)
            {
                Log.e("WebHttpUtil","uploadResult2PlateformTask:onError"+e.getMessage());
                e.printStackTrace();
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
            }

            public void onResponse(String paramString, int paramInt)
            {
                Log.d("WebHttpUtil","uploadResult2PlateformTask:onResponse="+paramString);
                if (WebHttpUtils.this.notify != null)
                    WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
            }
        });
    }

    public boolean setSSID(INetNotify ssidActivity, int set_ssid, String ssid) {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.ssid = ssid;
        this.id = set_ssid;
        this.notify = ssidActivity;
        this.thread = new Thread(this, "set_ssid");
        this.thread.start();
        return true;
    }
    public void setSSIDTask()
    {
        if ((this.ssid != null) && (this.ssid.length() > 0)){
            ((GetBuilder)((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_POST_SET_SSID + "?id=" + this.ssid)).id(this.id)).build().execute(new StringCallback()
            {
                public void onError(Call paramCall, Exception e, int paramInt)
                {
                    Log.e("WebHttpUtils","setSSIDTask:onError="+e.getMessage());
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onErrorNetClient(paramInt, e.getMessage());
                }

                public void onResponse(String paramString, int paramInt)
                {
                    Log.d("WebHttpUtils","setSSIDTask:onResponse="+paramString);
                    if (WebHttpUtils.this.notify != null)
                        WebHttpUtils.this.notify.onSuccessNetClient(paramInt, paramString);
                }
            });
        }
    }
}

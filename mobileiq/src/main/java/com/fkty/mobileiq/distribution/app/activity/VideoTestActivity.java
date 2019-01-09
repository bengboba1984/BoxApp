package com.fkty.mobileiq.distribution.app.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.presenter.impl.NetWorkPresenter;
import com.fkty.mobileiq.distribution.app.view.INetWorkView;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.CaptureFileBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.FTPConstant;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.manager.FTPManager;
import com.fkty.mobileiq.distribution.manager.MWifiManager;
import com.fkty.mobileiq.distribution.manager.OTTProperty;
import com.fkty.mobileiq.distribution.manager.UploadProgressListener;
import com.fkty.mobileiq.distribution.ui.adapter.CaptureFileListAdapter;
import com.fkty.mobileiq.distribution.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VideoTestActivity extends BaseActivity implements INetWorkView, INetNotify {
    private final int GET_SET_BRIDGE = 4;
    private final int BRIDGE_ON = 1;
    private final int BRIDGE_OFF = 0;
    private int BRIDGE_ACTION=-1;
    private final int GET_SET_DHCP = 3;
    private TextView title;
    private ImageView backImg;
    File fileDir = new File(CommonField.VIDEO_FILE_DIR);
    private CaptureFileListAdapter adapter;
    private List<CaptureFileBean> data;
    private ListView listView;
    private TextView selectorAll;
    private Button uploadBtn;
    private ProgressDialog progressBar;
    private Button captureBtn;
    private NetWorkPresenter presenter;
    private int status=1;
    private final int CAPTUREING = 2;
    private final int CAPTURE_FINISH = 3;
    private final int CAPTURE_READY = 1;
    private Button deleteBtn;
    private ProgressDialog uploadProgressBar;
    private TextView captureTime;
    private LinearLayout fileLayout;
    private boolean needExitBridge=true;

    public void onStartFailed(int paramInt, Bundle paramBundle) {
        this.status=CAPTURE_READY;
        this.captureBtn.setText(R.string.start_capture);
        this.captureBtn.setTextColor(Color.WHITE);
        countHandler.removeCallbacks(runnable);
        if(this.fileLayout.getVisibility()==View.VISIBLE){
            this.fileLayout.setVisibility(View.GONE);
        }
        showToast("开始抓包失败");
        //判断失败原因
    }

    public void onStartSuccess(int paramInt, Bundle paramBundle) {
        Log.d(TAG,"onStartSuccess:status="+this.status);
        this.captureBtn.setText(R.string.stop_capture);
        showToast("抓包中,请按结束获取信息");
    }

    public void onStopFailed(int paramInt, Bundle paramBundle) {
        showToast("抓包失败！！");
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.captureBtn.setText(R.string.start_capture);
        this.captureBtn.setTextColor(Color.WHITE);
        countHandler.removeCallbacks(runnable);
        if(this.fileLayout.getVisibility()==View.VISIBLE){
            this.fileLayout.setVisibility(View.GONE);
        }
    }

    public void onStopSuccess(int paramInt, String paramString) {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        showToast("抓包成功！！");
        Log.d(TAG,"paramString="+paramString);
        if (this.status == CAPTUREING) {

            String filePath = paramString;
            Log.d(TAG, "onStartSuccess:filePath=" + filePath);
            this.presenter.getCaptureFile(filePath,CommonField.VIDEO_FILE_DIR);

        } else {
            //capture finished by button
        }
    }

    public void onGetCaptureFileSuccess(int paramInt) {
        this.status=CAPTURE_FINISH;
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.captureBtn.setText(R.string.start_capture);
        this.captureBtn.setTextColor(Color.WHITE);
        countHandler.removeCallbacks(runnable);
        if(this.fileLayout.getVisibility()==View.VISIBLE){
            this.fileLayout.setVisibility(View.GONE);
        }
        initFileList();
    }

    @Override
    public void controlDialog(int paramInt, String paramString) {

    }

    @Override
    public void onDataUpdate(Bundle paramBundle) {

    }

    @Override
    public void toastMsg(String paramString) {

    }

    @Override
    public void initView(View paramView) {
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.selectorAll = paramView.findViewById(R.id.network_selector_all);
        this.listView = paramView.findViewById(R.id.network_list);
        this.fileLayout=paramView.findViewById(R.id.capture_file);
        this.captureTime=paramView.findViewById(R.id.capture_time);
        this.captureBtn = paramView.findViewById(R.id.start_capture);
        this.uploadBtn = paramView.findViewById(R.id.upload_capture);
        this.deleteBtn = paramView.findViewById(R.id.delete_captrue_file);

        if(MWifiManager.getIntance().isWifiConnect()
                && ((DataManager.getInstance().getStbID() != null) && (DataManager.getInstance().getStbID().length() >= 1))
//                && CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())
                ){
            this.captureBtn.setBackgroundColor(getResources().getColor(R.color.btn_green));
            this.captureBtn.setEnabled(true);
        }else{
            this.captureBtn.setEnabled(false);
            this.captureBtn.setBackgroundColor(Color.GRAY);
        }

        if ( !MWifiManager.getIntance().isNetworkConnected() || CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())){
            this.uploadBtn.setEnabled(false);
            this.uploadBtn.setBackgroundColor(Color.GRAY);
        }else{
            this.uploadBtn.setEnabled(true);
            this.uploadBtn.setBackgroundColor(getResources().getColor(R.color.btn_green));
        }
    }

    @Override
    public void initData() {
        Log.d(TAG,"this.status="+this.status);
        title.setText(getString(R.string.video_file_title));
        if (this.progressBar == null)
        {
            this.progressBar = new ProgressDialog(this);
            this.progressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.progressBar.setIndeterminate(true);
            this.progressBar.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                public void onCancel(DialogInterface paramDialogInterface)
                {
                }
            });
            this.progressBar.setCancelable(false);
        }

        if (this.uploadProgressBar == null)
        {
            this.uploadProgressBar = new ProgressDialog(this);
            this.uploadProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.uploadProgressBar.setIndeterminate(true);
            this.uploadProgressBar.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                public void onCancel(DialogInterface paramDialogInterface)
                {
                }
            });
            this.uploadProgressBar.setCancelable(false);
        }


        this.data = new ArrayList();
        this.adapter = new CaptureFileListAdapter(this.data, this);
        this.listView.setAdapter(this.adapter);

        initFileList();
        this.presenter = new NetWorkPresenter(this);
    }

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_video_test;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void doBusiness(Context paramContext) {

    }

    @Override
    public void setListener() {
        backImg.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        selectorAll.setOnClickListener(this);
        captureBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View paramView) {
        Log.d(TAG,"this.status="+this.status);
        switch (paramView.getId())
        {
            case R.id.vixtel_btn_back:
                actionBeforeExit();
                break;
            case R.id.network_selector_all:
                if(data!=null && data.size()>0){
                    Iterator it = data.iterator();
                    while (it.hasNext()){
                        ((CaptureFileBean)it.next()).setSelector(true);
                    }
                    this.adapter.notifyDataSetChanged();
                }
                break;
            case R.id.start_capture:
                if(this.status==CAPTURE_READY || this.status==CAPTURE_FINISH){
                    this.status=CAPTUREING;
                    OTTProperty.getInstance().setAllowedStopCapture(false);
                    this.captureBtn.setText(R.string.stop_capture);
                    this.captureBtn.setTextColor(Color.RED);
                    this.fileLayout.setVisibility(View.VISIBLE);
                    captureTime.setText("");
                    countHandler.postDelayed(runnable,1000);

                    if(CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())){
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
                        int j = (int)(1000.0D * Math.random());
                        String fn= DataManager.getInstance().getStbID()+"_"+sdf.format(new Date())+j;
                        this.presenter.start(fn);
                    }else{
                        BRIDGE_ACTION=BRIDGE_ON;
                        WebHttpUtils.getInstance().setBridge(this, GET_SET_BRIDGE,BRIDGE_ON);
                    }


                }else if(this.status==CAPTUREING){
                    if(OTTProperty.getInstance().isAllowedStopCapture()){
                        this.progressBar.setMessage("处理数据中....");
                        if (!this.progressBar.isShowing())
                            this.progressBar.show();
                        this.presenter.stop();
                    }else{
                        showToast("网桥设置中，请稍后");
                    }


                }

                break;
            case R.id.delete_captrue_file:
                deleteFiles();
                break;
            case R.id.upload_capture:
                if(CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())){
                    showToast("网桥模式无法上传FTP！");
                    break;
                }
                uploadCaptureFiles();
                break;
            default:
                break;
        }
    }

    private void uploadCaptureFiles(){
        final LinkedList selectedList=new LinkedList();
        for(int i=0;i<this.data.size();i++){
            CaptureFileBean cfb = this.data.get(i);
            if (cfb.isSelector()){
                Log.d(TAG,"cfb.getFilePath()="+cfb.getFilePath());
                selectedList.add(new File(cfb.getFilePath()));
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FTPManager.getInstance().uploadMultiFile(selectedList, FTPConstant.REMOTE_PATH,"video", new UploadProgressListener() {
                        @Override
                        public void onUploadProgress(int currentStep, long uploadSize, File file) {
                            if(currentStep==FTPConstant.FTP_UPLOAD_SUCCESS){
                                Log.d(TAG, "-----upload--successful:getAbsolutePath="+file.getAbsolutePath());
                                file.delete();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        uploadCaptureFileSuccess();
//                                        initFileList();
                                    }
                                });
                            } else if(currentStep==FTPConstant.FTP_UPLOAD_LOADING){
                                long fize = file.length();
                                float num = (float)uploadSize / (float)fize;
                                int result = (int)(num * 100);
                                uploadProgressBar.setProgress(result);
                                Log.d(TAG, "-----upload---"+result + "%");
                            }else if(currentStep==FTPConstant.FTP_UPLOAD_FAIL){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        uploadCaptureFileFailed();
                                    }
                                });
                            }else if(currentStep==FTPConstant.FTP_CONNECT_FAIL){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        uploadCaptureFileFailed();
                                    }
                                });
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
//                    uploadCaptureFileFailed();
                }

            }
        }).start();


    }


    private void deleteFiles(){
        for(int i=0;i<this.data.size();i++){
            CaptureFileBean cfb = this.data.get(i);
            if (cfb.isSelector()){
                File localFile1 = new File(cfb.getFilePath());
                if (localFile1.exists()){
                    localFile1.delete();
                }
            }
        }
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
        initFileList();
    }
    private void initFileList(){
        this.data.clear();
        if(!this.fileDir.exists()){
            this.fileDir.mkdirs();
        }
        if (this.fileDir.isDirectory()) {
            File[] arrayOfFile = this.fileDir.listFiles();
            Log.d(TAG,"arrayOfFile.length="+arrayOfFile.length);
            for (int i = 0; i<arrayOfFile.length; i++)
            {
                File localFile2 = arrayOfFile[i];
                CaptureFileBean localCaptureFileBean = new CaptureFileBean();
                localCaptureFileBean.setFilePath(localFile2.getAbsolutePath());
                localCaptureFileBean.setFileName(localFile2.getName());
                localCaptureFileBean.setFileTime(localFile2.lastModified());
                localCaptureFileBean.setFileSize(FileUtils.formatFileSize(localFile2.length()));
                localCaptureFileBean.setFileStatus(CommonField.UPLOAD_YES);
                this.data.add(localCaptureFileBean);
            }
        }
        //else is for testing code
        else{
            for (int i = 0; i<2; i++)
            {

                CaptureFileBean localCaptureFileBean = new CaptureFileBean();
                localCaptureFileBean.setFilePath("path"+i);
                localCaptureFileBean.setFileName("fileName+"+i);
                localCaptureFileBean.setFileTime(Calendar.getInstance().getTimeInMillis());
                localCaptureFileBean.setFileSize(FileUtils.formatFileSize(1024));
                localCaptureFileBean.setFileStatus(CommonField.UPLOAD_YES);
                this.data.add(localCaptureFileBean);
            }
        }
        this.adapter.notifyDataSetChanged();

        Collections.sort(this.data, new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2) {
                return (int)(((CaptureFileBean)o2).getFileTime()-((CaptureFileBean)o1).getFileTime());
            }
        });
    }

    private void uploadCaptureFileSuccess() {
        Toast.makeText(this, "上传平台成功", Toast.LENGTH_SHORT).show();
        initFileList();
    }
    private void uploadCaptureFileFailed() {
        Toast.makeText(this, "上传失败，请查看网络(桥接模式无法上传FTP)", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onErrorNetClient(int paramInt, String paramString) {
        Log.d(TAG,paramString);
        switch (paramInt){
            case GET_SET_BRIDGE:
                if(BRIDGE_ACTION==BRIDGE_ON){
                    Toast.makeText(this, "无法设为桥接模式！", Toast.LENGTH_SHORT).show();
                    this.captureBtn.setText(R.string.start_capture);
                    this.captureBtn.setTextColor(Color.WHITE);
                    countHandler.removeCallbacks(runnable);
                    if(this.fileLayout.getVisibility()==View.VISIBLE){
                        this.fileLayout.setVisibility(View.GONE);
                    }
                    this.status=CAPTURE_FINISH;
                }else if(BRIDGE_ACTION==BRIDGE_OFF){
                    if (progressBar.isShowing())
                        progressBar.dismiss();
                    Toast.makeText(this, "退出桥接模式失败！请至网络设置页面再行设置", Toast.LENGTH_SHORT).show();
                    startActivity(SettingOTTActivity.class);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {

    }

    @Override
    public void onSuccessNetClient(int paramInt, String paramString) {
        switch (paramInt){
            case GET_SET_BRIDGE:
                if(BRIDGE_ACTION==BRIDGE_ON){
                    DataManager.getInstance().setOotConnectType(CommonField.BRIDGE);
                    Toast.makeText(this, "盒子已切换成桥接模式！点击结束查看结果", Toast.LENGTH_SHORT).show();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
                    int j = (int)(1000.0D * Math.random());
                    String fn= DataManager.getInstance().getStbID()+"_"+sdf.format(new Date())+j;
                    this.presenter.start(fn);
                }else if(BRIDGE_ACTION==BRIDGE_OFF){
                    WebHttpUtils.getInstance().setDHCP(this, GET_SET_DHCP);
                }
                break;
            case GET_SET_DHCP:
                if (progressBar.isShowing())
                    progressBar.dismiss();
                finish();
                break;
        }

    }

    Handler countHandler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            String ct= String.valueOf(captureTime.getText());
            if(ct==null || "".equals(ct)){
                captureTime.setText("00:00");
            }else{
                String mm=ct.split(":")[0];
                String ss=ct.split(":")[1];
                if("59".equals(ss)){
                    mm=String.valueOf(Integer.valueOf(mm).intValue()+1);
                    ss="00";
                }else{
                    ss=String.valueOf(Integer.valueOf(ss).intValue()+1);
                    if(ss.length()==1){
                        ss="0"+ss;
                    }
                }
                captureTime.setText(mm+":"+ss);
            }
            countHandler.postDelayed(this,1000);
        }
    };

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void actionBeforeExit(){
        countHandler.removeCallbacks(runnable);
        if(CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType()) && needExitBridge){
            Dialog upgradeDialog=new AlertDialog.Builder(this).setTitle("固件升级")
                    .setMessage("是否退出桥接模式？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressBar.setMessage("退出桥接模式.......");
                            if (!progressBar.isShowing())
                                progressBar.show();
                            BRIDGE_ACTION=BRIDGE_OFF;
                            WebHttpUtils.getInstance().setBridge(VideoTestActivity.this, GET_SET_BRIDGE,BRIDGE_OFF);

                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            needExitBridge=false;
                            finish();
                        }
                    }).create();
            upgradeDialog.show();

        }else{
            finish();
        }

    }
}

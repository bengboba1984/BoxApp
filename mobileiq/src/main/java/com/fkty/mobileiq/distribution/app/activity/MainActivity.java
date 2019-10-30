package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.core.CoreManager;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.manager.MWifiManager;
import com.fkty.mobileiq.distribution.ui.adapter.MainViewPagerAdapter;
import com.fkty.mobileiq.distribution.view.CustomView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,INetNotify{
    private boolean mIsExit;
    private final int EXIT_PPPOE = 1001;
    private static final int[] pics = { R.mipmap.img_head1 };
//    private final int GET_TEMPLET = 2;
//    private final int SET_URL = 1;
    private MainViewPagerAdapter adapter;
    private TextView connectStatus;
    private int currentIndex;
    private ImageView[] dots;
    private long firstTime = 0L;
    private ImageView icon;
    private CustomView manualTest;
    private CustomView networkTest;
    private CustomView openTest;
    private ProgressDialog progressBar;
    private CustomView questionTest;
    private CustomView setting;
    private CustomView settingOTT;
    private CustomView testRecode;
    private CustomView videoTest;
    private TextView title;
    private ImageView userIcon;
    private CustomView userInfo;
    private ViewPager viewPager;
    private List<View> views;
    @Override
    public void initView(View paramView) {
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.userIcon = paramView.findViewById(R.id.vixtel_btn_back);
        this.setting = paramView.findViewById(R.id.setting);
        this.questionTest = paramView.findViewById(R.id.questionTest);
        this.openTest = paramView.findViewById(R.id.openTest);
        this.settingOTT = paramView.findViewById(R.id.settingOTT);
        this.manualTest = paramView.findViewById(R.id.manualTest);
        this.networkTest = paramView.findViewById(R.id.networkTest);
        this.userInfo = paramView.findViewById(R.id.userInfo);
        this.testRecode = paramView.findViewById(R.id.testRecode);
        this.videoTest = paramView.findViewById(R.id.videoTest);
        this.viewPager = paramView.findViewById(R.id.viewpager);
        this.connectStatus = paramView.findViewById(R.id.connect_status);
        this.icon = paramView.findViewById(R.id.vixtel_btn_icon);
        this.icon.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        this.views = new ArrayList();
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < pics.length; i++)
        {
            ImageView localImageView = new ImageView(this);
            localImageView.setLayoutParams(localLayoutParams);
            localImageView.setImageResource(pics[i]);
            localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            this.views.add(localImageView);
        }
        this.adapter = new MainViewPagerAdapter(this.views);
        initDots();
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
    }

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void doBusiness(Context paramContext) {
        this.title.setText(getString(R.string.main_title4test));
        this.setting.setTitleText(getString(R.string.setting));
        this.questionTest.setTitleText(getString(R.string.questionTest));
        this.openTest.setTitleText(getString(R.string.openTest));
        this.settingOTT.setTitleText(getString(R.string.settingOTT));
        this.manualTest.setTitleText(getString(R.string.manualTest));
        this.networkTest.setTitleText(getString(R.string.networkTest));
        this.userInfo.setTitleText(getString(R.string.userInfo));
        this.testRecode.setTitleText(getString(R.string.testRecode));
        this.videoTest.setTitleText(getString(R.string.videoTest));
        this.setting.setImgResource(R.mipmap.set_up);
        this.questionTest.setImgResource(R.mipmap.troubleshooting);
        this.openTest.setImgResource(R.mipmap.opening_test);
        this.settingOTT.setImgResource(R.mipmap.configure_box);
        this.manualTest.setImgResource(R.mipmap.manual_test);
        this.networkTest.setImgResource(R.mipmap.broken_wire_test);
        this.userInfo.setImgResource(R.mipmap.user_information);
        this.testRecode.setImgResource(R.mipmap.test_record);
        this.videoTest.setImgResource(R.mipmap.video_test);
        this.userIcon.setImageResource(R.mipmap.icon_user);
        this.viewPager.setAdapter(this.adapter);
//        this.progressBar.setMessage("获取测试模板中");
        if (MWifiManager.getIntance().isBoxConnectNetwork())
        {
            CoreManager.getInstance().start();
            this.connectStatus.setVisibility(View.GONE);
//            this.progressBar.setMessage("设置服务器地址");
//            if (!this.progressBar.isShowing())
//                this.progressBar.show();
//            WebHttpUtils.getInstance().setUrl(this, 1, DataManager.getInstance().getUrl());
            return;
        }
        this.connectStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void setListener() {
        this.setting.setOnClickListener(this);
        this.questionTest.setOnClickListener(this);
        this.openTest.setOnClickListener(this);
        this.settingOTT.setOnClickListener(this);
        this.manualTest.setOnClickListener(this);
        this.networkTest.setOnClickListener(this);
        this.userInfo.setOnClickListener(this);
        this.testRecode.setOnClickListener(this);
        this.videoTest.setOnClickListener(this);
        this.userIcon.setOnClickListener(this);
        this.connectStatus.setOnClickListener(this);
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void widgetClick(View paramView) {
        switch (paramView.getId())
        {
            default:
                break;
            case R.id.dot0:
                setCurView(0);
                setCurDot(0);
                break;
            case R.id.dot1:
                setCurView(1);
                setCurDot(1);
                break;
            case R.id.dot2:
                setCurView(2);
                setCurDot(2);
                break;
            case R.id.vixtel_btn_back:
                startActivity(UserInfoActivity.class);
                break;
            case R.id.setting:
                if (!MWifiManager.getIntance().isBoxConnectNetwork())
                {
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                startActivity(SettingActivity.class);
                break;
            case R.id.userInfo:
                if (!MWifiManager.getIntance().isWifiConnect())
                {
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                startActivity(CustomActivity.class);
                break;
            case R.id.settingOTT:
                if (!MWifiManager.getIntance().isWifiConnect())
                {
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                startActivity(SettingOTTActivity.class);
                break;
            case R.id.connect_status:
                Intent localIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(localIntent);
                break;
            case R.id.openTest:
                if (!MWifiManager.getIntance().isBoxConnectNetwork()){
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                Log.d(TAG,"++++++++++++++++++"+DataManager.getInstance().getAccount());
                if ((DataManager.getInstance().getAccount() == null) || (DataManager.getInstance().getAccount().length() < 1)){
                    showToast(getString(R.string.account_null));
                    startActivity(CustomActivity.class);
                    break;
                }
                if ((DataManager.getInstance().getWoNumber() == null) || (DataManager.getInstance().getWoNumber().length() < 1)){
                    showToast(getString(R.string.wo_number_null));
                    startActivity(CustomActivity.class);
                    break;
                }
                if(DataManager.getInstance().getOpenData()==null || DataManager.getInstance().getOpenData().size()<1){
                    showToast(getString(R.string.url_null));
                    startActivity(SettingActivity.class);
                    break;
                }
                startActivity(NewOpenTestActivity.class);
                break;
            case R.id.questionTest:
                if (!MWifiManager.getIntance().isBoxConnectNetwork()){
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                if ((DataManager.getInstance().getAccount() == null) || (DataManager.getInstance().getAccount().length() < 1)){
                    showToast(getString(R.string.account_null));
                    startActivity(CustomActivity.class);
                    break;
                }
                if(DataManager.getInstance().getTroubleData()==null || DataManager.getInstance().getTroubleData().size()<1){
                    showToast(getString(R.string.url_null));
                    startActivity(SettingActivity.class);
                    break;
                }
                startActivity(NewQuestionActivity.class);
                break;
            case R.id.manualTest:
                if (!MWifiManager.getIntance().isBoxConnectNetwork()){
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
                Log.d(TAG,"++++++++++++++++++"+DataManager.getInstance().getAccount());
                if ((DataManager.getInstance().getAccount() == null) || (DataManager.getInstance().getAccount().length() < 1)){
                    showToast(getString(R.string.account_null));
                    startActivity(CustomActivity.class);
                    break;
                }
                if(DataManager.getInstance().getManualData()==null || DataManager.getInstance().getManualData().size()<1){
                    showToast(getString(R.string.url_null));
                    startActivity(SettingActivity.class);
                    break;
                }
                startActivity(ManualActivity.class);
                break;
            case R.id.networkTest:
                if (!MWifiManager.getIntance().isWifiConnect() && !MWifiManager.getIntance().isNetworkConnected() ){
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
//                if ((DataManager.getInstance().getAccount() == null) || (DataManager.getInstance().getAccount().length() < 1)){
//                    showToast(getString(R.string.account_null));
//                    startActivity(CustomActivity.class);
//                    break;
//                }
                startActivity(NetworkActivity.class);
                break;
            case R.id.videoTest:
                if (!MWifiManager.getIntance().isWifiConnect() && !MWifiManager.getIntance().isNetworkConnected() ){
                    showToast(getString(R.string.wifi_disconnect_tip));
                    break;
                }
//                if ((DataManager.getInstance().getStbID() == null) || (DataManager.getInstance().getStbID().length() < 1)){
//                    showToast(getString(R.string.STB_null));
//                    startActivity(CustomActivity.class);
//                    break;
//                }
//                if(!CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())){
//                    showToast(getString(R.string.not_bridge));
//                    startActivity(SettingOTTActivity.class);
//                    break;
//                }
                startActivity(VideoTestActivity.class);
                break;
        }
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void setCurDot(int paramInt)
    {
        if ((paramInt < 0) || (paramInt > -1 + pics.length) || (this.currentIndex == paramInt))
            return;
        this.dots[paramInt].setEnabled(false);
        this.dots[this.currentIndex].setEnabled(true);
        this.currentIndex = paramInt;
    }
    private void initDots()
    {
        LinearLayout localLinearLayout = findViewById(R.id.dotlayout);
        localLinearLayout.setVisibility(View.GONE);
        this.dots = new ImageView[pics.length];
        for (int i = 0; i < pics.length; i++)
        {
            this.dots[i] = ((ImageView)localLinearLayout.getChildAt(i));
            this.dots[i].setEnabled(true);
            this.dots[i].setOnClickListener(this);
            this.dots[i].setTag(Integer.valueOf(i));
        }
        this.currentIndex = 0;
        this.dots[this.currentIndex].setEnabled(false);
    }

    private void setCurView(int paramInt)
    {
        if ((paramInt < 0) || (paramInt >= pics.length))
            return;
        this.viewPager.setCurrentItem(paramInt);
    }

    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG,"onRestart:isConnect="+MWifiManager.getIntance().isBoxConnectNetwork());
        if (MWifiManager.getIntance().isBoxConnectNetwork())
        {
            CoreManager.getInstance().start();
            this.connectStatus.setVisibility(View.GONE);
//            if (((DataManager.getInstance().getManualData() == null) || (DataManager.getInstance().getManualData().size() < 1)) && ((DataManager.getInstance().getOpenData() == null) || (DataManager.getInstance().getOpenData().size() < 1)) && ((DataManager.getInstance().getTroubleData() == null) || (DataManager.getInstance().getTroubleData().size() < 1)))
//            {
//                this.progressBar.setMessage("设置服务器地址");
//                if (!this.progressBar.isShowing())
//                    this.progressBar.show();
//                WebHttpUtils.getInstance().setUrl(this, 1, DataManager.getInstance().getUrl());
//            }
            return;
        }
        this.connectStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                if (!MWifiManager.getIntance().isBoxConnectNetwork()){
                    finish();
                }else{
                    WebHttpUtils.getInstance().exitPPPoe(this,EXIT_PPPOE);
                }
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onErrorNetClient(int paramInt, String paramString)
    {
        switch (paramInt){
            default:
                Log.d("error:","paramString="+paramString);
                finish();
//                System.exit(0);
                break;
            case EXIT_PPPOE:
                Log.d("error:","paramString="+paramString);
                showToast("退出PPPOE错误,错误信息：" + paramString);
                finish();
//                System.exit(0);
                break;
        }
    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        if(paramString!=null && paramString.length()>0){
            try {
                Log.d(TAG,"paramString="+paramString.substring(paramString.indexOf("{",0),paramString.lastIndexOf("}")+1));
                paramString=paramString.substring(paramString.indexOf("{",0),paramString.lastIndexOf("}")+1);
                JSONObject localJSONObject1 = new JSONObject(paramString);
                String str = localJSONObject1.optString("state");
                int i=-1;
                i = localJSONObject1.optInt(ServerErrorCode.ERROR_CODE);
                switch (i){
                    default:
                        showToast("未知错误！");
                        break;
                    case ServerErrorCode.ERROR_CODE_SUCCESS:
                        switch (paramInt){
                            default:
                                break;
                            case EXIT_PPPOE:
                                Log.d("error:EXIT_PPPOE=","paramString="+paramString);
                                showToast("退出PPPOE成功!");
                                finish();
//                                System.exit(0);
                                break;
                        }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

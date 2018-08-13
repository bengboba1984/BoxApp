package com.fkty.mobileiq.distribution.json;

import android.util.Log;

import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.manager.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by frank_tracy on 2017/12/20.
 */

public class MainJsonUtil {
    public static void catchModelData(String paramString)
    {
        Log.d("MainJsonUtil","catchModelData:paramString="+paramString);
        JSONObject templatesMsg= null;
        try {
            templatesMsg = new JSONObject(paramString);
            if(templatesMsg.optInt("errorCode")== ServerErrorCode.ERROR_CODE_SUCCESS){
                Log.d("MainJsonUtil","catchModelData:errorCode=0");

                JSONArray rowsJson = templatesMsg.optJSONArray("rows");
                if(rowsJson!=null && rowsJson.length()>0){
                    Log.d("MainJsonUtil","rowsJson:rowsJson="+rowsJson.length());
                    ArrayList troubleDataList=new ArrayList();
                    ArrayList openDataList=new ArrayList();
                    ArrayList manualDataList=new ArrayList();
                    int groupId=-1;
                    for(int i=0;i<rowsJson.length();i++){
                        JSONObject localJSONObject1 = rowsJson.optJSONObject(i);
                        TestParamsBean tpb=new TestParamsBean();
                        tpb.setTestId(localJSONObject1.optInt("id"));
                        tpb.setTestName(localJSONObject1.optString("name"));
                        tpb.setTestType(localJSONObject1.optInt("testType"));

                        JSONArray dsList=localJSONObject1.optJSONArray("destinations");
//                        Log.d("MainJsonUtil","dsList.length="+dsList.length());
                        if(dsList!=null && dsList.length()>0){
                            String nodeIp="";
                            for(int j=0;j<dsList.length();j++){
                                nodeIp=dsList.optJSONObject(j).optString("nodeIp");
//                                Log.d("MainJsonUtil","j="+j+"/dsList.optJSONObject(j)="+dsList.optJSONObject(j));
//                                Log.d("MainJsonUtil","j="+j+"/nodeip="+nodeIp);
                                if(nodeIp.length()>0){
                                    tpb.setDestNodeIp(nodeIp);
                                    break;
                                }
                            }
                        }
                        groupId=localJSONObject1.optInt("groupId");
//                        Log.d("MainJsonUtil","groupId="+groupId);
                        switch (groupId){
                            default:
                                break;
                            case CommonField.GROUP_ID_TROUBLE:
                                troubleDataList.add(tpb);
                                break;
                            case CommonField.GROUP_ID_OPEN:
                                openDataList.add(tpb);
                                break;
                            case CommonField.GROUP_ID_MANUAL:
//Log.d("MainJsonUtil","id="+tpb.getTestId()+"/name="+tpb.getTestName()+"/DestNodeIp="+tpb.getDestNodeIp());
                                manualDataList.add(tpb);
                                break;
                        }
                    }
                    DataManager.getInstance().setTroubleData(troubleDataList);
                    DataManager.getInstance().setOpenData(openDataList);
                    DataManager.getInstance().setManualData(manualDataList);
                    Log.d("MainJsonUtil","openDataList="+openDataList.size());
                    Log.d("MainJsonUtil","troubleDataList="+troubleDataList.size());
                    Log.d("MainJsonUtil","manualDataList="+manualDataList.size());
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void parseForeginServer(String paramString)
    {
        if ((paramString != null) && (paramString.length() > 0))
        {
            try
            {
                JSONObject localJSONObject1 = new JSONObject(paramString);
                if (localJSONObject1.optInt("errorCode") == 0)
                {
                    JSONArray localJSONArray = localJSONObject1.optJSONArray("rows");
                    if ((localJSONArray != null) && (localJSONArray.length() > 0))
                    {
                        String str1=null;
                        for(int i=0;i< localJSONArray.length();i++)
                        {
                            str1 = localJSONArray.optJSONObject(i).optString("propertyValue");
                            if ((str1 == null) || (str1.length() <= 0))
                            {
                                continue;
                            }else{
                                break;
                            }
                        }
                        if ((str1 != null) && (str1.length() > 0)){
                            JSONObject localJSONObject3 = new JSONObject(str1);
                            if (localJSONObject3.has("foreignServerUrl"))
                            {

                            }
                        }
                    }
                }
            }
            catch (JSONException localJSONException)
            {
                localJSONException.printStackTrace();
            }
        }
    }
}

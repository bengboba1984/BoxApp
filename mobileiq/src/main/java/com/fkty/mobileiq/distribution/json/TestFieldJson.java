package com.fkty.mobileiq.distribution.json;

import com.fkty.mobileiq.distribution.bean.TestResultBean;
import com.fkty.mobileiq.distribution.bean.TestShowFieldBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.manager.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/21.
 */

public class TestFieldJson {

    public static List<TestResultBean> parseFieldColumn(List<TestShowFieldBean> fieldList, JSONObject resultJSON) {
        ArrayList testResultBeanList = new ArrayList();

        if (fieldList != null && fieldList.size() > 0) {
            Iterator it = fieldList.iterator();
            while (it.hasNext()) {
                TestResultBean trb = new TestResultBean();
                TestShowFieldBean tsfb = (TestShowFieldBean) it.next();
                trb.setTestName(tsfb.getName());
                if (resultJSON != null && resultJSON.length() > 0 && resultJSON.has(tsfb.getColumn())) {
                    if ("int".equals(tsfb.getType())) {
                        int value = resultJSON.optInt(tsfb.getColumn());
                        trb.setContent(value + tsfb.getUnit());
                    } else if ("string".equals(tsfb.getType())) {
                        String value = resultJSON.optString(tsfb.getColumn());
                        trb.setContent(value + tsfb.getUnit());
                    }
                } else {
                    trb.setContent("");
                }
                testResultBeanList.add(trb);
            }
        }
        return testResultBeanList;
    }

    public static void parseUploadResultField(JSONObject resultJSON,int testType) {
        JSONObject rs = DataManager.getInstance().getUploadResult();
        try {
            JSONArray items;
            if (rs.length() == 0) {
                items = new JSONArray();
            }else{
                items=rs.getJSONArray("items");
            }
            JSONObject item =new JSONObject();
            item.put("testType",testType);
            item.put("applicationType",1);
            item.put("errorCode", ServerErrorCode.ERROR_CODE_SUCCESS);
            item.put("account", DataManager.getInstance().getAccount());


            switch (testType){
                default:
                    break;
                case CommonField.TEST_TYPE_PING:
                    item.put("result",resultJSON);
                    break;
                case CommonField.TEST_TYPE_DNS:
                    item.put("result",resultJSON);
                    break;
                case CommonField.TEST_TYPE_HTTP:
                    item.put("result",resultJSON);
                    break;
                case CommonField.TEST_TYPE_SPEED:
                    item.put("result",resultJSON);
                    break;
                case CommonField.TEST_TYPE_TRACE:
                    item.put("result",resultJSON);
                    break;
            }
            items.put(item);

            rs.put("items",items);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
//    public static List<TestResultBean> parseFieldColumn4SameCol(List<TestShowFieldBean> fieldList, JSONObject resultJSON) {
//        ArrayList testResultBeanList = new ArrayList();
//
//        if (fieldList != null && fieldList.size() > 0) {
//            Iterator it = fieldList.iterator();
//            while (it.hasNext()) {
//                TestShowFieldBean tsfb = (TestShowFieldBean) it.next();
//                Iterator<String> rsKeys=resultJSON.keys();
//                while(rsKeys.hasNext()){
//                    String rsKey=rsKeys.next();
//                    if(rsKey.startsWith(tsfb.getColumn())){
//                        TestResultBean trb = new TestResultBean();
//                        trb.setTestName(tsfb.getName());
//                        if ("int".equals(tsfb.getType())) {
//                            int value = resultJSON.optInt(rsKey);
//                            trb.setContent(value + tsfb.getUnit());
//                        } else if ("string".equals(tsfb.getType())) {
//                            String value = resultJSON.optString(rsKey);
//                            trb.setContent(value + tsfb.getUnit());
//                        }else {
//                            trb.setContent("");
//                        }
//                        testResultBeanList.add(trb);
//                    }
//                }
//            }
//        }
//        return testResultBeanList;
//    }

}
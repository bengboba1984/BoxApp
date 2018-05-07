package com.fkty.mobileiq.distribution.json;

import com.fkty.mobileiq.distribution.bean.TestResultBean;
import com.fkty.mobileiq.distribution.bean.TestShowFieldBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/21.
 */

public class TestFieldJson {

    public static List<TestResultBean> parseFieldColumn(List<TestShowFieldBean>  fieldList, JSONObject resultJSON) {
        ArrayList testResultBeanList=new ArrayList();

        if(fieldList!=null && fieldList.size()>0){
            Iterator it=fieldList.iterator();
            while(it.hasNext()){
                TestResultBean trb = new TestResultBean();
                TestShowFieldBean tsfb= (TestShowFieldBean) it.next();
                trb.setTestName(tsfb.getName());
                if(resultJSON!=null && resultJSON.length()>0 && resultJSON.has(tsfb.getColumn())){
                    if("int".equals(tsfb.getType())){
                        int value=resultJSON.optInt(tsfb.getColumn());
                        trb.setContent(value + tsfb.getUnit());
                    }else if("string".equals(tsfb.getType())){
                        String value=resultJSON.optString(tsfb.getColumn());
                        trb.setContent(value + tsfb.getUnit());
                    }
                }else{
                    trb.setContent("");
                }
                testResultBeanList.add(trb);
            }
        }
        return testResultBeanList;
    }
}

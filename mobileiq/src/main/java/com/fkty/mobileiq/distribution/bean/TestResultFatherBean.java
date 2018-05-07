package com.fkty.mobileiq.distribution.bean;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/31.
 */

public class TestResultFatherBean {
    private List<TestResultBean> data;
    private int errorCode;
    private String name;
    private String testName;
    private int testType;

    public List<TestResultBean> getData() {
        return data;
    }

    public void setData(List<TestResultBean> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }
}

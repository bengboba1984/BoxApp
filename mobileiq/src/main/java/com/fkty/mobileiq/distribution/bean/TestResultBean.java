package com.fkty.mobileiq.distribution.bean;

/**
 * Created by frank_tracy on 2018/3/20.
 */

public class TestResultBean
{
    private String content;
    private String testName;

    public TestResultBean() {
    }

    public TestResultBean(String testName, String content ) {
        this.content = content;
        this.testName = testName;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getTestName()
    {
        return this.testName;
    }

    public void setContent(String paramString)
    {
        this.content = paramString;
    }

    public void setTestName(String paramString)
    {
        this.testName = paramString;
    }
}

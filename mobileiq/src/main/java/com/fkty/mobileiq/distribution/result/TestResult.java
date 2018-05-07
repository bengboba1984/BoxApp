package com.fkty.mobileiq.distribution.result;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class TestResult {
    private static TestResult result;
    private ModelTypeBean bean;
    private boolean isNeedSave;

    public static TestResult getInstance()
    {
        if (result == null)
            result = new TestResult();
        return result;
    }

    public void clear()
    {
        if (this.bean != null)
            this.bean = null;
    }

    public ModelTypeBean getResult()
    {
        return this.bean;
    }

    public boolean isNeedSave()
    {
        return this.isNeedSave;
    }

    public void setNeedSave(boolean paramBoolean)
    {
        this.isNeedSave = paramBoolean;
    }

    public void setResult(ModelTypeBean paramModelTypeBean)
    {
        this.bean = paramModelTypeBean;
    }
}

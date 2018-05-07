package com.fkty.mobileiq.distribution.result;

import com.fkty.mobileiq.distribution.bean.TestTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class ModelTypeBean {
    private int modleStates;
    private int modleType;
    private List<TestTypeBean> subTests = new ArrayList();

    public ModelTypeBean(int paramInt1, int paramInt2)
    {
        this.modleType = paramInt1;
        this.modleStates = paramInt2;
    }

    public void addTest(TestTypeBean paramTestTypeBean)
    {
        if (paramTestTypeBean != null)
            this.subTests.add(paramTestTypeBean);
    }

    public void cleanTests()
    {
        if (this.subTests != null)
            this.subTests.clear();
    }

    public int getModleStates()
    {
        return this.modleStates;
    }

    public int getModleType()
    {
        return this.modleType;
    }

    public TestTypeBean getSubTest(int paramInt)
    {
        if (getSubTestsSize() == 0)
            return null;
        return (TestTypeBean)this.subTests.get(paramInt);
    }

    public List<TestTypeBean> getSubTests()
    {
        return this.subTests;
    }

    public int getSubTestsSize()
    {
        return this.subTests.size();
    }

    public void setModleStates(int paramInt)
    {
        this.modleStates = paramInt;
    }

    public void setModleType(int paramInt)
    {
        this.modleType = paramInt;
    }

    public void setSubTests(List<TestTypeBean> paramList)
    {
        this.subTests = paramList;
    }
}

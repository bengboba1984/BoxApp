package com.fkty.mobileiq.distribution.bean;

import java.util.Map;

/**
 * Created by frank_tracy on 2018/3/20.
 */
public class TestShowFieldBean
{
    private String column;
    private boolean enable;
    private String name;
    private int ratio;
    private String type;
    private String unit;

    public TestShowFieldBean(String column, boolean enable, String name, int ratio, String type, String unit) {
        this.column = column;
        this.enable = enable;
        this.name = name;
        this.ratio = ratio;
        this.type = type;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getColumn()
    {
        return this.column;
    }

    public String getName()
    {
        return this.name;
    }

    public int getRatio()
    {
        return this.ratio;
    }

    public String getType()
    {
        return this.type;
    }

    public boolean isEnable()
    {
        return this.enable;
    }

    public void setColumn(String paramString)
    {
        this.column = paramString;
    }

    public void setEnable(boolean paramBoolean)
    {
        this.enable = paramBoolean;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setRatio(int paramInt)
    {
        this.ratio = paramInt;
    }

    public void setType(String paramString)
    {
        this.type = paramString;
    }

}
package com.fkty.mobileiq.distribution.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by frank_tracy on 2018/3/30.
 */

public class TestChildBean implements Parcelable
{
    public static final Parcelable.Creator<TestChildBean> CREATOR = new Parcelable.Creator()
    {
        public TestChildBean createFromParcel(Parcel paramParcel)
        {
            return new TestChildBean(paramParcel);
        }

        public TestChildBean[] newArray(int paramInt)
        {
            return new TestChildBean[paramInt];
        }
    };
    private String result;
    private int taskErrorCode;
    private String templateName;
    private String testName;
    private int testType;

    public TestChildBean()
    {
    }

    protected TestChildBean(Parcel paramParcel)
    {
        this.templateName = paramParcel.readString();
        this.testType = paramParcel.readInt();
        this.taskErrorCode = paramParcel.readInt();
        this.result = paramParcel.readString();
        this.testName = paramParcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public String getResult()
    {
        return this.result;
    }

    public int getTaskErrorCode()
    {
        return this.taskErrorCode;
    }

    public String getTemplateName()
    {
        return this.templateName;
    }

    public String getTestName()
    {
        return this.testName;
    }

    public int getTestType()
    {
        return this.testType;
    }

    public void setResult(String paramString)
    {
        this.result = paramString;
    }

    public void setTaskErrorCode(int paramInt)
    {
        this.taskErrorCode = paramInt;
    }

    public void setTemplateName(String paramString)
    {
        this.templateName = paramString;
    }

    public void setTestName(String paramString)
    {
        this.testName = paramString;
    }

    public void setTestType(int paramInt)
    {
        this.testType = paramInt;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.templateName);
        paramParcel.writeInt(this.testType);
        paramParcel.writeInt(this.taskErrorCode);
        paramParcel.writeString(this.result);
        paramParcel.writeString(this.testName);
    }
}
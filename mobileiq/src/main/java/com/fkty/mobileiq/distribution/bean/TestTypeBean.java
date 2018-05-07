package com.fkty.mobileiq.distribution.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class TestTypeBean implements Parcelable {
    public static final Parcelable.Creator<TestTypeBean> CREATOR = new Parcelable.Creator()
    {
        public TestTypeBean createFromParcel(Parcel paramParcel)
        {
            return new TestTypeBean(paramParcel);
        }

        public TestTypeBean[] newArray(int paramInt)
        {
            return new TestTypeBean[paramInt];
        }
    };
    private String result;
    private int status;
    private String testName;
    private int testType;

    public TestTypeBean()
    {
    }

    protected TestTypeBean(Parcel paramParcel)
    {
        this.status = paramParcel.readInt();
        this.testType = paramParcel.readInt();
        this.testName = paramParcel.readString();
        this.result = paramParcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public String getResult()
    {
        return this.result;
    }

    public int getStatus()
    {
        return this.status;
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

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
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
        paramParcel.writeInt(this.status);
        paramParcel.writeInt(this.testType);
        paramParcel.writeString(this.testName);
        paramParcel.writeString(this.result);
    }
}

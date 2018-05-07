package com.fkty.mobileiq.distribution.bean;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class TestParamsBean
{
    private String destNodeIp;
    private boolean detachPrevious;
    private ParameterBean parameter;
    private List<String> resources;
    private int testId;
    private String testName;
    private int testType;

    public String getDestNodeIp()
    {
        return this.destNodeIp;
    }

    public ParameterBean getParameter()
    {
        return this.parameter;
    }

    public List<String> getResources()
    {
        return this.resources;
    }

    public int getTestId()
    {
        return this.testId;
    }

    public String getTestName()
    {
        return this.testName;
    }

    public int getTestType()
    {
        return this.testType;
    }

    public boolean isDetachPrevious()
    {
        return this.detachPrevious;
    }

    public void setDestNodeIp(String paramString)
    {
        this.destNodeIp = paramString;
    }

    public void setDetachPrevious(boolean paramBoolean)
    {
        this.detachPrevious = paramBoolean;
    }

    public void setParameter(ParameterBean paramParameterBean)
    {
        this.parameter = paramParameterBean;
    }

    public void setResources(List<String> paramList)
    {
        this.resources = paramList;
    }

    public void setTestId(int paramInt)
    {
        this.testId = paramInt;
    }

    public void setTestName(String paramString)
    {
        this.testName = paramString;
    }

    public void setTestType(int paramInt)
    {
        this.testType = paramInt;
    }

    public static class ParameterBean
    {
        private String defaultValue;
        private int dependTestId;
        private long taskBeginTime;
        private int taskExecuteCount;
        private long taskId;
        private int taskInterval;
        private long taskLifeTime;
        private String testParameter;
        private long testTimeout;
        private int testType;

        public String getDefaultValue()
        {
            return this.defaultValue;
        }

        public int getDependTestId()
        {
            return this.dependTestId;
        }

        public long getTaskBeginTime()
        {
            return this.taskBeginTime;
        }

        public int getTaskExecuteCount()
        {
            return this.taskExecuteCount;
        }

        public long getTaskId()
        {
            return this.taskId;
        }

        public int getTaskInterval()
        {
            return this.taskInterval;
        }

        public long getTaskLifeTime()
        {
            return this.taskLifeTime;
        }

        public String getTestParameter()
        {
            return this.testParameter;
        }

        public long getTestTimeout()
        {
            return this.testTimeout;
        }

        public int getTestType()
        {
            return this.testType;
        }

        public void setDefaultValue(String paramString)
        {
            this.defaultValue = paramString;
        }

        public void setDependTestId(int paramInt)
        {
            this.dependTestId = paramInt;
        }

        public void setTaskBeginTime(long paramLong)
        {
            this.taskBeginTime = paramLong;
        }

        public void setTaskExecuteCount(int paramInt)
        {
            this.taskExecuteCount = paramInt;
        }

        public void setTaskId(long paramLong)
        {
            this.taskId = paramLong;
        }

        public void setTaskInterval(int paramInt)
        {
            this.taskInterval = paramInt;
        }

        public void setTaskLifeTime(long paramLong)
        {
            this.taskLifeTime = paramLong;
        }

        public void setTestParameter(String paramString)
        {
            this.testParameter = paramString;
        }

        public void setTestTimeout(long paramLong)
        {
            this.testTimeout = paramLong;
        }

        public void setTestType(int paramInt)
        {
            this.testType = paramInt;
        }

        public static class DefaultValueBean
        {
            private int connectTime;
            private int connectedPercent;
            private int downloadTime;
            private int firstByteTime;
            private int firstPagePercent;
            private int firstPageTime;
            private int loadedPercent;
            private int meanQuality;
            private int pageLoadTime;
            private int resolveTime;
            private int resolvedPercent;
            private int successPercent;
            private int throughput;
            private int totalTime;

            public int getConnectTime()
            {
                return this.connectTime;
            }

            public int getConnectedPercent()
            {
                return this.connectedPercent;
            }

            public int getDownloadTime()
            {
                return this.downloadTime;
            }

            public int getFirstByteTime()
            {
                return this.firstByteTime;
            }

            public int getFirstPagePercent()
            {
                return this.firstPagePercent;
            }

            public int getFirstPageTime()
            {
                return this.firstPageTime;
            }

            public int getLoadedPercent()
            {
                return this.loadedPercent;
            }

            public int getMeanQuality()
            {
                return this.meanQuality;
            }

            public int getPageLoadTime()
            {
                return this.pageLoadTime;
            }

            public int getResolveTime()
            {
                return this.resolveTime;
            }

            public int getResolvedPercent()
            {
                return this.resolvedPercent;
            }

            public int getSuccessPercent()
            {
                return this.successPercent;
            }

            public int getThroughput()
            {
                return this.throughput;
            }

            public int getTotalTime()
            {
                return this.totalTime;
            }

            public void setConnectTime(int paramInt)
            {
                this.connectTime = paramInt;
            }

            public void setConnectedPercent(int paramInt)
            {
                this.connectedPercent = paramInt;
            }

            public void setDownloadTime(int paramInt)
            {
                this.downloadTime = paramInt;
            }

            public void setFirstByteTime(int paramInt)
            {
                this.firstByteTime = paramInt;
            }

            public void setFirstPagePercent(int paramInt)
            {
                this.firstPagePercent = paramInt;
            }

            public void setFirstPageTime(int paramInt)
            {
                this.firstPageTime = paramInt;
            }

            public void setLoadedPercent(int paramInt)
            {
                this.loadedPercent = paramInt;
            }

            public void setMeanQuality(int paramInt)
            {
                this.meanQuality = paramInt;
            }

            public void setPageLoadTime(int paramInt)
            {
                this.pageLoadTime = paramInt;
            }

            public void setResolveTime(int paramInt)
            {
                this.resolveTime = paramInt;
            }

            public void setResolvedPercent(int paramInt)
            {
                this.resolvedPercent = paramInt;
            }

            public void setSuccessPercent(int paramInt)
            {
                this.successPercent = paramInt;
            }

            public void setThroughput(int paramInt)
            {
                this.throughput = paramInt;
            }

            public void setTotalTime(int paramInt)
            {
                this.totalTime = paramInt;
            }
        }

        public static class TestParameterBean
        {
            private boolean headOnly;
            private int itemTimeoutTime;
            private int maxDownloadSize;
            private int maxLoadTime;
            private int maxPageDepth;
            private int maxSubCount;
            private int minLoadPercent;
            private int primaryResultIndex;
            private String userAgent;
            private String validResponseCodes;

            public int getItemTimeoutTime()
            {
                return this.itemTimeoutTime;
            }

            public int getMaxDownloadSize()
            {
                return this.maxDownloadSize;
            }

            public int getMaxLoadTime()
            {
                return this.maxLoadTime;
            }

            public int getMaxPageDepth()
            {
                return this.maxPageDepth;
            }

            public int getMaxSubCount()
            {
                return this.maxSubCount;
            }

            public int getMinLoadPercent()
            {
                return this.minLoadPercent;
            }

            public int getPrimaryResultIndex()
            {
                return this.primaryResultIndex;
            }

            public String getUserAgent()
            {
                return this.userAgent;
            }

            public String getValidResponseCodes()
            {
                return this.validResponseCodes;
            }

            public boolean isHeadOnly()
            {
                return this.headOnly;
            }

            public void setHeadOnly(boolean paramBoolean)
            {
                this.headOnly = paramBoolean;
            }

            public void setItemTimeoutTime(int paramInt)
            {
                this.itemTimeoutTime = paramInt;
            }

            public void setMaxDownloadSize(int paramInt)
            {
                this.maxDownloadSize = paramInt;
            }

            public void setMaxLoadTime(int paramInt)
            {
                this.maxLoadTime = paramInt;
            }

            public void setMaxPageDepth(int paramInt)
            {
                this.maxPageDepth = paramInt;
            }

            public void setMaxSubCount(int paramInt)
            {
                this.maxSubCount = paramInt;
            }

            public void setMinLoadPercent(int paramInt)
            {
                this.minLoadPercent = paramInt;
            }

            public void setPrimaryResultIndex(int paramInt)
            {
                this.primaryResultIndex = paramInt;
            }

            public void setUserAgent(String paramString)
            {
                this.userAgent = paramString;
            }

            public void setValidResponseCodes(String paramString)
            {
                this.validResponseCodes = paramString;
            }
        }
    }
}

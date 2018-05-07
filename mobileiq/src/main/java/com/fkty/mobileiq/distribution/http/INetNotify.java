package com.fkty.mobileiq.distribution.http;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public interface INetNotify {
     int GET_FOREGIN_SERVER = 2;
     int NET_LOGIN = 1;

     void onErrorNetClient(int paramInt, String paramString);

     void onFailedNetClient(int paramInt, String paramString);

     void onSuccessNetClient(int paramInt, String paramString);
}

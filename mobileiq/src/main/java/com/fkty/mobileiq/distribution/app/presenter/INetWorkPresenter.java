package com.fkty.mobileiq.distribution.app.presenter;

import com.fkty.mobileiq.distribution.inter.IBasicPresenter;

/**
 * Created by frank_tracy on 2018/4/8.
 */

public interface INetWorkPresenter extends IBasicPresenter {
//    void upload(String paramString);

    void start(String paramString);

    void stop();

    void getCaptureFile(String filePath,String localPath);
}

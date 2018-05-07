package com.fkty.mobileiq.distribution.app.model;

import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.inter.IBasicModel;

/**
 * Created by frank_tracy on 2018/4/10.
 */

public interface INetWorkModel extends IBasicModel {
    void upload(String paramString, IBasicHandler.Callback paramCallback);

    void startCapture(String fileName,IBasicHandler.Callback paramCallback);

    void stopCapture(IBasicHandler.Callback paramCallback);

    void getCaptureFile(String filePath,String localPath,IBasicHandler.Callback paramCallback);
}

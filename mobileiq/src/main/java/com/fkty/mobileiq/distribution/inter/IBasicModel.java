package com.fkty.mobileiq.distribution.inter;

/**
 * Created by frank_tracy on 2017/12/22.
 */

public interface IBasicModel extends IBasicHandler, ILifeRecycle
{
    void setWorkCallback(IBasicHandler.Callback paramCallback);
}
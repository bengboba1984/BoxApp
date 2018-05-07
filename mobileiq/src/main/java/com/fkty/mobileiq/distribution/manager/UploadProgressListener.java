package com.fkty.mobileiq.distribution.manager;

import java.io.File;

/**
 * Created by frank_tracy on 2018/4/12.
 */

public interface UploadProgressListener {
    void onUploadProgress(int currentStep, long uploadSize, File file);
}

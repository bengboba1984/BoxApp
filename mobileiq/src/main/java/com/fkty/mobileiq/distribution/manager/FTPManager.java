package com.fkty.mobileiq.distribution.manager;

import android.provider.ContactsContract;
import android.util.Log;

import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.FTPConstant;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by frank_tracy on 2018/4/12.
 */

public class FTPManager {
//    private String hostName;
//    private int serverPort;
//    private String userName;
//    private String password;
    private FTPClient ftpClient;

    private static FTPManager instance;

    private FTPManager() {
        ftpClient=new FTPClient();
//        hostName = "";
//        serverPort = -1;
//        userName = "";
//        password = "";
    }

    /**
     * @return
     * @throws
     * @Title: getInstance
     * @Description: 单例方式提供对象
     */
    public static FTPManager getInstance() {
        if (instance == null) {
            synchronized (FTPManager.class) {
                if (instance == null) {
                    instance = new FTPManager();
                }
            }
        }
        return instance;
    }
    private boolean uploadingSingle(File localFile,
                                    UploadProgressListener listener) throws IOException {
        boolean flag = true;
        // 不带进度的方式
        // // 创建输入流
        // InputStream inputStream = new FileInputStream(localFile);
        // // 上传单个文件
        // flag = ftpClient.storeFile(localFile.getName(), inputStream);
        // // 关闭文件流
        // inputStream.close();

        // 带有进度的方式
        BufferedInputStream buffIn = new BufferedInputStream(
                new FileInputStream(localFile));
        ProgressInputStream progressInput = new ProgressInputStream(buffIn,
                listener, localFile);
        flag = ftpClient.storeFile(localFile.getName(), progressInput);
        buffIn.close();

        return flag;
    }
    public void uploadSingleFile(File singleFile, String remotePath,
                                 UploadProgressListener listener) throws IOException {

        // 上传之前初始化
        this.uploadBeforeOperate(remotePath, listener);

        boolean flag;
        flag = uploadingSingle(singleFile, listener);
        if (flag) {
            listener.onUploadProgress(FTPConstant.FTP_UPLOAD_SUCCESS, 0,
                    singleFile);
        } else {
            listener.onUploadProgress(FTPConstant.FTP_UPLOAD_FAIL, 0,
                    singleFile);
        }

        // 上传完成之后关闭连接
        this.uploadAfterOperate(listener);
    }

    public void uploadMultiFile(LinkedList<File> fileList, String remotePath,String functionName,
                                UploadProgressListener listener) throws IOException {

        // 上传之前初始化
        this.uploadBeforeOperate(remotePath, listener);

        boolean flag;

        for (File singleFile : fileList) {

            String fileName=singleFile.getName();
            Log.d("FTPManager","uploadMultiFile:fileName="+fileName);
            String[] fileNameParts;
            if(fileName!=null && !"".equals(fileName)){
                fileNameParts=fileName.split("_");
                if(fileNameParts!=null && fileNameParts.length>1 && fileNameParts[0]!=null && !"".equals(fileNameParts[0])){
//                    String[] rp=remotePath.split("/");
                    Log.d("FTPManager","uploadMultiFile:"+fileNameParts[0]);
                    boolean isFlag=ftpClient.makeDirectory(fileNameParts[0]);
//                    Log.d("FTPManager","makeDirectory:"+isFlag);
                    isFlag=ftpClient.changeWorkingDirectory(fileNameParts[0]);
//                    Log.d("FTPManager","changeWorkingDirectory:"+isFlag);
//                    Log.d("FTPManager","uploadMultiFile:"+functionName);
                    isFlag=ftpClient.makeDirectory(functionName);
//                    Log.d("FTPManager","makeDirectory:"+isFlag);
                    isFlag=ftpClient.changeWorkingDirectory(functionName);
//                    Log.d("FTPManager","changeWorkingDirectory:"+isFlag);
                }else{
                    ftpClient.makeDirectory(functionName);
                    ftpClient.changeWorkingDirectory(functionName);
                }

            }

            flag = uploadingSingle(singleFile, listener);
            if (flag) {
                listener.onUploadProgress(FTPConstant.FTP_UPLOAD_SUCCESS, 0,
                        singleFile);
            } else {
                listener.onUploadProgress(FTPConstant.FTP_UPLOAD_FAIL, 0,
                        singleFile);
            }
            ftpClient.changeWorkingDirectory("/"+remotePath);
        }

        // 上传完成之后关闭连接
        this.uploadAfterOperate(listener);
    }

    private void uploadBeforeOperate(String remotePath,
                                     UploadProgressListener listener) throws IOException {

        // 打开FTP服务
        try {
            this.openConnect();
            listener.onUploadProgress(FTPConstant.FTP_CONNECT_SUCCESSS, 0,
                    null);
        } catch (IOException e1) {
            Log.d("FTPManager","uploadBeforeOperate:"+e1.toString());
            listener.onUploadProgress(FTPConstant.FTP_CONNECT_FAIL, 0, null);
            return;
        }

        // 设置模式
        ftpClient.setFileTransferMode(org.apache.commons.net.ftp.FTP.STREAM_TRANSFER_MODE);
        // FTP下创建文件夹
        ftpClient.makeDirectory(remotePath);
        // 改变FTP目录
        Log.d("FTPManager","remotePath:"+"/"+remotePath);
        ftpClient.changeWorkingDirectory(remotePath);
        // 上传单个文件

    }

    private void uploadAfterOperate(UploadProgressListener listener)
            throws IOException {
        this.closeConnect();
        listener.onUploadProgress(FTPConstant.FTP_DISCONNECT_SUCCESS, 0, null);
    }

    public void closeConnect() throws IOException {
        if (ftpClient != null) {
            // 退出FTP
            ftpClient.logout();
            // 断开连接
            ftpClient.disconnect();
        }
    }

    public void openConnect() throws IOException {
        HashMap ftpInfo=DataManager.getInstance().getFtpInfo();

        Log.d("FTPManager","openConnect:"+"/"+ftpInfo.get("FTP_PORT"));
        // 中文转码
        ftpClient.setControlEncoding("UTF-8");
        int reply; // 服务器响应值
        // 连接至服务器
        Log.d("FTPManager","openConnect:"+"/"+2);
        ftpClient.connect(ftpInfo.get("FTP_HOST_NAME")!=null?ftpInfo.get("FTP_HOST_NAME").toString():FTPConstant.HOST_NAME, ftpInfo.get("FTP_PORT")!=null?Integer.parseInt(ftpInfo.get("FTP_PORT").toString()):FTPConstant.PORT);
        Log.d("FTPManager","openConnect:"+"/"+3);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        Log.d("FTPManager","openConnect:"+"/"+4);
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        }
        Log.d("FTPManager","openConnect:"+"/"+5);
        // 登录到服务器
        ftpClient.login(ftpInfo.get("FTP_USER_NAME")!=null?ftpInfo.get("FTP_USER_NAME").toString():FTPConstant.USER_NAME, ftpInfo.get("FTP_PASSWORD")!=null?ftpInfo.get("FTP_PASSWORD").toString():FTPConstant.PASSWORD);
        Log.d("FTPManager","openConnect:"+"/"+6);
        // 获取响应值
        reply = ftpClient.getReplyCode();
        Log.d("FTPManager","openConnect:"+"/"+!FTPReply.isPositiveCompletion(reply));
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        } else {
            // 获取登录信息
            FTPClientConfig config = new FTPClientConfig(ftpClient
                    .getSystemType().split(" ")[0]);
            config.setServerLanguageCode("zh");
            ftpClient.configure(config);
            // 使用被动模式设为默认
            ftpClient.enterLocalPassiveMode();
            // 二进制文件支持
            ftpClient
                    .setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        }
    }


}

package com.fkty.mobileiq.distribution.bean;

/**
 * Created by frank_tracy on 2017/12/6.
 */

public class LoginInfo {
    private String jobnumber;
    private String password;
    private String unit;
    private String userName;

    public LoginInfo() {
    }

    public LoginInfo(String jobnumber, String password, String unit, String userName) {
        this.jobnumber = jobnumber;
        this.password = password;
        this.unit = unit;
        this.userName = userName;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

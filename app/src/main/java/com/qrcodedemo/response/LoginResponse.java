package com.qrcodedemo.response;

/**
 * Created by LG on 2018/3/9.
 */

public class LoginResponse {

    private String erroCode;
    private boolean ckeckResult;

    public LoginResponse(String erroCode, boolean ckeckResult) {
        this.erroCode = erroCode;
        this.ckeckResult = ckeckResult;
    }

    public String getErroCode() {
        return erroCode;
    }

    public void setErroCode(String erroCode) {
        this.erroCode = erroCode;
    }

    public boolean isCkeckResult() {
        return ckeckResult;
    }

    public void setCkeckResult(boolean ckeckResult) {
        this.ckeckResult = ckeckResult;
    }
}

package com.qrcodedemo.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by LG on 2018/3/12.
 */

@Root(name = "CheckLogin" ,strict = false)
@Namespace(reference = "http://szgree.com.cn/")
public class LoginRequestModel {

    public LoginRequestModel(String username, String userPwd) {
        this.username = username;
        this.userPwd = userPwd;
    }

    @Element(name = "userName", required = false)
    public String username;
    @Element(name = "userPwd", required = false)
    public String userPwd;
}

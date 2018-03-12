package com.qrcodedemo.response;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by LG on 2018/3/12.
 */

@Root(name = "CheckLoginResponse")
public class LoginResponseModel {
    @Attribute(name = "xmls", empty = "http://szgree.com.cn/", required = false)
    public String nameSpace;
    @Element(name = "CheckLoginResult")
    public boolean result;
    @Element(name = "error")
    public String error;
}

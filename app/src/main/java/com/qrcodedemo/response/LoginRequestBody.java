package com.qrcodedemo.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by LG on 2018/3/12.
 */


@Root(name = "soap:Body", strict = false)
public class LoginRequestBody {

    public LoginRequestBody(LoginRequestModel checkLogin) {
        CheckLogin = checkLogin;
    }

    @Element(name = "CheckLogin", required = false)
    public LoginRequestModel CheckLogin;
}



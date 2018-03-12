package com.qrcodedemo.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by LG on 2018/3/12.
 */

@Root(name = "Body",strict = false)
public class LoginResponseBody {
    @Element(name = "CheckLoginResponse", required = false)
    public LoginResponseModel responseModel;
}

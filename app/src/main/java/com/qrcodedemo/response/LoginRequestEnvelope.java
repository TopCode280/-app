package com.qrcodedemo.response;

/**
 * Created by LG on 2018/3/12.
 */


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope")
})

public class LoginRequestEnvelope {
    public LoginRequestEnvelope(LoginRequestBody body) {
        this.body = body;
    }

    @Element(name = "soap:Body", required = false)
    public LoginRequestBody body;
}


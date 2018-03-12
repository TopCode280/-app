package com.qrcodedemo.view;

import com.qrcodedemo.response.LoginRequestEnvelope;
import com.qrcodedemo.response.LoginResponse;
import com.qrcodedemo.response.LoginResponseEnvelope;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LG on 2018/3/10.
 */

public interface VerifyLoginInterface {
    //get内部网址需更换
    @Headers({
            "Content-Type: application/soap+xml; charset=utf-8 ",
            "Accept-Charset: utf-8"
    })
    @POST("DataSwitchService.asmx")
    Call<LoginResponseEnvelope> login(@Body LoginRequestEnvelope envelope);

}

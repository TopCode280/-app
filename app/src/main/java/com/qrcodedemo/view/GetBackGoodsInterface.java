package com.qrcodedemo.view;

import com.qrcodedemo.response.GetBackResponse;
import com.qrcodedemo.response.GetInputResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LG on 2018/3/10.
 */

public interface GetBackGoodsInterface {

    //get内部网址需更换
    @GET("neibwangzhi")
    Call<GetBackResponse> getBackGoods();

}

package com.qrcodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qrcodedemo.response.LoginRequestBody;
import com.qrcodedemo.response.LoginRequestEnvelope;
import com.qrcodedemo.response.LoginRequestModel;
import com.qrcodedemo.response.LoginResponseEnvelope;
import com.qrcodedemo.view.BaseInterface;
import com.qrcodedemo.view.VerifyLoginInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener,BaseInterface {

    private String TAG = LaunchActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText passEditText;
    private Button loginButton;
    private Button exitButton;

    private HttpLoggingInterceptor interceptor;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.i(TAG, "onCreat()");
        initView();
    }

    private void initView() {

        nameEditText = (EditText) findViewById(R.id.uesr_input);
        passEditText = (EditText) findViewById(R.id.passport_input);

        loginButton = (Button) findViewById(R.id.login_button);
        exitButton = (Button) findViewById(R.id.exit_button);
        interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        loginButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

    }

    //此处为模拟服务器校验登录
    private void onVerifyLogin(String name,String password) {

        Log.i(TAG, "onVerifyLogin()");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        VerifyLoginInterface verifyLogin = retrofit.create(VerifyLoginInterface.class);
        LoginRequestModel loginRequestModel = new LoginRequestModel(name, password);
        LoginRequestBody loginRequestBody = new LoginRequestBody(loginRequestModel);
        LoginRequestEnvelope loginRequestEnvelope = new LoginRequestEnvelope(loginRequestBody);
        Call<LoginResponseEnvelope> call = verifyLogin.login(loginRequestEnvelope);
        call.enqueue(new Callback<LoginResponseEnvelope>() {
            @Override
            public void onResponse(Call<LoginResponseEnvelope> call, Response<LoginResponseEnvelope> response) {
                Log.i(TAG, "onResponse"+response.toString());
            }

            @Override
            public void onFailure(Call<LoginResponseEnvelope> call, Throwable t) {
                Log.e(TAG,"onFailure" +t.toString());
            }
        });

    }


    private void startActivitys() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
//                startActivitys();
//                onVerifyLogin(nameEditText.getEditableText().toString(), passEditText.getEditableText().toString());
                startActivitys();
                break;
            case R.id.exit_button:
                finish();
                break;
            default:
                break;
        }
    }
}

package com.qrcodedemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qrcodedemo.response.GetBackResponse;
import com.qrcodedemo.response.GetInputResponse;
import com.qrcodedemo.view.BaseInterface;
import com.qrcodedemo.view.GetBackGoodsInterface;
import com.qrcodedemo.view.GetInputGoodsInterface;
import com.qrcodedemo.zxing.activity.CaptureActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,BaseInterface{

    private Context context;

    private String TAG = MainActivity.class.getSimpleName();
    private int input_request_code = 101;
    private int back_request_code = 102;

    private List<Map<String, Object>> inputGoodsMap = new ArrayList<>();
    private List<Map<String, Object>> backGoodsMap = new ArrayList<>();

//    private HashMap<Integer, Object> inputGoodsMap = new HashMap<>();
//    private HashMap<Integer, Object> backGoodsMap = new HashMap<>();
    private TextView mInputText;
    private TextView mBackText;
    private Button mScanButton;
    private Button mGetAllButton;
    private Button mBackButton;
    private Button mBackAllButton;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mInputText = (TextView) findViewById(R.id.good_input_text);
        mBackText = (TextView) findViewById(R.id.good_back_text);

        mScanButton = (Button) findViewById(R.id.scan_input_button);
        mBackButton = (Button) findViewById(R.id.scan_back_button);
        mGetAllButton = (Button) findViewById(R.id.scan_input_all_button);
        mBackAllButton = (Button) findViewById(R.id.scan_back_all_button);
        mListView = (ListView) findViewById(R.id.all_good_list);
        String[] nameInput = new String[] {"Apple", "Banana", "Cherry", "Coco", "Kiwi",
                "Orange", "Pear", "Strawberry", "Watermelon"};
        int[] idInput = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i=0;i<9;i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("id", idInput[i]);
            temp.put("name", nameInput[i]);
            inputGoodsMap.add(temp);
        }

        String[] nameBack = new String[] {"江西省", "广东省", "福建省", "安徽省", "山东省",
                "陕西省", "山西省", "辽宁省", "云南省"};
        int[] idBack = new int[]{100, 101, 102, 103, 104, 105, 106, 107, 108};
        for (int i=0;i<9;i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("id", idBack[i]);
            temp.put("name", nameBack[i]);
            backGoodsMap.add(temp);
        }
        mScanButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mGetAllButton.setOnClickListener(this);
        mBackAllButton.setOnClickListener(this);
        requestPermission();
    }

    private void requestPermission() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.requestEach(Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.VIBRATE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            Log.d(TAG, permission.name + " is granted. More info should be provided.");
                        }else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });

    }

    //二维码扫描返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                //显示扫描到的内容
//                text.setText(bundle.getString("result"));
            }
    }

    //得到所有进货
    /**
     * 响应点击事件
     * */
    private void getInputGoods() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInputGoodsInterface getInputGoods = retrofit.create(GetInputGoodsInterface.class);
        Call<GetInputResponse> call = getInputGoods.getInputGoods();
        call.enqueue(new Callback<GetInputResponse>() {
            @Override
            public void onResponse(Call<GetInputResponse> call, Response<GetInputResponse> response) {
                Log.i(TAG, "getInputGoods==onResponse()");
                mInputText.setText(null);
                mBackText.setText(null);
                SimpleAdapter adapter = new SimpleAdapter(context, inputGoodsMap, R.layout.layout_item,
                        new String[]{"id", "name"}, new int[]{R.id.text1, R.id.text3});
                mListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<GetInputResponse> call, Throwable t) {
                Log.e(TAG, "getInputGoods==onFailure()");
                mInputText.setText(null);
                mBackText.setText(null);
                SimpleAdapter adapter = new SimpleAdapter(context, inputGoodsMap, R.layout.layout_item,
                        new String[]{"id", "name"}, new int[]{R.id.text1, R.id.text3});
                mListView.setAdapter(adapter);
            }
        });
    }

    //得到所有推货清单
    /**
     * 响应点击事件
     * */
    private void getBackGoods() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetBackGoodsInterface getBackGoods = retrofit.create(GetBackGoodsInterface.class);
        Call<GetBackResponse> call = getBackGoods.getBackGoods();
        call.enqueue(new Callback<GetBackResponse>() {
            @Override
            public void onResponse(Call<GetBackResponse> call, Response<GetBackResponse> response) {

            }

            @Override
            public void onFailure(Call<GetBackResponse> call, Throwable t) {

            }
        });
    }

    private int getRandom(List<Map<String, Object>> list) {
        int random = (int) (Math.random() * (1 + list.size()));
        return random;
    }

    private String message;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_input_button:
                mBackText.setText(null);
                int getInt = getRandom(inputGoodsMap);
                Log.i(TAG, getInt + "");
                if (getInt < inputGoodsMap.size()) {
                    message = inputGoodsMap.get(getInt).toString();
                    mInputText.setText(message);
                }else {
                    mInputText.setText(null);
                    Toast.makeText(this, "没有找到该商品", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, input_request_code);
                break;
            case R.id.scan_input_all_button:
                mInputText.setText(null);
                mBackText.setText(null);
                SimpleAdapter adapter = new SimpleAdapter(this, inputGoodsMap, R.layout.layout_item,
                        new String[]{"id", "name"}, new int[]{R.id.text1, R.id.text3});
                mListView.setAdapter(adapter);
//                getInputGoods();
                break;
            case R.id.scan_back_button:
                mInputText.setText(null);
                int getInt1 = getRandom(inputGoodsMap);
                Log.i(TAG, getInt1 + "");
                if (getInt1 < backGoodsMap.size()) {
                    message = backGoodsMap.get(getInt1).toString();
                    mBackText.setText(message);
                }else {
                    mBackText.setText(null);
                    Toast.makeText(this, "没有找到该商品", Toast.LENGTH_LONG).show();
                }
                Intent intent1 = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent1);
                break;
            case R.id.scan_back_all_button:
                mInputText.setText(null);
                mBackText.setText(null);
                SimpleAdapter adapter1 = new SimpleAdapter(this, backGoodsMap, R.layout.layout_item,
                        new String[]{"id", "name"}, new int[]{R.id.text1, R.id.text3});
                adapter1.notifyDataSetChanged();
                mListView.setAdapter(adapter1);
                break;
            default:

                break;
        }
    }
}

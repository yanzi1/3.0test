package com.me.fanyin.zbme.views.exam.remote;

import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * API
 * Created by xunice on 16/3/15.
 */
public class ApiModel {


    ResultListener resultListener;

    public ApiModel(ResultListener resultListener) {
        this.resultListener = resultListener;
    }
    
    public void getData(Call<String> call){
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    resultListener.onSuccess(result);
                }else{
                    resultListener.onError(new Exception(response.code()+""));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                resultListener.onError(new Exception(t.getMessage()));
            }
        });
    }

}

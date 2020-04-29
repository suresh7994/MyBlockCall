package com.example.myblockcall;


import com.example.myblockcall.modal.NumberList;
import com.example.myblockcall.modal.Result;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("contact.php")
    Call<ResponseContact> insertBlocknumber(
            @Field("name") String name,
            @Field("mobile") String mobile

    );

@Headers({"application-id: MY-APPLICATION-ID",
        "secret-key: MY-SECRET-KEY",
        "application-type: REST"})
    @GET("contactindex.php")
    Call<NumberList> getContact();
}

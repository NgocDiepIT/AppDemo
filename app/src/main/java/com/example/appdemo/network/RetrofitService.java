package com.example.appdemo.network;

import com.example.appdemo.json_models.request.CreateStatusSendForm;
import com.example.appdemo.json_models.request.LikeStatusSendForm;
import com.example.appdemo.json_models.request.LoginSendForm;
import com.example.appdemo.json_models.request.RegisterSendForm;
import com.example.appdemo.json_models.response.Friend;
import com.example.appdemo.json_models.response.Status;
import com.example.appdemo.json_models.response.UserInfor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST(APIStringRoot.LOGIN)
    @Headers({APIStringRoot.HEADER})
    Call<UserInfor> login(@Body LoginSendForm sendForm);

    @POST(APIStringRoot.REGISTER)
    @Headers({APIStringRoot.HEADER})
    Call<UserInfor> register(@Body RegisterSendForm sendForm);

    @GET(APIStringRoot.GET_ALL_POST)
    @Headers({APIStringRoot.HEADER})
    Call<List<Status>> getAllPost(@Query("userId") String userId);

    @POST(APIStringRoot.CREATE_POST)
    @Headers({APIStringRoot.HEADER})
    Call<Status> createPost(@Body CreateStatusSendForm sendForm);

    @POST(APIStringRoot.LIKE_POST)
    @Headers({APIStringRoot.HEADER})
    Call<Void> likePost(@Body LikeStatusSendForm sendForm);

    @GET(APIStringRoot.GET_ALL_FRIEND)
    @Headers({APIStringRoot.HEADER})
    Call<ArrayList<Friend>> getAllFriend(@Query("userId") String userId);
}
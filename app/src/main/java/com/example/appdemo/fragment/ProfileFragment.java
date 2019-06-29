package com.example.appdemo.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.example.appdemo.R;
import com.example.appdemo.dbcontext.RealmContext;
import com.example.appdemo.json_models.response.ProfileUser;
import com.example.appdemo.json_models.response.UserInfor;
import com.example.appdemo.network.RetrofitService;
import com.example.appdemo.network.RetrofitUtils;
import com.example.appdemo.utils.Utils;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private RetrofitService retrofitService;
    UserInfor userInfor;
    SliderLayout sliderLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        getProfile(userInfor.getUsername(), userInfor.getUserId());
        return view;
    }

    private void init(View view) {
        userInfor = RealmContext.getInstance().getUser();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        sliderLayout = view.findViewById(R.id.slider);
    }

    private void getProfile(String username, String userId){
        retrofitService.getProfileUser(username, userId).enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                ProfileUser profileUser = response.body();
                if(response.code() == 200 && profileUser != null){
//                    Log.d("bkhub", profileUser.toString());
                    String[] coverPhoto = profileUser.getCoverPhoto();
                    showSlider(profileUser.getCoverPhoto());
                } else {
                    Utils.showToast(getActivity(), "Fail!");
                }
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });
    }

    private void showSlider(String[] coverPhoto){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for(String url : coverPhoto){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .image(url)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .bundle(new Bundle());

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);//đặt hiệu ứng
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//dấu 3 chấm nằm ở trung tâm ảnh
        sliderLayout.setDuration(4000);//đặt thời gian tự động chuyển ảnh
    }
}

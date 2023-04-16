package org.techtowm.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {
    String LOGIN_URL = "http://61.83.14.115/";

    @FormUrlEncoded
    @POST("retrofit_simplelogin.php")
    Call<String> getUserLogin(
            @Field("userId") String userId,
            @Field("userPassword") String userPassword
    );
}

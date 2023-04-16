package org.techtowm.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface {
    String REGIST_URL = "http://122.254.177.150/";

    @FormUrlEncoded
    @POST("retrofit_simpleregister.php")
    Call<String> getUserRegist(
            @Field("userID") String userID,
            @Field("userPassword") String userPassword,
            @Field("userName") String userName,
            @Field("userAge") int userAge
    );
}

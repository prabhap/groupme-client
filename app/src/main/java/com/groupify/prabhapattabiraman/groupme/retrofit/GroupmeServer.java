package com.groupify.prabhapattabiraman.groupme.retrofit;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GroupmeServer {
    @GET("/groups")
    Call<List<Map<String, String>>> listGroups(@Query("l") String l);

    @POST("/groups/create")
    @FormUrlEncoded
    Call<ResponseBody> createGroup(@Field("name") String name, @Field("geoLocation") String geoLocation,
                                   @Field("range") int range, @Field("open") boolean open);
}

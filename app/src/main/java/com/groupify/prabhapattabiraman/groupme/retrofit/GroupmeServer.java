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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupmeServer {
    @GET("/user/{userId}/unSubscribedGroups")
    Call<List<Map<String, String>>> listGroups(@Path("userId") String userId, @Query("l") String l);

    @POST("/user/{userId}/groups/create")
    @FormUrlEncoded
    Call<ResponseBody> createGroup(@Path("userId") String userId, @Field("name") String name, @Field("geoLocation") String geoLocation,
                                   @Field("range") int range, @Field("open") boolean open);

    @POST("/user/{userId}/groups/{groupId}/conversation")
    @FormUrlEncoded
    Call<ResponseBody> createConversation(@Path("userId") String userId, @Path("groupId") int groupId, @Field("conversation") String text);

    @POST("/users/create")
    @FormUrlEncoded
    Call<String> createUser(@Field("phoneNumber") String phoneNumber);

    @GET("/user/{userId}/groups/{groupId}/conversations")
    Call<List<Map<String, String>>> getConversations(@Path("userId") String userId, @Path("groupId") int groupId);

    @POST("users/{userId}/groups/{groupId}/register")
    Call<List<Map<String, String>>> registerAndGetConversations(@Path("userId") String userId, @Path("groupId") int groupId);
}

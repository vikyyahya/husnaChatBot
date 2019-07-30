package id.vikyyahya.mychatbot.network;

import java.lang.reflect.Array;
import java.util.ArrayList;

import id.vikyyahya.mychatbot.model.ChatResp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

   // @POST("backend/api/dBmK5m/users/register")
   // Call<RegisterResp> insertRegister(@Body RegisterReq registerReq);

    @GET("conversation_start.php")
    Call<ChatResp> chatResponse(
            @Query("say") String say,
            @Query("convo_id") String convoId
    );
}

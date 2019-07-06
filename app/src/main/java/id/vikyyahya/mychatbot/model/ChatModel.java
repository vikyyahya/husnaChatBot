package id.vikyyahya.mychatbot.model;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.network.ApiBuilder;
import id.vikyyahya.mychatbot.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatModel implements ChatContract.Model {


    @Override
    public void sendChatToBot(final OnFinishedListener onFinishedListener, String say) {

        ApiService service = ApiBuilder.getClient().create(ApiService.class);
        Call<ChatResp> call = service.chatResponse(say);
        call.enqueue(new Callback<ChatResp>() {
            @Override
            public void onResponse(Call<ChatResp> call, Response<ChatResp> response) {
                if(response.isSuccessful()){
                    onFinishedListener.onFinishedSuccess(response.body());
                }else {
                    onFinishedListener.onFinishedFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChatResp> call, Throwable t) {
                onFinishedListener.onFailure();

            }
        });
    }
}

package id.vikyyahya.mychatbot.model;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.network.ApiBuilder;
import id.vikyyahya.mychatbot.network.ApiService;
import id.vikyyahya.mychatbot.util.PrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatModel implements ChatContract.Model {


    @Override
    public void sendChatToBot(final OnFinishedListener onFinishedListener, String say,String convoid) {

        ApiService service = ApiBuilder.getClient().create(ApiService.class);
        Call<ChatResp> call = service.chatResponse(say,convoid);
        call.enqueue(new Callback<ChatResp>() {
            @Override
            public void onResponse(Call<ChatResp> call, Response<ChatResp> response) {
                if(response.isSuccessful()){
                    onFinishedListener.onFinishedSuccess(response.body());
                    ChatResp user = response.body();

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

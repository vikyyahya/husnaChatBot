package id.vikyyahya.mychatbot;

import java.util.ArrayList;

import id.vikyyahya.mychatbot.model.ChatObject;
import id.vikyyahya.mychatbot.model.ChatResp;

public interface ChatContract {
    interface View {

        void notifyAdapterObjectAdded(int position);
        void scrollChatDown();
        void notifyAdapterObjectRemove(int position);
        void speakOut(String speakText);
        void inputChat(String text);

    }

    interface Presenter {

        void attachView(ChatContract.View view);
        void removeItem();
        void addLoading();
        void onEditTextActionDone(String inputText);
        ArrayList<ChatObject> getChatObjects();
        void requestDataFromServer(String say );
    }

    interface Model {
        interface OnFinishedListener {
            void onFinishedSuccess(ChatResp chatResp);
            void onFinishedFail(String error);
            void onFailure();
        }

        void sendChatToBot(OnFinishedListener onFinishedListener, String say);
    }

}

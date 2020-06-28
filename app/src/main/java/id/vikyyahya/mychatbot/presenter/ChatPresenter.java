package id.vikyyahya.mychatbot.presenter;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.model.ChatInput;
import id.vikyyahya.mychatbot.model.ChatModel;
import id.vikyyahya.mychatbot.model.ChatObject;
import id.vikyyahya.mychatbot.model.ChatResp;
import id.vikyyahya.mychatbot.model.ChatResponse;


public class ChatPresenter implements ChatContract.Presenter, ChatContract.Model.OnFinishedListener{

    private ArrayList<ChatObject> chatObjects;
    private ChatContract.View view;
    private ChatContract.Model model;

    public ChatPresenter(ChatContract.View v) {
        // Create the ArrayList for the chat objects
        this.view = v;
        this.chatObjects = new ArrayList<>();
        this.model = new ChatModel();

        // Add an initial greeting message
        ChatResponse greetingMsg = new ChatResponse();
        greetingMsg.setText("Assalamu'alaikum, Hallo nama saya Husna ada yang bisa saya bantu ?");
        chatObjects.add(greetingMsg);
//        view.speakOut("Assalamu'alaikum, Hallo nama saya Husna ada yang bisa saya bantu ?");


//        ChatInput chatInput = new ChatInput();
//        chatInput.setText("gdgd");
//        chatObjects.add(chatInput);

    }

    @Override
    public void attachView(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void removeItem() {

    }

    @Override
    public void addLoading() {

    }

    @Override
    public ArrayList<ChatObject> getChatObjects() {
        return this.chatObjects;
    }

    @Override
    public void requestDataFromServer(String say,String convoid) {
        model.sendChatToBot(this,say,convoid);
    }

    @Override
    public void onEditTextActionDone(String inputText) {
        // Create new input object
        ChatInput inputObject = new ChatInput();
        inputObject.setText(inputText);

        // Add it to the list and tell the adapter we added something
        this.chatObjects.add(inputObject);
        view.notifyAdapterObjectAdded(chatObjects.size() - 1);

        // Also scroll down if we aren't at the bottom already
        view.scrollChatDown();
    }

    @Override
    public void onFinishedSuccess(ChatResp chatResp) {
        responseChat(chatResp);

    }

    @Override
    public void onFinishedFail(String error) {
        ChatResponse responseMsg = new ChatResponse();
        responseMsg.setText("Maaf gangguan Jaringan");
        chatObjects.add(responseMsg);
        view.scrollChatDown();
        view.speakOut("Maaf gangguan Jaringan");

    }

    @Override
    public void onFailure() {
        responseErrorChat();
    }

    private void responseChat(ChatResp chatResp){
        ChatResponse responseMsg = new ChatResponse();
        responseMsg.setText(chatResp.getBotsay());
//        user.setIdcon(chatResp.getConvoId());
//        USR = user;
//        Log.i("tess", "sss pres" + USR.getIdcon());
//        Log.i("Response",chatResp.getBotsay());

        chatObjects.add(responseMsg);
        view.scrollChatDown();
        view.speakOut(chatResp.getBotsay());
    }

    private void responseErrorChat(){
        ChatResponse responseMsg = new ChatResponse();
        responseMsg.setText("Maaf ganguan server");
        chatObjects.add(responseMsg);
        view.scrollChatDown();
    }
}

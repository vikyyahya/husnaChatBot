package id.vikyyahya.mychatbot.view.fragment;


import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.MainActivity;
import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.model.ChatInput;
import id.vikyyahya.mychatbot.model.ChatModel;
import id.vikyyahya.mychatbot.model.ChatObject;
import id.vikyyahya.mychatbot.model.User;
import id.vikyyahya.mychatbot.presenter.ChatPresenter;
import id.vikyyahya.mychatbot.util.PrefUtil;
import id.vikyyahya.mychatbot.view.ChatAdapter;

import static android.content.Context.WIFI_SERVICE;
import static id.vikyyahya.mychatbot.MainActivity.USR;


public class ChatFragment extends Fragment implements View.OnClickListener ,ChatContract.View{

    private RecyclerView rvChatList;
    private EditText etSearchBox;
    private ChatAdapter chatAdapter;
    private Button btnSend;
//    private ArrayList<ChatObject> chatObjects;
//    private ChatContract.Model model;
    private ChatPresenter presenter;
    public static String INPUT_CHAT = "input chat";

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        final MainActivity mainActivity = (MainActivity) getActivity();
//        if(isSessionLogin()){
//            Log.i("session","session ok");
//        }



        rvChatList = view.findViewById(R.id.rv_chat);
        etSearchBox = view.findViewById(R.id.et_search_box);
        btnSend = view.findViewById(R.id.btn_send);
        etSearchBox.setOnEditorActionListener(searchBoxListener);
        btnSend.setOnClickListener(this);
        //    Instantiate presenter and attach view
        this.presenter = new ChatPresenter();
        presenter.attachView(this);

        // Instantiate the adapter and give it the list of chat objects
        this.chatAdapter = new ChatAdapter(presenter.getChatObjects());


        if(getArguments() != null) {

            final String a = getArguments().getString(INPUT_CHAT);
            presenter.onEditTextActionDone(a);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.requestDataFromServer(a,"1");
//                    Log.i("tess", "fragment" + USR.getIdcon());
                    WifiManager wm = (WifiManager) mainActivity.getApplicationContext().getSystemService(WIFI_SERVICE);
                    String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                    User user =  new User();
                    user.setIdcon(ip);
                    PrefUtil.putUser(getContext(),PrefUtil.USER_SESSION,user);
//                            statusLoading = 0;
                }
            }, 1000);
        }

        // Set up the RecyclerView with adapter and layout manager
        rvChatList.setAdapter(chatAdapter);
        rvChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChatList.setItemAnimator(new DefaultItemAnimator());


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.i("tess", "fragment" + USR.getIdcon());

    }

    private EditText.OnEditorActionListener searchBoxListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView tv, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!TextUtils.isEmpty(tv.getText())) {


                    final String say = tv.getText().toString();
                    presenter.onEditTextActionDone(say);

                    etSearchBox.getText().clear();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            Log.i("tess", "fragment" + USR.getIdcon());
                            presenter.requestDataFromServer(say,"1");
//                            statusLoading = 0;
                        }
                    }, 1000);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public void notifyAdapterObjectAdded(int position) {
        this.chatAdapter.notifyItemInserted(position);
    }

    @Override
    public void scrollChatDown() {
        this.rvChatList.scrollToPosition(presenter.getChatObjects().size() - 1);
    }

    @Override
    public void notifyAdapterObjectRemove(int position) {

    }

    @Override
    public void speakOut(String speakText) {

    }

    @Override
    public void inputChat(String text) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_send:
                final String say = etSearchBox.getText().toString();
                presenter.onEditTextActionDone(say);

                etSearchBox.getText().clear();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.requestDataFromServer(say,"1");
//                            statusLoading = 0;
                    }
                }, 1000);

                break;
        }

    }
    boolean isSessionLogin() {
        return PrefUtil.getUser(getContext(), PrefUtil.USER_SESSION) != null;
    }
}

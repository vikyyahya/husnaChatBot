package id.vikyyahya.mychatbot;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.vikyyahya.mychatbot.presenter.ChatPresenter;
import id.vikyyahya.mychatbot.view.ChatAdapter;
import id.vikyyahya.mychatbot.view.fragment.ChatFragment;
import id.vikyyahya.mychatbot.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements ChatContract.View, View.OnClickListener {

    private RecyclerView rvChatList;
    private EditText etSearchBox;
    private ChatAdapter chatAdapter;
    private Button btnSend;
    private Fragment pageContent = new HomeFragment();

    private ChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rvChatList = (RecyclerView) findViewById(R.id.rv_chat);
//        etSearchBox = (EditText) findViewById(R.id.et_search_box);
//        btnSend = (Button) findViewById(R.id.btn_send);
//        etSearchBox.setOnEditorActionListener(searchBoxListener);
//        btnSend.setOnClickListener(this);
//
//        // Instantiate presenter and attach view
//        this.presenter = new ChatPresenter();
//        presenter.attachView(this);
//
//        // Instantiate the adapter and give it the list of chat objects
//        this.chatAdapter = new ChatAdapter(presenter.getChatObjects());
//
//        // Set up the RecyclerView with adapter and layout manager
//        rvChatList.setAdapter(chatAdapter);
//        rvChatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        rvChatList.setItemAnimator(new DefaultItemAnimator());

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        pageContent = new HomeFragment();
                        break;

                    case R.id.navigation_chat:
                        pageContent = new ChatFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,pageContent).commit();
                return true;

            }
        });
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
                            presenter.requestDataFromServer(say);
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
                        presenter.requestDataFromServer(say);
//                            statusLoading = 0;
                    }
                }, 1000);

                break;
        }
    }
}

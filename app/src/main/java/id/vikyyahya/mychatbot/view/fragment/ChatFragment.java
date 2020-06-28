package id.vikyyahya.mychatbot.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.R;
//import id.vikyyahya.mychatbot.model.User;
import id.vikyyahya.mychatbot.presenter.ChatPresenter;
import id.vikyyahya.mychatbot.util.Encript;
import id.vikyyahya.mychatbot.util.TexttoSpeech;
import id.vikyyahya.mychatbot.view.ChatAdapter;
import static android.content.Context.WIFI_SERVICE;


public class ChatFragment extends Fragment implements  View.OnClickListener, ChatContract.View, RecognitionListener {

    private RecyclerView rvChatList;
    private EditText etSearchBox;
    private ChatAdapter chatAdapter;
    private Button btnSend,btnMic;
    private ChatPresenter presenter;
    public static String INPUT_CHAT = "input chat";
    Encript encript = new Encript();
//    final User user = new User();
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    public static String RESPONE_TEXT;
    private TexttoSpeech ttsutil;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean isAppInstalled = appInstalledOrNot("com.google.android.tts");
        if (isAppInstalled) {
            if (!ttsutil.getDefaultEngineTts().equalsIgnoreCase("com.google.android.tts")) {
//                showWarning("3", "Warning", "warning2", "Silahkan Switch Setting ke Google Text to Speech", "", "Batal", "Ok");
                ttsutil = new TexttoSpeech(getActivity(), new Locale("id", "ID"), "com.google.android.tts");
            }
        } else {
            installTTSApplication();
        }
    }
    public void installTTSApplication() {
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.tts"));
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(marketIntent);
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        rvChatList = view.findViewById(R.id.rv_chat);
        etSearchBox = view.findViewById(R.id.et_search_box);
        btnMic = view.findViewById(R.id.btn_mic);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setVisibility(View.GONE);
        startListeningWithoutDialog();
        etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=s.toString();
                firstWay(view,val);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSend = view.findViewById(R.id.btn_send);
        etSearchBox.setOnEditorActionListener(searchBoxListener);
        btnSend.setOnClickListener(this);
        btnMic.setOnClickListener(this);

        //    Instantiate presenter and attach view
        this.presenter = new ChatPresenter(this);
        presenter.attachView(this);



        // Instantiate the adapter and give it the list of chat objects
        this.chatAdapter = new ChatAdapter(presenter.getChatObjects());


        if (getArguments() != null) {

            final String a = getArguments().getString(INPUT_CHAT);
//            final String b = getArguments().getString(RESPONE_TEXT);
//            Log.i("DATA RESpon",b);

            presenter.onEditTextActionDone(a);



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!cekid()) {
                        WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
                        String ip = encript.md5(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
                        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("key_name", ip);
                        editor.commit();
//                        PrefUtil.putUser(getContext(), PrefUtil.USER_SESSION, user);
//                        Log.i("tess", "use = " + user);

                        presenter.requestDataFromServer(a, ip);
//                            statusLoading = 0;
                    } else {
                        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        String shrepref = pref.getString("key_name", null);
                        Log.i("tess", "sh freg okokok = " + shrepref);
                        presenter.requestDataFromServer(a, shrepref);
                    }
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

        ttsutil = new TexttoSpeech(getActivity(), new Locale("id", "ID"), "com.google.android.tts");
//        this.presenter = new ChatPresenter(this);
//        this.speakOut("Assalamu'alaikum, Hallo nama saya Husna ada yang bisa saya bantu ?");
        ttsutil.speak("Assalamu'alaikum, Hallo nama saya Husna ada yang bisa saya bantu ?");


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
                            if (!cekid()) {
                                WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
                                String ip = encript.md5(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
                                SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("key_name", ip);
                                editor.commit();

//                                PrefUtil.putUser(getContext(), PrefUtil.USER_SESSION, user);
//                                Log.i("tess", "use = " + user);

                                presenter.requestDataFromServer(say, ip);
                            } else {
                                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                String shrepref = pref.getString("key_name", null);
                                Log.i("tess", "sh freg tombol = " + shrepref);
                                presenter.requestDataFromServer(say, shrepref);
//                            statusLoading = 0;
                            }

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
        if(ttsutil != null){
            ttsutil.speak(speakText);
        }
    }

    @Override
    public void inputChat(String text) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                final String say = etSearchBox.getText().toString();
                presenter.onEditTextActionDone(say);
                etSearchBox.getText().clear();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!cekid()) {
                            WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
                            String ip = encript.md5(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
                            SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("key_name", ip);
                            editor.commit();

//                            PrefUtil.putUser(getContext(), PrefUtil.USER_SESSION, user);
//                                Log.i("tess", "use = " + user);

                            presenter.requestDataFromServer(say, ip);
                        } else {
                            SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            String shrepref = pref.getString("key_name", null);
//                            Log.i("tess", "sh freg tombol = " + shrepref);
                            presenter.requestDataFromServer(say, shrepref);
//                            statusLoading = 0;
                        }
                    }
                }, 1000);
                break;

            case R.id.btn_mic:
                speech.startListening(recognizerIntent);
                break;
        }
    }
    boolean cekid() {
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        return pref.getString("key_name", null) != null;
    }
    private void firstWay(View view, String val) {
        Button button1,button2;
        button1 = view.findViewById(R.id.btn_send);
        button2 = view.findViewById(R.id.btn_mic);
        if (val.isEmpty())
        {
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.VISIBLE);
        }
        else
        {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE);
        }
    }

    public void startListeningWithoutDialog() {
        speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(getContext()));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"id-ID");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"id-ID");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        btnMic.setBackgroundTintList(getResources().getColorStateList(R.color.colorBtnNav));
    }
    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        btnMic.setBackgroundTintList(getResources().getColorStateList(R.color.oren));

    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        String text = "";
//        for (String result : matches)
//            text += result + "\n";
        final String finalText = matches.get(0);
        presenter.onEditTextActionDone(finalText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!cekid()) {
                    WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
                    String ip = encript.md5(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
                    SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("key_name", ip);
                    editor.commit();
//                    PrefUtil.putUser(getContext(), PrefUtil.USER_SESSION, user);
//                                Log.i("tess", "use = " + user);
                    presenter.requestDataFromServer(finalText, ip);
                } else {
                    SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String shrepref = pref.getString("key_name", null);
//                            Log.i("tess", "sh freg tombol = " + shrepref);
                    presenter.requestDataFromServer(finalText, shrepref);
//                            statusLoading = 0;
                }
            }
        }, 1000);
    }
    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }
    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(getContext(), "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }
}

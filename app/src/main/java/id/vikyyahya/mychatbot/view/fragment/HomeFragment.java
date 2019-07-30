package id.vikyyahya.mychatbot.view.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import id.vikyyahya.mychatbot.ChatContract;
import id.vikyyahya.mychatbot.MainActivity;
import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.presenter.ChatPresenter;
import id.vikyyahya.mychatbot.view.ChatAdapter;


public class HomeFragment extends Fragment implements View.OnClickListener, ChatContract.View {

    private ChatPresenter presenter;
    private ChatAdapter chatAdapter;
    private RecyclerView rvChatList;
    private CardView cardView1,cardView2,cardView3,cardView4;
    CarouselView carouselView ;
    private BottomNavigationView bottomNavigationView;
    int[] sliderImage = {R.drawable.smk1,R.drawable.smk2,R.drawable.smk3,R.drawable.smk4};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        bottomNavigationView = mainActivity.findViewById(R.id.btn_nav);



        carouselView = view.findViewById(R.id.crsl1);
        carouselView.setPageCount(sliderImage.length);
        carouselView.setImageListener(imageListener);

        cardView1 = view.findViewById(R.id.cv1);
        cardView2 = view.findViewById(R.id.cv2);
        cardView3 = view.findViewById(R.id.cv3);
        cardView4 = view.findViewById(R.id.cv4);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);


        //    Instantiate presenter and attach view
        this.presenter = new ChatPresenter();
        presenter.attachView(this);

        // Instantiate the adapter and give it the list of chat objects
        this.chatAdapter = new ChatAdapter(presenter.getChatObjects());

        // Set up the RecyclerView with adapter and layout manager


        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sliderImage[position]);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.cv1){
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null){

                ChatFragment chatFragment = new ChatFragment();

                Bundle bundle = new Bundle();
                bundle.putString(ChatFragment.INPUT_CHAT,"Info Terkini");
                chatFragment.setArguments(bundle);

                bottomNavigationView.setSelectedItemId(R.id.navigation_chat);


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,chatFragment,ChatFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        }else if (v.getId() == R.id.cv2){
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null){

                ChatFragment chatFragment = new ChatFragment();

                Bundle bundle = new Bundle();
                bundle.putString(ChatFragment.INPUT_CHAT,"Fasilitas");
                chatFragment.setArguments(bundle);


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,chatFragment,ChatFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        }else if (v.getId() == R.id.cv3){
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null){

                ChatFragment chatFragment = new ChatFragment();

                Bundle bundle = new Bundle();
                bundle.putString(ChatFragment.INPUT_CHAT,"Alamat");
                chatFragment.setArguments(bundle);


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,chatFragment,ChatFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        }else if (v.getId() == R.id.cv4){
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null){

                ChatFragment chatFragment = new ChatFragment();

                Bundle bundle = new Bundle();
                bundle.putString(ChatFragment.INPUT_CHAT,"Cara daftar");
                chatFragment.setArguments(bundle);


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,chatFragment,ChatFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        }

    }

    @Override
    public void notifyAdapterObjectAdded(int position) {
//        this.chatAdapter.notifyItemInserted(position);

    }

    @Override
    public void scrollChatDown() {

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
}

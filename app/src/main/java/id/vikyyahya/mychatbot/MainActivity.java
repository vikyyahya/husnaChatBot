package id.vikyyahya.mychatbot;

import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;

import com.synnapps.carouselview.CarouselView;

import id.vikyyahya.mychatbot.model.User;
import id.vikyyahya.mychatbot.presenter.ChatPresenter;
import id.vikyyahya.mychatbot.util.PrefUtil;
import id.vikyyahya.mychatbot.view.ChatAdapter;
import id.vikyyahya.mychatbot.view.fragment.AboutFragment;
import id.vikyyahya.mychatbot.view.fragment.ChatFragment;
import id.vikyyahya.mychatbot.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvChatList;
    private ChatAdapter chatAdapter;

    private Fragment pageContent;
    private ChatPresenter presenter;
    public static User USR ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SMK AL HUSNA");
//     getSupportFragmentManager().beginTransaction().add(R.id.frag_container,pageContent).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
       String shrepref =  pref.getString("key_name",null);

        Log.i ("tess","sh = "+ shrepref);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        pageContent = new HomeFragment();
                        break;
                    case R.id.navigation_chat:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ChatFragment chatFragment = new ChatFragment();
                        Fragment fragment = new ChatFragment();
                        Log.d("ssssss", String.valueOf(fragment));
                        if(!(fragment == chatFragment) ){
                            pageContent = new ChatFragment();
                        }
                        break;
                    case R.id.navigation_about:
                        pageContent = new AboutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, pageContent).commit();
                return true;
            }
        });

//
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

        if (!(fragment == homeFragment)) {
            fragmentTransaction.add(R.id.frag_container,homeFragment);
            fragmentTransaction.commit();
        }
    }
}

package id.vikyyahya.mychatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LaunchScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchscreen);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                Intent intent = new Intent(LaunchScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}

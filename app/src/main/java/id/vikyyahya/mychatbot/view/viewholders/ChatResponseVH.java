package id.vikyyahya.mychatbot.view.viewholders;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.model.ChatObject;

public class ChatResponseVH extends BaseViewHolder implements TextToSpeech.OnInitListener {

    private TextView tvResponseText;
    private TextView tvResponseTime;
    private TextToSpeech tts;

    public ChatResponseVH(@NonNull View itemView) {
        super(itemView);
        this.tvResponseText = (TextView) itemView.findViewById(R.id.tv_response_text);
        this.tvResponseTime = (TextView) itemView.findViewById(R.id.timeResp);
    }

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
    Date currentTime = Calendar.getInstance().getTime();
    String waktu = formatter.format(currentTime);

    @Override
    public void onBindView(ChatObject object) {
        this.tvResponseText.setText(object.getText());
        this.tvResponseTime.setText(waktu);
        Log.i("deb chat res",object.getText());
//        tts = new TextToSpeech();
    }

    @Override
    public void onInit(int status) {

    }
}

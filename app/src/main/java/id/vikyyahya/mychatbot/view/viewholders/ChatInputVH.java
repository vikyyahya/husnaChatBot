package id.vikyyahya.mychatbot.view.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.model.ChatObject;

public class ChatInputVH extends BaseViewHolder {

    private TextView tvInputText;
    private TextView tvTime;


    public ChatInputVH(@NonNull View itemView) {
        super(itemView);
        this.tvInputText = (TextView) itemView.findViewById(R.id.tv_input_text);
        this.tvTime = (TextView) itemView.findViewById(R.id.time);
    }
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
    Date currentTime = Calendar.getInstance().getTime();
    String waktu = formatter.format(currentTime);


    @Override
    public void onBindView(ChatObject object) {
        this.tvInputText.setText(object.getText());
        this.tvTime.setText(waktu);
    }
}

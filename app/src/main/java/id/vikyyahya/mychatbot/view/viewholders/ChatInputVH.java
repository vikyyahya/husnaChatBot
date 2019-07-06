package id.vikyyahya.mychatbot.view.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

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

    @Override
    public void onBindView(ChatObject object) {
        this.tvInputText.setText(object.getText());
    }
}

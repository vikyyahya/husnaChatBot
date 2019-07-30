package id.vikyyahya.mychatbot.view.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.model.ChatObject;

public class ChatResponseVH extends BaseViewHolder {

    private TextView tvResponseText;


    public ChatResponseVH(@NonNull View itemView) {
        super(itemView);
        this.tvResponseText = (TextView) itemView.findViewById(R.id.tv_response_text);

    }

    @Override
    public void onBindView(ChatObject object) {
        this.tvResponseText.setText(object.getText());

    }
}

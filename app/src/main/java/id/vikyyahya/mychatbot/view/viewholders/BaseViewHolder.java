package id.vikyyahya.mychatbot.view.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import id.vikyyahya.mychatbot.model.ChatObject;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {


    BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBindView(ChatObject object);

}

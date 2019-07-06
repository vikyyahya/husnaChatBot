package id.vikyyahya.mychatbot.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.vikyyahya.mychatbot.R;
import id.vikyyahya.mychatbot.model.ChatObject;
import id.vikyyahya.mychatbot.view.viewholders.BaseViewHolder;
import id.vikyyahya.mychatbot.view.viewholders.ChatInputVH;
import id.vikyyahya.mychatbot.view.viewholders.ChatResponseVH;

public class ChatAdapter  extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<ChatObject> chatObjects;

    public ChatAdapter(ArrayList<ChatObject> chatObjects) {
        this.chatObjects = chatObjects;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Create the ViewHolder based on the viewType
        View itemView;
        switch (viewType) {
            case ChatObject.INPUT_OBJECT:
                itemView = inflater.inflate(R.layout.chat_input_layout, parent, false);
                return new ChatInputVH(itemView);
            case ChatObject.RESPONSE_OBJECT:
                itemView = inflater.inflate(R.layout.chat_response_layout, parent, false);
                return new ChatResponseVH(itemView);
            default:
                itemView = inflater.inflate(R.layout.chat_response_layout, parent, false);
                return new ChatResponseVH(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindView(chatObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return chatObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatObjects.get(position).getType();
    }
}

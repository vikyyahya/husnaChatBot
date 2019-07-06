package id.vikyyahya.mychatbot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatResp implements Serializable {
    @SerializedName("convo_id")
    @Expose
    private String convoId;
    @SerializedName("usersay")
    @Expose
    private String usersay;
    @SerializedName("botsay")
    @Expose
    private String botsay;

    public String getConvoId() {
        return convoId;
    }

    public void setConvoId(String convoId) {
        this.convoId = convoId;
    }

    public String getUsersay() {
        return usersay;
    }

    public void setUsersay(String usersay) {
        this.usersay = usersay;
    }

    public String getBotsay() {
        return botsay;
    }

    public void setBotsay(String botsay) {
        this.botsay = botsay;
    }
}

package no.birkett.quietshare;

import java.util.ArrayList;

/**
 * Created by Selva on 10/24/2017.
 */

public class MessageObject extends ArrayList<MessageObject> {
    private String message;
    private String timestamp;

    public MessageObject(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

package network;

public class ChatMessage {
    String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    public ChatMessage() {
        message = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

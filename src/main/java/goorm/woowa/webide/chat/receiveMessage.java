package goorm.woowa.webide.chat;

public class receiveMessage {
    private String message;

    public receiveMessage() {
    }

    public receiveMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        System.out.println();
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

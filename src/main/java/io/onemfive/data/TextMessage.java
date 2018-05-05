package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class TextMessage implements Persistable, Message {

    private Long toDID;
    private Long fromDID;
    private String text;

    public TextMessage() {
    }

    public TextMessage(Long toDID, Long fromDID, String text) {
        this.toDID = toDID;
        this.fromDID = fromDID;
        this.text = text;
    }

    public Long getToDID() {
        return toDID;
    }

    public void setToDID(Long toDID) {
        this.toDID = toDID;
    }

    public Long getFromDID() {
        return fromDID;
    }

    public void setFromDID(Long fromDID) {
        this.fromDID = fromDID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

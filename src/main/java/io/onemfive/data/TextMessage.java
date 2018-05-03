package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class TextMessage implements Persistable, Message {

    private DID to;
    private DID from;
    private String text;

    public TextMessage() {
    }

    public TextMessage(DID to, DID from, String text) {
        this.to = to;
        this.from = from;
        this.text = text;
    }

    public DID getTo() {
        return to;
    }

    public void setTo(DID to) {
        this.to = to;
    }

    public DID getFrom() {
        return from;
    }

    public void setFrom(DID from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

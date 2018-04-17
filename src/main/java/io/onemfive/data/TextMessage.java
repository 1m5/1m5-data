package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class TextMessage implements Message {

    private LID to;
    private LID from;
    private String text;

    public TextMessage() {
    }

    public TextMessage(LID to, LID from, String text) {
        this.to = to;
        this.from = from;
        this.text = text;
    }

    public LID getTo() {
        return to;
    }

    public void setTo(LID to) {
        this.to = to;
    }

    public LID getFrom() {
        return from;
    }

    public void setFrom(LID from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

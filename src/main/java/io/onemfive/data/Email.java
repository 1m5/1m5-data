package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Email implements Serializable {

    private LID to;
    private LID from;
    private String subject;
    private String message;

    public Email() {
    }

    public Email(LID to, LID from, String subject, String message) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.message = message;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

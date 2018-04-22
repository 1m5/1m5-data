package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Email implements Serializable {

    private DID to;
    private DID from;
    private String subject;
    private String message;

    public Email() {
    }

    public Email(DID to, DID from, String subject, String message) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.message = message;
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

package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Email implements Persistable, Serializable {

    private Long id;
    private Long toDID;
    private Long fromDID;
    private String subject;
    private String message;

    public Email() {
    }

    public Email(Long toDID, Long fromDID, String subject, String message) {
        this.toDID = toDID;
        this.fromDID = fromDID;
        this.subject = subject;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

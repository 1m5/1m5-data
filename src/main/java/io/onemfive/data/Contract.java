package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class Contract {

    public enum Status {NEW,ACTIVE,RETIRED}

    private Status status = Status.NEW;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

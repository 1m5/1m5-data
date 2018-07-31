package io.onemfive.data.content;

/**
 * The smallest grouped content on the network.
 *
 * @author objectorange
 */
public abstract class Fragment extends Content {

    public enum Status {
        WRITING, // Writing Fragment
        SAVED // Fragment verified saved to CDN
    }

    private Status status = Status.WRITING;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

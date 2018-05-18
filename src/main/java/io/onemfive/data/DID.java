package io.onemfive.data;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "alias", type = IndexType.Fulltext)
})
public class DID implements Persistable, Serializable {

    public enum Status {UNREGISTERED, ACTIVE, SUSPENDED}

    @Id
    private Long id;
    private String alias;
    private String passphrase;
    private Status status = Status.UNREGISTERED;
    private boolean verified = false;
    private boolean authenticated = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}

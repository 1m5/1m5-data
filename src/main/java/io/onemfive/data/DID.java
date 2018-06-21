package io.onemfive.data;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public enum Provider{I2P}

    @Id
    private Long id;
    private String alias;
    private String passphrase;
    private Status status = Status.UNREGISTERED;
    private boolean verified = false;
    private boolean authenticated = false;
    private KeyPair masterKeys;
    // Identities = Signing Keys: Long-Lived
    private Map<Provider,Map<String,KeyPair>> providerIdentities = new HashMap<>();
    // Encryption Keys: Short-Lived
    private Map<Provider,Map<String,KeyPair>> providerEncryptionKeys = new HashMap<>();

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

    public KeyPair getMasterKeys() {
        return masterKeys;
    }

    public void setMasterKeys(KeyPair masterKeys) {
        this.masterKeys = masterKeys;
    }

    public boolean supportsProvider(Provider provider, String alias) {
        return getIdentity(provider, alias) != null && getEncryptionKeys(provider, alias) != null;
    }

    public boolean addIdentity(Provider provider, String alias, KeyPair keyPair) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        if(identities == null) {
            identities = new HashMap<>();
            providerIdentities.put(provider, identities);
        }
        identities.put(alias,keyPair);
        return true;
    }

    public KeyPair getIdentity(Provider provider, String alias) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        if(identities == null)
            return null;
        else
            return identities.get(alias);
    }

    public boolean removeIdentity(Provider provider, String alias) {
        Map<String,KeyPair> identities = providerIdentities.get(provider);
        if(identities != null) {
            identities.remove(alias);
        }
        return true;
    }

    public boolean addEncryptionKeys(Provider provider, String alias, KeyPair keyPair) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        if(encryptionKeys == null) {
            encryptionKeys = new HashMap<>();
            providerEncryptionKeys.put(provider, encryptionKeys);
        }
        encryptionKeys.put(alias, keyPair);
        return true;
    }

    public KeyPair getEncryptionKeys(Provider provider, String alias) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        if(encryptionKeys == null)
            return null;
        else
            return encryptionKeys.get(alias);
    }

    public boolean removeEncryptionKeys(Provider provider, String alias) {
        Map<String,KeyPair> encryptionKeys = providerEncryptionKeys.get(provider);
        if(encryptionKeys != null) {
            encryptionKeys.remove(alias);
        }
        return true;
    }
}

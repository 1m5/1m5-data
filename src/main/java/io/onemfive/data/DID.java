package io.onemfive.data;

import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.security.*;
import java.util.*;

/**
 * Decentralized IDentification
 *
 * @author objectorange
 */
public class DID implements Persistable, Serializable {

    public enum Status {INACTIVE, ACTIVE, SUSPENDED}

    @Id
    private Long id;
    private String alias;
    private volatile String passphrase;
    private byte[] passphraseHash;
    private String passphraseHashAlgorithm = "PBKDF2WithHmacSHA1";
    private String description = "";
    private Status status = Status.ACTIVE;
    private volatile boolean verified = false;
    private volatile boolean authenticated = false;
    private byte[] identityHash;
    private String identityHashAlgorithm;
    private Map<String,PublicKey> identities = new HashMap<>();
    private Map<String,Peer> peers = new HashMap<>();

    public DID() {}

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

    public void setPassphrase(String passphrase) throws NoSuchAlgorithmException {
        this.passphrase = passphrase;
    }

    public void addPeer(Peer peer) {
        peers.put(peer.getNetwork(),peer);
    }

    public Peer getPeer(String network) {
        return peers.get(network);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public byte[] getPassphraseHash() {
        return passphraseHash;
    }

    public void setPassphraseHash(byte[] passphraseHash) {
        this.passphraseHash = passphraseHash;
    }

    public String getPassphraseHashAlgorithm() {
        return passphraseHashAlgorithm;
    }

    public void setPassphraseHashAlgorithm(String passphraseHashAlgorithm) {
        this.passphraseHashAlgorithm = passphraseHashAlgorithm;
    }

    public byte[] getIdentityHash() {
        return identityHash;
    }

    public void setIdentityHash(byte[] identityHash) {
        this.identityHash = identityHash;
    }

    public String getIdentityHashAlgorithm() {
        return identityHashAlgorithm;
    }

    public void setIdentityHashAlgorithm(String identityHashAlgorithm) {
        this.identityHashAlgorithm = identityHashAlgorithm;
    }

    public PublicKey getPublicKey(String alias) {
        return identities.get(alias);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(identityHash);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DID) {
            DID did2 = (DID)o;
            if(getIdentityHash() != null && did2.getIdentityHash() != null)
                return Arrays.equals(getIdentityHash(), did2.getIdentityHash());
        }
        return false;
    }

    @Override
    public String toString() {
        return new String(getIdentityHash());
    }
}

package io.onemfive.data;

import java.security.*;
import java.util.*;

/**
 * Decentralized IDentification
 *
 * @author objectorange
 */
public class DID implements Persistable, JSONSerializable {

    public enum Status {INACTIVE, ACTIVE, SUSPENDED}

    private String alias;
    private volatile String passphrase;
    private String passphraseHash;
    private String passphraseHashAlgorithm = "PBKDF2WithHmacSHA1"; // Default
    private String description = "";
    private Status status = Status.ACTIVE;
    private volatile Boolean verified = false;
    private volatile Boolean authenticated = false;
    private String identityHash;
    private String identityHashAlgorithm = "PBKDF2WithHmacSHA1"; // Default
    private Map<String,PublicKey> identities = new HashMap<>();
    private Map<String,Peer> peers = new HashMap<>();

    public DID() {}

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

    public String getPassphraseHash() {
        return passphraseHash;
    }

    public void setPassphraseHash(String passphraseHash) {
        this.passphraseHash = passphraseHash;
    }

    public String getPassphraseHashAlgorithm() {
        return passphraseHashAlgorithm;
    }

    public void setPassphraseHashAlgorithm(String passphraseHashAlgorithm) {
        this.passphraseHashAlgorithm = passphraseHashAlgorithm;
    }

    public String getIdentityHash() {
        return identityHash;
    }

    public void setIdentityHash(String identityHash) {
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
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(alias!=null) m.put("alias",alias);
        if(passphrase!=null) m.put("passphrase",passphrase);
        if(passphraseHash!=null) m.put("passphraseHash",passphraseHash);
        if(passphraseHashAlgorithm!=null) m.put("passphraseHashAlgorithm",passphraseHashAlgorithm);
        if(description!=null) m.put("description",description);
        if(status!=null) m.put("status",status.name());
        if(verified!=null) m.put("verified",verified.toString());
        if(authenticated!=null) m.put("authenticated",authenticated.toString());
        if(identityHash!=null) m.put("identityHash",identityHash);
        if(identityHashAlgorithm!=null) m.put("identityHashAlgorithm",identityHashAlgorithm);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("alias")!=null) alias = (String)m.get("alias");
        if(m.get("passphrase")!=null) passphrase = (String)m.get("passphrase");
        if(m.get("passphraseHash")!=null) passphraseHash = ((String)m.get("passphraseHash"));
        if(m.get("passphraseHashAlgorithm")!=null) passphraseHashAlgorithm = (String)m.get("passphraseHashAlgorithm");
        if(m.get("description")!=null) description = (String)m.get("description");
        if(m.get("status")!=null) status = Status.valueOf((String)m.get("status"));
        if(m.get("verified")!=null) verified = Boolean.parseBoolean((String)m.get("verified"));
        if(m.get("authenticated")!=null) authenticated = Boolean.parseBoolean((String)m.get("authenticated"));
        if(m.get("identityHash")!=null) identityHash = ((String)m.get("identityHash"));
        if(m.get("identityHashAlgorithm")!=null) identityHashAlgorithm = (String)m.get("identityHashAlgorithm");
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(identityHash.getBytes());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DID) {
            DID did2 = (DID)o;
            if(getIdentityHash() != null && did2.getIdentityHash() != null)
                return getIdentityHash().equals(did2.getIdentityHash());
        }
        return false;
    }

    @Override
    public String toString() {
        if(identityHash != null)
            return identityHash;
        else if(alias != null)
            return alias;
        else
            return null;
    }
}

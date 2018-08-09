package io.onemfive.data;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.security.*;
import java.util.*;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "hashString", type = IndexType.Fulltext)
})
public class DID implements Persistable, Serializable {

    public static final String MESSAGE_DIGEST_SHA1 = "sha1";
    public static final String MESSAGE_DIGEST_SHA256 = "sha256";
    public static final String MESSAGE_DIGEST_SHA384 = "sha384";
    public static final String MESSAGE_DIGEST_SHA512 = "sha512";

    public static final String ENCODING_BASE64 = "base64";

    public enum Status {INACTIVE, ACTIVE, SUSPENDED}

    @Id
    private Long id;
    private String alias;
    private volatile String passphrase;
    private byte[] passphraseHash;
    private String description = "";
    private Status status = Status.INACTIVE;
    private boolean verified = false;
    private boolean authenticated = false;
    private String hashAlgorithm;
    private byte[] identityHash;
    private Map<String,Peer> peers = new HashMap<>();

    public static DID create(String alias, String passphrase) throws NoSuchAlgorithmException {
        return create(alias, passphrase, MESSAGE_DIGEST_SHA512);
    }

    public static DID create(String alias, String passphrase, String hashAlgorithm) throws NoSuchAlgorithmException {
        DID did = new DID();
        did.setId(new SecureRandom(new byte[32]).nextLong());
        did.setAlias(alias);
        did.setPassphrase(passphrase, hashAlgorithm);
        long nonce = new SecureRandom(new byte[64]).nextLong();
        String key = alias+"|"+passphrase+"|"+nonce;
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        did.setHashAndAlgorithm(md.digest(key.getBytes()), hashAlgorithm);
        did.setStatus(Status.ACTIVE);
        return did;
    }

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

    public void setPassphrase(String passphrase, String hashAlgorithm) throws NoSuchAlgorithmException {
        this.passphrase = passphrase;
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
        this.passphraseHash = md.digest(passphrase.getBytes());
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

    public String getHashString() {
        return new String(identityHash);
    }

    public byte[] getHash() {
        return identityHash;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAndAlgorithm(byte[] hash, String hashAlgorithm) {
        this.identityHash = hash;
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(identityHash);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DID) {
            DID did2 = (DID)o;
            if(getHash() != null && did2.getHash() != null)
                return Arrays.equals(getHash(), did2.getHash());
        }
        return false;
    }

    @Override
    public String toString() {
        return new String(getHash());
    }
}

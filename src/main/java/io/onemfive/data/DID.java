package io.onemfive.data;

import java.util.*;

/**
 * Decentralized IDentification
 *
 * An identity container for both personal and network identities.
 * Requires an alias and passphrase to secure them.
 * Personal identities are managed through the Key Ring Service while
 * network identities are persisted to the local hard drive.
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
    // Identities used for personal identification: Alias, PublicKey
    private Map<String,PublicKey> identities = new HashMap<>();
    // Identities used in peer networks: Network name, NetworkPeer
    private Map<String,NetworkPeer> peers = new HashMap<>();

    public DID() {
        // Ensure default primary key exists
        PublicKey pk = new PublicKey();
        pk.setAlias("primary");
        identities.put("primary", pk);
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

    public void addPeer(NetworkPeer networkPeer) {
        peers.put(networkPeer.getNetwork(), networkPeer);
    }

    public NetworkPeer getPeer(String network) {
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

    public PublicKey getPublicKey() {
        // User primary as alias
        return identities.get("primary");
    }

    public PublicKey getPublicKey(String alias) {
        return identities.get(alias);
    }

    public void addPublicKey(PublicKey publicKey) {
        if(publicKey.getAlias() == null) {
            publicKey.setAlias("primary");
        }
        identities.put(publicKey.getAlias(), publicKey);
    }

    public Collection<PublicKey> availableIdentities(){
        return identities.values();
    }

    public Collection<NetworkPeer> availableNetworkPeers() {
        return peers.values();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(passphrase!=null) m.put("passphrase",passphrase);
        if(passphraseHash!=null) m.put("passphraseHash",passphraseHash);
        if(passphraseHashAlgorithm!=null) m.put("passphraseHashAlgorithm",passphraseHashAlgorithm);
        if(description!=null) m.put("description",description);
        if(status!=null) m.put("status",status.name());
        if(verified!=null) m.put("verified",verified.toString());
        if(authenticated!=null) m.put("authenticated",authenticated.toString());
        if(identities != null && identities.size() > 0) {
            Map<String,Object> ids = new HashMap<>();
            m.put("identities",ids);
            Set<String> aliases = identities.keySet();
            for(String a : aliases) {
                Map<String,Object> key = new HashMap<>();
                ids.put(a, key);
                PublicKey p = (PublicKey)ids.get(a);
                key.put("alias", String.valueOf(p.getAlias()));
                key.put("fingerprint", p.getFingerprint());
                key.put("encodedBase64", p.getEncodedBase64());
            }
        }
        if(peers != null && peers.size() > 0) {
            Map<String,Object> pm = new HashMap<>();
            m.put("peers",pm);
            Set<String> networks = peers.keySet();
            for(String n : networks) {
                pm.put(n,((NetworkPeer)m.get(n)).toMap());
            }
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("passphrase")!=null) passphrase = (String)m.get("passphrase");
        if(m.get("passphraseHash")!=null) passphraseHash = ((String)m.get("passphraseHash"));
        if(m.get("passphraseHashAlgorithm")!=null) passphraseHashAlgorithm = (String)m.get("passphraseHashAlgorithm");
        if(m.get("description")!=null) description = (String)m.get("description");
        if(m.get("status")!=null) status = Status.valueOf((String)m.get("status"));
        if(m.get("verified")!=null) verified = Boolean.parseBoolean((String)m.get("verified"));
        if(m.get("authenticated")!=null) authenticated = Boolean.parseBoolean((String)m.get("authenticated"));
        if(m.get("identities")!=null) {
            Map<String,Object> im = (Map<String,Object>)m.get("identities");
            identities = new HashMap<>();
            Set<String> aliases = im.keySet();
            PublicKey key;
            for(String a : aliases) {
                Map<String,Object> km = (Map<String,Object>)im.get(a);
                key = new PublicKey();
                key.setAlias((String)km.get("alias"));
                key.setFingerprint((String)km.get("fingerprint"));
                key.setEncodedBase64(key.getEncodedBase64());
                identities.put(a, key);
            }
        }
        NetworkPeer p;
        if(m.get("peers")!=null){
            Map<String,Object> pm = (Map<String,Object>)m.get("peers");
            peers = new HashMap<>();
            Set<String> networks = pm.keySet();
            for(String n : networks) {
                p = new NetworkPeer();
                p.fromMap((Map<String,Object>)pm.get(n));
                peers.put(n, p);
            }
        }
    }

    @Override
    public int hashCode() {
        if(passphraseHash!=null)
            return passphraseHash.hashCode();
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DID) {
            DID did2 = (DID)o;
            if(passphraseHash != null && did2.passphraseHash != null)
                return passphraseHash.equals(did2.passphraseHash);
        }
        return false;
    }

    @Override
    public String toString() {
        if(passphraseHash != null)
            return passphraseHash;
        else
            return null;
    }
}

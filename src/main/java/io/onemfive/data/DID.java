package io.onemfive.data;

import io.onemfive.data.security.PublicKey;
import io.onemfive.data.util.Base64;

import java.util.*;

/**
 * Decentralized IDentification
 *
 * @author objectorange
 */
public class DID implements Addressable, Persistable, JSONSerializable {

    public enum Status {INACTIVE, ACTIVE, SUSPENDED}

    private String alias;
    // Hash of Alias - meant for local identification within this application only.
    // Use identities for external identification.
    // May want to change to public key in future based on private
    private String address;
    private String aliasHashAlgorithm = "PBKDF2WithHmacSHA1"; // Default
    private volatile String passphrase;
    private String passphraseHash;
    private String passphraseHashAlgorithm = "PBKDF2WithHmacSHA1"; // Default
    private String description = "";
    private Status status = Status.ACTIVE;
    private volatile Boolean verified = false;
    private volatile Boolean authenticated = false;
    // Identities used for personal identification: Alias, PublicKey
    private Map<String,PublicKey> identities = new HashMap<>();
    // Identities used in peer networks: Peer.Network, Peer
    private Map<Network,Peer> peers = new HashMap<>();

    public DID() {}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAliasHashAlgorithm() {
        return aliasHashAlgorithm;
    }

    public void setAliasHashAlgorithm(String aliasHashAlgorithm) {
        this.aliasHashAlgorithm = aliasHashAlgorithm;
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

    public Peer getPeer(Network network) {
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

    public PublicKey getPublicKey(String alias) {
        return identities.get(alias);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(alias!=null) m.put("alias",alias);
        if(aliasHashAlgorithm!=null) m.put("aliasHashAlgorithm",aliasHashAlgorithm);
        if(address!=null) m.put("address",address);
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
                key.put("algorithm", p.getAlgorithm());
                key.put("format", p.getFormat());
                key.put("encodedInBase64", Base64.encode(p.getEncoded()));
                key.put("class", p.getClass().getName());
            }
        }
        if(peers != null && peers.size() > 0) {
            Map<Network,Object> pm = new HashMap<>();
            m.put("peers",pm);
            Set<Network> networks = peers.keySet();
            for(Network n : networks) {
                pm.put(n,((Peer)m.get(n)).toMap());
            }
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("alias")!=null) alias = (String)m.get("alias");
        if(m.get("address")!=null) address = (String)m.get("address");
        if(m.get("aliasHashAlgorithm")!=null) aliasHashAlgorithm = (String)m.get("aliasHashAlgorithm");
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
                key.setAlgorithm((String)km.get("algorithm"));
                key.setFormat((String)km.get("format"));
                if(key.getEncodedInBase64()!=null) {
                    key.setEncoded(Base64.decode(key.getEncodedInBase64()));
                }
                identities.put(a, key);
            }
        }
        Peer p;
        if(m.get("peers")!=null){
            Map<String,Object> pm = (Map<String,Object>)m.get("peers");
            peers = new HashMap<>();
            Set<String> networks = pm.keySet();
            for(String n : networks) {
                p = new Peer();
                p.fromMap((Map<String,Object>)pm.get(n));
                peers.put(Network.valueOf(n), p);
            }
        }
    }

    @Override
    public int hashCode() {
        if(address!=null)
            return address.hashCode();
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DID) {
            DID did2 = (DID)o;
            if(address != null && did2.address != null)
                return address.equals(did2.address);
        }
        return false;
    }

    @Override
    public String toString() {
        if(address != null)
            return address;
        else
            return null;
    }
}

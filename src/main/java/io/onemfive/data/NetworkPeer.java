package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * @author objectorange
 */
public class NetworkPeer implements Addressable, JSONSerializable {

    public enum Network {
        // Invisible Direct Mesh (1M5 internal project)
        IDM,
        // Invisible Internet Project - https://geti2p.net/
        I2P,
        // TOR - https://www.torproject.org/
        TOR,
        // Clearnet
        CLEAR
    }

    protected String network = Network.CLEAR.name(); // Default
    protected PublicKey publicKey = new PublicKey();
    protected DID did = new DID();

    public NetworkPeer() {}

    public NetworkPeer(String network, PublicKey publicKey) {
        this.network = network;
        this.publicKey = publicKey;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getShortAddress() {
        return publicKey == null ? null : publicKey.getFingerprint();
    }

    @Override
    public String getFullAddress() {
        return publicKey == null ? null : publicKey.getEncodedBase64();
    }

    public DID getDid() {
        return did;
    }

    public void setDid(DID did) {
        this.did = did;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(network!=null) m.put("network",network);
        if(publicKey!=null) m.put("publicKey",publicKey.toMap());
        if(did!=null) m.put("did",did.toMap());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null) network = (String)m.get("network");
        if(m.get("publicKey")!=null) {
            Map<String, Object> mp = (Map<String, Object>) m.get("publicKey");
            publicKey = new PublicKey();
            publicKey.fromMap(mp);
        }
        if(m.get("did")!=null) {
            did = new DID();
            did.fromMap((Map<String,Object>)m.get("did"));
        }
    }
}

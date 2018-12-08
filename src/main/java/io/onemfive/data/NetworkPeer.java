package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * @author objectorange
 */
public class NetworkPeer implements Addressable, JSONSerializable, PIIClearable {

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
    protected DID did = new DID();

    public NetworkPeer() {}

    public NetworkPeer(String network) {
        this.network = network;
    }

    public NetworkPeer(String network, String primaryAlias, String passphrase) {
        this.network = network;
        did.setUsername(primaryAlias);
        did.setPassphrase(passphrase);
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public DID getDid() {
        return did;
    }

    public void setDid(DID did) {
        this.did = did;
    }

    @Override
    public String getShortAddress() {
        // use primary alias
        PublicKey p = did.getPublicKey();
        return (p == null) ? null : p.getShortAddress();
    }

    @Override
    public String getFullAddress() {
        PublicKey p = did.getPublicKey();
        return (p == null) ? null : p.getFullAddress();
    }

    @Override
    public void clearSensitive() {
        if(did!=null) did.clearSensitive();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(network!=null) m.put("network",network);
        if(did!=null) m.put("did",did.toMap());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null) network = (String)m.get("network");
        if(m.get("did")!=null) {
            did = new DID();
            did.fromMap((Map<String,Object>)m.get("did"));
        }
    }

    @Override
    public Object clone() {
        NetworkPeer clone = new NetworkPeer();
        clone.did = (DID)did.clone();
        clone.network = network;
        return clone;
    }
}

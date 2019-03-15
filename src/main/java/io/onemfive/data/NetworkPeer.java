package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * Maintains addresses of the base networked peers including Tor, I2P, and IDN.
 *
 * In 1m5-core, a NetworkPeer is provided set with an IMS address/fingerprint
 * with Tor, I2P, and IDN set if their respective sensors are activated.
 *
 * @author objectorange
 */
public class NetworkPeer implements Addressable, JSONSerializable, PIIClearable {

    public enum Network {
        // Invisible Matrix Services (1M5) - https://github.com/1m5/1m5-core
        IMS,
        // Invisible Direct Network (1DN) - https://github.com/1m5/1m5-1dn
        IDN,
        // Invisible Internet Project (I2P) - https://geti2p.net/
        I2P,
        // The Onion Router (TOR) - https://www.torproject.org/
        TOR,
        // Clearnet
        CLEAR
    }

    protected String network;
    protected DID did;

    public NetworkPeer() {
        this(Network.CLEAR.name(), null, null);
    }

    public NetworkPeer(String network) {
        this(network, null, null);
    }

    public NetworkPeer(String network, String username, String passphrase) {
        this.network = network;
        did = new DID();
        did.setUsername(username);
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
    public String getFingerprint() {
        return did.getPublicKey().getFingerprint();
    }

    public void setFingerprint(String fingerprint) {
        did.getPublicKey().setFingerprint(fingerprint);
    }

    @Override
    public String getAddress() {
        return did.getPublicKey().getAddress();
    }

    public void setAddress(String address) {
        did.getPublicKey().setAddress(address);
    }

    public String getIMSAddress(){
        if(Network.IMS.name().equals(network))
            return getAddress();
        else
            return did.getPeer(Network.IMS.name()).getAddress();
    }

    public void setIMSAddress(String address) {
        if(Network.IMS.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.IMS.name()).setAddress(address);
    }

    public String getIMSFingerprint() {
        if(Network.IMS.name().equals(network))
            return getFingerprint();
        else
            return did.getPeer(Network.IMS.name()).getFingerprint();
    }

    public void setIMSFingerprint(String fingerprint) {
        if(Network.IMS.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.IMS.name()).setFingerprint(fingerprint);
    }

    public String getTorAddress(){
        if(Network.TOR.name().equals(network))
            return getAddress();
        else
            return did.getPeer(Network.TOR.name()).getAddress();
    }

    public void setTorAddress(String address) {
        if(Network.TOR.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.TOR.name()).setAddress(address);
    }

    public String getTorFingerprint() {
        if(Network.TOR.name().equals(network))
            return getFingerprint();
        else
            return did.getPeer(Network.TOR.name()).getFingerprint();
    }

    public void setTorFingerprint(String fingerprint) {
        if(Network.TOR.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.TOR.name()).setFingerprint(fingerprint);
    }

    public String getI2PAddress(){
        if(Network.I2P.name().equals(network))
            return getAddress();
        else
            return did.getPeer(Network.I2P.name()).getAddress();
    }

    public void setI2PAddress(String address) {
        if(Network.I2P.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.I2P.name()).setAddress(address);
    }

    public String getI2PFingerprint() {
        if(Network.I2P.name().equals(network))
            return getFingerprint();
        else
            return did.getPeer(Network.I2P.name()).getFingerprint();
    }

    public void setI2PFingerprint(String fingerprint) {
        if(Network.I2P.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.I2P.name()).setFingerprint(fingerprint);
    }

    public String getIDNAddress(){
        if(Network.IDN.name().equals(network))
            return getAddress();
        else
            return did.getPeer(Network.IDN.name()).getAddress();
    }

    public void setIDNAddress(String address) {
        if(Network.IDN.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.IDN.name()).setAddress(address);
    }

    public String getIDNFingerprint() {
        if(Network.IDN.name().equals(network))
            return getFingerprint();
        else
            return did.getPeer(Network.IDN.name()).getFingerprint();
    }

    public void setIDNFingerprint(String fingerprint) {
        if(Network.IDN.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.IDN.name()).setFingerprint(fingerprint);
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

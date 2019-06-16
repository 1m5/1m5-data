package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

    private static Logger LOG = Logger.getLogger(NetworkPeer.class.getName());

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
        CLEAR,
        // Software Defined Radio (SDR) - https://github.com/1m5/1m5-radio
        SDR,
        // Satellite - https://github.com/1m5/1m5-satellite
        SAT
    }

    protected String network;
    protected DID did;
    protected Boolean isLocal = false;

    public NetworkPeer() {
        this(Network.IMS.name(), null, null);
    }

    public NetworkPeer(String network) {
        this(network, null, null);
    }

    public NetworkPeer(String network, String username, String passphrase) {
        this.network = network;
        did = new DID();
        did.setUsername(username);
        did.setPassphrase(passphrase);
        if(Network.IMS.name().equals(network)) {
            did.addPeer(new NetworkPeer(Network.I2P.name()));
        }
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
        if(did != null) {
            if (Network.IMS.name().equals(network) && did.getPeer(Network.I2P.name()) == null) {
                did.addPeer(new NetworkPeer(Network.I2P.name()));
            }
            this.did = did;
        }
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
        else if(did.getPeer(Network.IMS.name())!=null)
            return did.getPeer(Network.IMS.name()).getAddress();
        else
            return null;
    }

    public void setIMSAddress(String address) {
        if(Network.IMS.name().equals(network))
            setAddress(address);
        did.getPeer(Network.IMS.name()).setAddress(address);
    }

    public String getIMSFingerprint() {
        if(Network.IMS.name().equals(network))
            return getFingerprint();
        else if(did.getPeer(Network.IMS.name())!=null)
            return did.getPeer(Network.IMS.name()).getFingerprint();
        else
            return null;
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
        else if(did.getPeer(Network.TOR.name())!=null)
            return did.getPeer(Network.TOR.name()).getAddress();
        else
            return null;
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
        else if(did.getPeer(Network.TOR.name())!=null)
            return did.getPeer(Network.TOR.name()).getFingerprint();
        else
            return null;
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
        else if(did.getPeer(Network.I2P.name())!=null)
            return did.getPeer(Network.I2P.name()).getAddress();
        else
            return null;
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
        else if(did.getPeer(Network.I2P.name())!=null)
            return did.getPeer(Network.I2P.name()).getFingerprint();
        else
            return null;
    }

    public void setI2PFingerprint(String fingerprint) {
        if(Network.I2P.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.I2P.name()).setFingerprint(fingerprint);
    }

    /**
     * Checks the status of this peer instance and supplied peer instance
     * @param peer the NetworkPeer to check against
     * @return int
     *      -2 if supplied NetworkPeer is not ready
     *      -1 if this NetworkPeer is not ready
     *       0 if not the same and ready
     *       1 if they are the same and ready
     */
    public int getI2PStatus(NetworkPeer peer) {
        int result;
        if(peer.getI2PAddress() == null || peer.getI2PAddress().isEmpty()) result = -2;
        else if(getI2PAddress() == null || getI2PAddress().isEmpty()) result =  -1;
        else if(getI2PAddress().equals(peer.getI2PAddress())) result = 1;
        else if(getAddress() != null && getAddress().equals(peer.getAddress())) result = 1;
        else result = 0;
        return result;
    }

    public String getIDNAddress(){
        if(Network.IDN.name().equals(network))
            return getAddress();
        else if(did.getPeer(Network.IDN.name())!=null)
            return did.getPeer(Network.IDN.name()).getAddress();
        else
            return null;
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
        else if(did.getPeer(Network.IDN.name())!=null)
            return did.getPeer(Network.IDN.name()).getFingerprint();
        else
            return null;
    }

    public void setIDNFingerprint(String fingerprint) {
        if(Network.IDN.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.IDN.name()).setFingerprint(fingerprint);
    }

    public Boolean isLocal() {
        return isLocal;
    }

    public void setLocal(Boolean local) {
        isLocal = local;
    }

    @Override
    public void clearSensitive() {
        if(did!=null) did.clearSensitive();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(network!=null)
            m.put("network",network);
        if(getAddress()!=null)
            m.put("address",getAddress());
        if(getFingerprint()!=null)
            m.put("fingerprint",getFingerprint());
        if(getI2PAddress()!=null)
            m.put("i2pAddress",getI2PAddress());
        if(getI2PFingerprint()!=null)
            m.put("i2pFingerprint",getI2PFingerprint());
        if(getTorAddress()!=null)
            m.put("torAddress",getTorAddress());
        if(getTorFingerprint()!=null)
            m.put("torFingerprint",getTorFingerprint());
        if(getIDNAddress()!=null)
            m.put("idnAddress",getIDNAddress());
        if(getIDNFingerprint()!=null)
            m.put("idnFingerprint",getIDNFingerprint());
        if(getIMSAddress()!=null)
            m.put("imsAddress",getIMSAddress());
        if(getIMSFingerprint()!=null)
            m.put("imsFingerprint",getIMSFingerprint());
        if(did!=null)
            m.put("did",did.toMap());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null)
            network = (String)m.get("network");
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{\n\tlocal: "+isLocal());

        if(getIMSAddress()!=null)
            sb.append("\n\t1m5Address: "+getIMSAddress());
        if(getIMSFingerprint()!=null)
            sb.append("\n\t1m5Fingerprint: "+getIMSFingerprint());

        if(getTorAddress()!=null)
            sb.append("\n\ttorAddress: "+getTorAddress());
        if(getTorFingerprint()!=null)
            sb.append("\n\ttorFingerprint: "+getTorFingerprint());

        if(getI2PAddress()!=null)
            sb.append("\n\ti2pAddress: "+getI2PAddress());
        if(getI2PFingerprint()!=null)
            sb.append("\n\ti2pFingerprint: "+getI2PFingerprint());

        if(getIDNAddress()!=null)
            sb.append("\n\tidnAddress: "+getIDNAddress());
        if(getIDNFingerprint()!=null)
            sb.append("\n\tidnFingerprint: "+getIDNFingerprint());

        sb.append("\n}");
        return sb.toString();
    }
}

package io.onemfive.data;

import io.onemfive.data.util.JSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * Maintains addresses of the base networked peers including Tor, I2P, and IDN.
 *
 * In 1m5-core, a NetworkPeer is provided set with an IMS address/fingerprint
 * with Tor, I2P, IDN, RAD, and LiFi set if their respective sensors are activated.
 *
 * @author objectorange
 */
public class NetworkPeer implements Addressable, JSONSerializable, PIIClearable {

    private static Logger LOG = Logger.getLogger(NetworkPeer.class.getName());

    public enum Network {
        // Invisible Matrix Services (1M5) - https://github.com/1m5/1m5-core
        IMS,
        // Invisible Internet Project (I2P) - https://geti2p.net/ and  - https://github.com/1m5/1m5-i2p
        I2P,
        // The Onion Router (TOR) - https://www.torproject.org/ and https://github.com/1m5/1m5-tor-client
        TOR,
        // Clearnet - https://github.com/1m5/1m5-clearnet
        CLEAR,
        // Software Defined Radio (SDR) - https://github.com/1m5/1m5-radio
        SDR,
        // Light Fidelity (LiFi) - https://github.com/1m5/1m5-lifi
        LIFI
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

    public NetworkPeer(String username, String passphrase) {
        this(Network.IMS.name(), username, passphrase);
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
        else if(did.getPeer(Network.IMS.name())!=null)
            return did.getPeer(Network.IMS.name()).getAddress();
        else
            return null;
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

    public String getSDRAddress(){
        if(Network.SDR.name().equals(network))
            return getAddress();
        else if(did.getPeer(Network.SDR.name())!=null)
            return did.getPeer(Network.SDR.name()).getAddress();
        else
            return null;
    }

    public void setSDRAddress(String address) {
        if(Network.SDR.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.SDR.name()).setAddress(address);
    }

    public String getSDRFingerprint() {
        if(Network.SDR.name().equals(network))
            return getFingerprint();
        else if(did.getPeer(Network.SDR.name())!=null)
            return did.getPeer(Network.SDR.name()).getFingerprint();
        else
            return null;
    }

    public void setSDRFingerprint(String fingerprint) {
        if(Network.SDR.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.SDR.name()).setFingerprint(fingerprint);
    }

    public String getLiFiAddress(){
        if(Network.LIFI.name().equals(network))
            return getAddress();
        else if(did.getPeer(Network.LIFI.name())!=null)
            return did.getPeer(Network.LIFI.name()).getAddress();
        else
            return null;
    }

    public void setLiFiAddress(String address) {
        if(Network.LIFI.name().equals(network))
            setAddress(address);
        else
            did.getPeer(Network.LIFI.name()).setAddress(address);
    }

    public String getLiFiFingerprint() {
        if(Network.LIFI.name().equals(network))
            return getFingerprint();
        else if(did.getPeer(Network.LIFI.name())!=null)
            return did.getPeer(Network.LIFI.name()).getFingerprint();
        else
            return null;
    }

    public void setLiFiFingerprint(String fingerprint) {
        if(Network.LIFI.name().equals(network))
            setFingerprint(fingerprint);
        else
            did.getPeer(Network.LIFI.name()).setFingerprint(fingerprint);
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
        if(getSDRAddress()!=null)
            m.put("sdrAddress",getSDRAddress());
        if(getSDRFingerprint()!=null)
            m.put("sdrFingerprint",getSDRFingerprint());
        if(getLiFiAddress()!=null)
            m.put("lifiAddress",getLiFiAddress());
        if(getLiFiFingerprint()!=null)
            m.put("lifiFingerprint",getLiFiFingerprint());
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
        if(m.get("network")!=null) network = (String)m.get("network");
        if(m.get("address")!=null) setAddress((String)m.get("address"));
        if(m.get("fingerprint")!=null) setFingerprint((String)m.get("fingerprint"));
        if(m.get("i2pAddress")!=null) setI2PAddress((String)m.get("i2pAddress"));
        if(m.get("i2pFingerprint")!=null) setI2PFingerprint((String)m.get("i2pFingerprint"));
        if(m.get("torAddress")!=null) setTorAddress((String)m.get("torAddress"));
        if(m.get("torFingerprint")!=null) setTorFingerprint((String)m.get("torFingerprint"));
        if(m.get("sdrAddress")!=null) setSDRAddress((String)m.get("sdrAddress"));
        if(m.get("sdrFingerprint")!=null) setSDRFingerprint((String)m.get("sdrFingerprint"));
        if(m.get("lifiAddress")!=null) setLiFiAddress((String)m.get("lifiAddress"));
        if(m.get("lifiFingerprint")!=null) setLiFiFingerprint((String)m.get("lifiFingerprint"));
        if(m.get("imsAddress")!=null) setIMSAddress((String)m.get("imsAddress"));
        if(m.get("imsFingerprint")!=null) setIMSFingerprint((String)m.get("imsFingerprint"));
        if(m.get("did")!=null) {
            did = new DID();
            if(m.get("did") instanceof String)
                did.fromMap((Map<String,Object>) JSONParser.parse((String)m.get("did")));
            else
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

        if(getNetwork()!=null)
            sb.append("\n\tnetwork: "+getNetwork());
        if(getAddress()!=null)
            sb.append("\n\taddress: "+getAddress());
        if(getFingerprint()!=null)
            sb.append("\n\tfingerprint: "+getFingerprint());

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

        if(getSDRAddress()!=null)
            sb.append("\n\tsdrAddress: "+getSDRAddress());
        if(getSDRFingerprint()!=null)
            sb.append("\n\tsdrFingerprint: "+getSDRFingerprint());

        if(getLiFiAddress()!=null)
            sb.append("\n\tlifiAddress: "+getLiFiAddress());
        if(getLiFiFingerprint()!=null)
            sb.append("\n\tlifiFingerprint: "+getLiFiFingerprint());

        sb.append("\n}");
        return sb.toString();
    }
}

package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * @author objectorange
 */
public class Peer implements Addressable, JSONSerializable {

    public static final String NETWORK_1DM = "1DM";
    public static final String NETWORK_I2P = "I2P";
    public static final String NETWORK_TOR = "TOR";

    private String network;
    private String address;

    public Peer() {}

    public Peer(String network, String address) {
        this.network = network;
        this.address = address;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(network!=null) m.put("network",network);
        if(address!=null) m.put("address",address);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null) network = (String)m.get("network");
        if(m.get("address")!=null) address = (String)m.get("address");
    }
}

package io.onemfive.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * @author objectorange
 */
public class Peer implements Addressable, JSONSerializable {

    private Network network;
    private String address;

    public Peer() {}

    public Peer(Network network, String address) {
        this.network = network;
        this.address = address;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
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
        if(network!=null) m.put("network",network.name());
        if(address!=null) m.put("address",address);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("network")!=null) network = Network.valueOf((String)m.get("network"));
        if(m.get("address")!=null) address = (String)m.get("address");
    }
}

package io.onemfive.data;

/**
 * A node on the 1M5 peer-to-peer network.
 *
 * @author objectorange
 */
public class Peer implements Addressable {

    public static final String NETWORK_1M5 = "1M5";
    public static final String NETWORK_1MM = "1MM";
    public static final String NETWORK_I2P = "I2P";
    public static final String NETWORK_I2PBOTE = "I2PBOTE";
    public static final String NETWORK_TOR = "TOR";

    private String network;
    private String address;

    public Peer(String network, String address) {
        this.network = network;
        this.address = address;
    }

    public String getNetwork() {
        return network;
    }

    @Override
    public String getAddress() {
        return address;
    }
}

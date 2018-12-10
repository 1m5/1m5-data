package io.onemfive.data;

import io.onemfive.data.util.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class PublicKey implements Addressable, JSONSerializable {

    private String alias;
    private String fingerprint;
    private String address;

    public PublicKey() {}

    public PublicKey(String address) {
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getFingerprint() {
        return fingerprint;
    }

    @Override
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    public void setEncodedBase64(byte[] encoded) {
        this.address = Base64.encode(encoded);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if(alias!=null) m.put("alias",alias);
        if(fingerprint!=null) m.put("fingerprint",fingerprint);
        if(address !=null) m.put("address", address);
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        if(m.get("alias")!=null) alias = (String)m.get("alias");
        if(m.get("fingerprint")!=null) fingerprint = (String)m.get("fingerprint");
        if(m.get("address")!=null) address = (String)m.get("address");
    }

    @Override
    public Object clone() {
        PublicKey clone = new PublicKey();
        clone.alias = alias;
        clone.fingerprint = fingerprint;
        clone.address = address;
        return clone;
    }
}

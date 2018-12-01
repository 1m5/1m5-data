package io.onemfive.data;

import io.onemfive.data.util.Base64;

import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class PublicKey implements Addressable, JSONSerializable {

    private String alias = "primary"; // default
    private String fingerprint;
    private String encodedBase64;

    public PublicKey() {}

    public PublicKey(String encodedBase64) {
        this.encodedBase64 = encodedBase64;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getEncodedBase64() {
        return encodedBase64;
    }

    public void setEncodedBase64(String encodedBase64) {
        this.encodedBase64 = encodedBase64;
    }

    public void setEncodedBase64(byte[] encoded) {
        this.encodedBase64 = Base64.encode(encoded);
    }

    @Override
    public String getShortAddress() {
        return fingerprint;
    }

    @Override
    public String getFullAddress() {
        return encodedBase64;
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    @Override
    public void fromMap(Map<String, Object> m) {

    }
}

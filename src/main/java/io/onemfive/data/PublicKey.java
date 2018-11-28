package io.onemfive.data;

import io.onemfive.data.util.Base64;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class PublicKey {

    private String alias;
    private String fingerprint;
    private String encodedBase64;

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
}

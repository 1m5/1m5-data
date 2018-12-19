package io.onemfive.data.content;

import io.onemfive.data.JSONSerializable;
import io.onemfive.data.util.HashUtil;
import io.onemfive.data.util.JSONParser;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data submitted to the network for dissemination.
 *
 * @author objectorange
 */
public abstract class Content implements JSONSerializable, Serializable {

    // Required
    protected String type;
    private Integer version = 0;
    protected String authorAddress;
    private byte[] body;
    private String bodyEncoding = "UTF-8"; // default
    private Long createdAt;
    private String shortHash;
    private String shortHashAlgorithm = "SHA-1"; // default
    private String fullHash;
    private String fullHashAlgorithm = "SHA-256"; // default
    private Boolean encrypted = false;
    private String encryptionAlgorithm;
    private List<String> keywords = new ArrayList<>();

    public Content() {
        type = getClass().getName();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void advanceVersion() {
        version++;
    }

    public Integer getVersion() {
        return version;
    }

    public String getAuthorAddress() {
        return authorAddress;
    }

    public void setAuthorAddress(String authorAddress) {
        this.authorAddress = authorAddress;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body, boolean generateFullHash, boolean generateShortHash) {
        this.body = body;
        try {
            if(generateFullHash) {
                fullHash = HashUtil.generateHash(new String(body));
            }
            if(generateShortHash) {
                shortHash = HashUtil.generateShortHash(new String(body));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        incrementVersion();
    }

    public String getBodyEncoding() {
        return bodyEncoding;
    }

    public void setBodyEncoding(String bodyEncoding) {
        this.bodyEncoding = bodyEncoding;
    }

    private void incrementVersion() {
        version++;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getShortHash() {
        return shortHash;
    }

    public void setShortHash(String shortHash) {
        this.shortHash = shortHash;
    }

    public String getShortHashAlgorithm() {
        return shortHashAlgorithm;
    }

    public void setShortHashAlgorithm(String shortHashAlgorithm) {
        this.shortHashAlgorithm = shortHashAlgorithm;
    }

    public String getFullHash() {
        return fullHash;
    }

    public void setFullHash(String fullHash) {
        this.fullHash = fullHash;
    }

    public String getFullHashAlgorithm() {
        return fullHashAlgorithm;
    }

    public void setFullHashAlgorithm(String fullHashAlgorithm) {
        this.fullHashAlgorithm = fullHashAlgorithm;
    }

    public boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }

    /**
     * Follows https://en.wikipedia.org/wiki/Magnet_URI_scheme
     *
     * @return Magnet URI
     */
    public String getMagnetLink() {
        StringBuilder m = new StringBuilder();
        if(getBody() != null) {
            m.append("xl=");
            m.append(getBody().length);
        }
        String hash = null;
        String hashAlgorithm = null;
        if(fullHash != null && fullHash.length() < 200) {
            hash = fullHash;
            hashAlgorithm = fullHashAlgorithm;
        } else {
            hash = shortHash;
            hashAlgorithm = shortHashAlgorithm;
        }
        if(hash != null && hashAlgorithm != null) {
            if(body != null) m.append("&");
            m.append("xt=urn:");
            m.append(hashAlgorithm.toLowerCase());
            m.append(":");
            m.append(hash);
        }
        if(keywords != null && keywords.size() > 0) {
            if(hash != null && hashAlgorithm != null) m.append("&");
            m.append("kt=");
            boolean first = true;
            for(String k : keywords) {
                if(first) first = false;
                else m.append("+");
                m.append(k);
            }
        }
        String ml = m.toString();
        if(ml.isEmpty())
            return null;
        else
            return "magnet:?"+ml;
    }

    public Map<String,Object> toMap() {
        Map<String,Object> m = new HashMap<>();
        if(type!=null) m.put("type",type);
//        if(version!=null) m.put("version",version);
        if(body != null) m.put("body", new String(body));
//        if(bodyEncoding != null) m.put("bodyEncoding",bodyEncoding);
//        if(createdAt != null) m.put("createdAt",String.valueOf(createdAt));
        if(shortHash != null) m.put("shortHash", shortHash);
//        if(shortHashAlgorithm != null) m.put("shortHashAlgorithm",shortHashAlgorithm);
        if(fullHash != null) m.put("fullHash", fullHash);
//        if(fullHashAlgorithm != null) m.put("fullHashAlgorithm",fullHashAlgorithm);
//        if(authorAddress != null) m.put("authorAddress", authorAddress);
//        if(encrypted!=null) m.put("encrypted",encrypted);
//        if(encryptionAlgorithm!=null) m.put("encryptionAlgorithm",encryptionAlgorithm);
//        if(keywords != null && keywords.size() > 0) {
//            m.put("keywords", keywords);
//        }
        return m;
    }

    public void fromMap(Map<String,Object> m) {
        if(m.containsKey("type")) type = (String)m.get("type");
//        if(m.containsKey("version")) version = Integer.parseInt((String)m.get("version"));
        if(m.containsKey("body")) body = ((String)m.get("body")).getBytes();
//        if(m.containsKey("bodyEncoding")) bodyEncoding = (String)m.get("bodyEncoding");
//        if(m.containsKey("createdAt")) createdAt = Long.parseLong((String)m.get("createdAt"));
        if(m.containsKey("shortHash")) shortHash = (String)m.get("shortHash");
//        if(m.containsKey("shortHashAlgorithm")) shortHashAlgorithm = (String)m.get("shortHashAlgorithm");
        if(m.containsKey("fullHash")) fullHash = (String)m.get("fullHash");
//        if(m.containsKey("fullHashAlgorithm")) fullHashAlgorithm = (String)m.get("fullHashAlgorithm");
//        if(m.containsKey("authorAddress")) authorAddress = (String)m.get("authorAddress");
//        if(m.containsKey("encrypted")) encrypted = Boolean.parseBoolean((String)m.get("encrypted"));
//        if(m.containsKey("encryptionAlgorithm")) encryptionAlgorithm = (String)m.get("encryptionAlgorithm");
//        if(m.containsKey("keywords")) keywords = (List<String>)m.get("keywords");
    }

    @Override
    public String toString() {
        return JSONParser.toString(toMap());
    }
}

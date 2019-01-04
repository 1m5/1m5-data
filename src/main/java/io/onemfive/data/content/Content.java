package io.onemfive.data.content;

import io.onemfive.data.Hash;
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
    private Hash shortHash;
    private Hash.Algorithm shortHashAlgorithm = Hash.Algorithm.SHA256; // default
    private Hash fullHash;
    private Hash.Algorithm fullHashAlgorithm = Hash.Algorithm.SHA512; // default
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
                fullHash = HashUtil.generateHash(body, fullHashAlgorithm);
            }
            if(generateShortHash) {
                shortHash = HashUtil.generateHash(body, shortHashAlgorithm);
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

    public Hash getShortHash() {
        return shortHash;
    }

    public void setShortHash(Hash shortHash) {
        this.shortHash = shortHash;
    }

    public Hash.Algorithm getShortHashAlgorithm() {
        return shortHashAlgorithm;
    }

    public void setShortHashAlgorithm(Hash.Algorithm shortHashAlgorithm) {
        this.shortHashAlgorithm = shortHashAlgorithm;
    }

    public Hash getFullHash() {
        return fullHash;
    }

    public void setFullHash(Hash fullHash) {
        this.fullHash = fullHash;
    }

    public Hash.Algorithm getFullHashAlgorithm() {
        return fullHashAlgorithm;
    }

    public void setFullHashAlgorithm(Hash.Algorithm fullHashAlgorithm) {
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
            hash = fullHash.getHash();
            hashAlgorithm = fullHashAlgorithm.getName();
        } else {
            hash = shortHash.getHash();
            hashAlgorithm = shortHashAlgorithm.getName();
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
        if(shortHash != null) m.put("shortHash", shortHash.getHash());
        if(shortHashAlgorithm != null) m.put("shortHashAlgorithm",shortHashAlgorithm.getName());
        if(fullHash != null) m.put("fullHash", fullHash);
        if(fullHashAlgorithm != null) m.put("fullHashAlgorithm",fullHashAlgorithm.getName());
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
        if(m.containsKey("shortHashAlgorithm")) shortHashAlgorithm = Hash.Algorithm.valueOf((String)m.get("shortHashAlgorithm"));
        if(m.containsKey("shortHash")) shortHash = new Hash((String)m.get("shortHash"), shortHashAlgorithm);
        if(m.containsKey("fullHashAlgorithm")) fullHashAlgorithm = Hash.Algorithm.valueOf((String)m.get("fullHashAlgorithm"));
        if(m.containsKey("fullHash")) fullHash = new Hash((String)m.get("fullHash"), fullHashAlgorithm);
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

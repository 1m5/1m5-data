package io.onemfive.data.content;

import io.onemfive.data.Addressable;
import io.onemfive.data.DID;
import io.onemfive.data.JSONSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data submitted to the network for dissemination.
 *
 * @author objectorange
 */
public abstract class Content implements Addressable, JSONSerializable, Serializable {

    // Required
    protected String type;
    private Integer version = 0;
    protected DID author;
    private byte[] body;
    private String bodyEncoding;
    private Long createdAt;
    private String hash;
    private String hashAlgorithm;
    private List<String> keywords = new ArrayList<>();

    public Content() {
        type = getClass().getName();
    }

    @Override
    public String getAddress() {
        return hash;
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

    public DID getAuthor() {
        return author;
    }

    public void setAuthor(DID author) {
        this.author = author;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
        hash = null; // will get rebuilt on next getHash()
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
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
        if(getHash() != null && getHashAlgorithm() != null) {
            if(body != null) m.append("&");
            m.append("xt=urn:");
            m.append(getHashAlgorithm().toLowerCase());
            m.append(":");
            m.append(getHash());
        }
        if(keywords != null && keywords.size() > 0) {
            if(getHash() != null && getHashAlgorithm() != null) m.append("&");
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
        if(body != null) m.put("body", new String(body));
        if(bodyEncoding != null) m.put("bodyEncoding",bodyEncoding);
        if(createdAt != null) m.put("createdAt",String.valueOf(createdAt));
        if(hash != null) m.put("hash", hash);
        if(hashAlgorithm != null) m.put("hashAlgorithm",hashAlgorithm);
        if(author != null) {
            m.put("author", author.toMap());
        }
        if(keywords != null && keywords.size() > 0) {
            m.put("keywords", keywords);
        }
        return m;
    }

    public void fromMap(Map<String,Object> m) {
        if(m.containsKey("type")) setType((String)m.get("type"));
        if(m.containsKey("body")) setBody(((String)m.get("body")).getBytes());
        if(m.containsKey("bodyEncoding")) setBodyEncoding((String)m.get("bodyEncoding"));
        if(m.containsKey("createdAt")) setCreatedAt(Long.parseLong((String)m.get("createdAt")));
        if(m.containsKey("hash")) setHash((String)m.get("hash"));
        if(m.containsKey("hashAlgorithm")) setHashAlgorithm((String)m.get("hashAlgorithm"));
        if(m.containsKey("author")) {
            author = new DID();
            author.fromMap((Map<String,Object>)m.get("author"));
        }
        if(m.containsKey("keywords")) keywords = (List<String>)m.get("keywords");
    }
}

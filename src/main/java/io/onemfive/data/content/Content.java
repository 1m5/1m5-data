package io.onemfive.data.content;

import io.onemfive.data.Addressable;
import io.onemfive.data.DID;

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
public class Content implements Addressable, Serializable {

    // Required if not root
    private String parentHash;
    // Required
    private String type;
    private Integer version = 0;
    private DID author;
    private byte[] body;
    private String bodyEncoding;
    private Long createdAt;
    private String hash;
    private String hashAlgorithm;
    private List<String> keywords = new ArrayList<>();

    public Content() {}

    public Content(String hash) {
        this.hash = hash;
    }

    @Override
    public String getAddress() {
        return hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
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
    }

    public String getBodyEncoding() {
        return bodyEncoding;
    }

    public void setBodyEncoding(String bodyEncoding) {
        this.bodyEncoding = bodyEncoding;
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
     * @return
     */
    public String getMagnetLink() {
        StringBuilder m = new StringBuilder();
        if(body != null) {
            m.append("xl=");
            m.append(body.length);
        }
        if(hash != null && hashAlgorithm != null) {
            if(body != null) m.append("&");
            m.append("xt=urn:");
            m.append(hashAlgorithm);
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
        if(body != null) m.put("body", new String(body));
        if(bodyEncoding != null) m.put("bodyEncoding",bodyEncoding);
        if(createdAt != null) m.put("createdAt",String.valueOf(createdAt));
        if(hash != null) m.put("hash", hash);
        if(author != null) {
            m.put("author.alias", author.getAlias());
            m.put("author.encodedKey", author.getEncodedKey());
        }
        return m;
    }

    public static Content fromMap(Map<String,Object> m) {
        if(!m.containsKey("type"))
            return null;
        Content c = null;
        try {
            c = (Content)Class.forName((String)m.get("type")).newInstance();
            c.setType((String)m.get("type"));
        } catch (Exception e) {
            return null;
        }
        if(m.containsKey("parentHash")) c.setParentHash((String)m.get("parentHash"));
        if(m.containsKey("body")) c.setBody(((String)m.get("body")).getBytes());
        if(m.containsKey("bodyEncoding")) c.setBodyEncoding((String)m.get("bodyEncoding"));
        if(m.containsKey("createdAt")) c.setCreatedAt(Long.parseLong((String)m.get("createdAt")));
        if(m.containsKey("hash")) c.setHash((String)m.get("hash"));
        if(m.containsKey("author.alias")) {
            DID did = new DID();
            did.setAlias((String)m.get("author.alias"));
            did.addEncodedKey((String)m.get("author.encodedKey"));
            c.setAuthor(did);
        }
        return c;
    }
}

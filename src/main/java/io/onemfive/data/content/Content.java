package io.onemfive.data.content;

import io.onemfive.data.Addressable;
import io.onemfive.data.DID;
import io.onemfive.data.JSONSerializable;

import java.io.Serializable;
import java.security.MessageDigest;
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
public class Content implements Addressable, JSONSerializable, Serializable {

    // Required if not root
    private Content parent;
    // Required
    private String type;
    private Integer version = 0;
    protected DID author;
    private byte[] body;
    private String bodyEncoding;
    protected List<Content> fragments = new ArrayList<>();
    private Long createdAt;
    private String hash;
    private String hashAlgorithm;
    private List<String> keywords = new ArrayList<>();

    public Content() {}

    @Override
    public String getAddress() {
        return hash;
    }

    public Content getParent() {
        return parent;
    }

    public void setParent(Content parent) {
        this.parent = parent;
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
        if(fragments.size() > 0) {
            StringBuilder b = new StringBuilder();
            if (body != null) {
                b.append(new String(body));
            }
            for (Content f : fragments) {
                b.append(new String(f.getBody()));
            }
            return b.toString().getBytes();
        } else {
            return body;
        }
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

    public void addFragment(Content content) {
        fragments.add(content);
        content.setParent(this);
        hash = null; // will get rebuilt on next getHash()
        incrementVersion();
    }

    private void incrementVersion() {
        version++;
        if(parent != null) {
            parent.incrementVersion();
        }
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getHash() {
        if(hash == null) {
            if(hashAlgorithm == null) hashAlgorithm = "sha512";
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance(hashAlgorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            byte[] b = getBody();
            if(b != null) {
                hash = new String(md.digest(b));
            }
        }
        return hash;
    }

    private void setHash(String hash) {
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
        if(parent!=null) {
            m.put("parentHash",parent.getHash());
            m.put("parentHashAlgorithm",parent.getHashAlgorithm());
        }
        if(type!=null) m.put("type",type);
        if(body != null) m.put("body", new String(body));
        if(bodyEncoding != null) m.put("bodyEncoding",bodyEncoding);
        if(createdAt != null) m.put("createdAt",String.valueOf(createdAt));
        if(hash != null) m.put("hash", hash);
        if(hashAlgorithm != null) m.put("hashAlgorithm",hashAlgorithm);
        if(fragments != null && fragments.size() > 0) {
            StringBuilder sb = new StringBuilder();
            List<Map<String,Object>> fM = new ArrayList<>();
            for(Content f : fragments) {
                fM.add(f.toMap());
            }
            m.put("fragments",fM);
        }
        if(author != null) {
            m.put("author.alias", author.getAlias());
            m.put("author.identityHash", new String(author.getIdentityHash()));
            m.put("author.identityHashAlgorithm", author.getIdentityHashAlgorithm());
        }
        return m;
    }

    public void fromMap(Map<String,Object> m) {
        if(m.containsKey("parentHash")) {
            Content cp = new Content();
            cp.setHash((String)m.get("parentHash"));
            cp.setHashAlgorithm((String)m.get("parentHashAlgorithm"));
            setParent(cp);
        }
        if(m.containsKey("body")) setBody(((String)m.get("body")).getBytes());
        if(m.containsKey("bodyEncoding")) setBodyEncoding((String)m.get("bodyEncoding"));
        if(m.containsKey("createdAt")) setCreatedAt(Long.parseLong((String)m.get("createdAt")));
        if(m.containsKey("hash")) setHash((String)m.get("hash"));
        if(m.containsKey("hashAlgorithm")) setHashAlgorithm((String)m.get("hashAlgorithm"));
        if(m.containsKey("fragments")){
            List<Map<String,Object>> fragments = (List<Map<String,Object>>)m.get("fragments");
            for(Map<String,Object> m2 : fragments){
                String type = (String)m2.get("type");
                if(type!=null){
                    try {
                        Object obj = Class.forName(type).newInstance();
                        Content c = (Content)obj;
                        c.fromMap(m2);
                        addFragment(c);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(m.containsKey("author.alias")) {
            DID did = new DID();
            did.setAlias((String)m.get("author.alias"));
            did.setIdentityHash(((String)m.get("author.identityHash")).getBytes());
            did.setIdentityHashAlgorithm((String)m.get("author.identityHashAlgorithm"));
            setAuthor(did);
        }
    }
}

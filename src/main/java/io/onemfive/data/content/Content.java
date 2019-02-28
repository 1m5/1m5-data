package io.onemfive.data.content;

import io.onemfive.data.Hash;
import io.onemfive.data.JSONSerializable;
import io.onemfive.data.util.HashUtil;
import io.onemfive.data.util.JSONParser;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Data submitted to the network for dissemination.
 *
 * @author objectorange
 */
public abstract class Content implements JSONSerializable, Serializable {

    private Logger LOG = Logger.getLogger(Content.class.getName());

    // Required
    protected String type;
    protected String contentType;
    private Integer version = 0;
    private String name;
    private Long size = 0L;
    protected String authorAddress;
    private byte[] body;
    private String bodyEncoding;
    private Long createdAt;
    private Hash hash;
    private Hash.Algorithm hashAlgorithm = Hash.Algorithm.SHA256; // default
    private Hash fingerprint;
    private Hash.Algorithm fingerprintAlgorithm = Hash.Algorithm.SHA1; // default
    private List<Content> children = new ArrayList<>();
    private Boolean encrypted = false;
    private String encryptionAlgorithm;
    private List<String> keywords = new ArrayList<>();
    // Everyone is given read access (e.g. article)
    private Boolean readable = false;
    // Everyone is given write access (e.g. wiki)
    private Boolean writeable = false;

    public static Content buildContent(byte[] body, String contentType) {
        return buildContent(body, contentType, null, false, false);
    }

    public static Content buildContent(byte[] body, String contentType, String name) {
        return buildContent(body, contentType, name, false, false);
    }

    public static Content buildContent(byte[] body, String contentType, String name, boolean generateHash, boolean generateFingerprint) {
        Content c = null;
        if(contentType==null) return null;
        else if(contentType.startsWith("text/plain")) c = new Text(body, contentType, name, generateHash, generateFingerprint);
        else if(contentType.startsWith("text/html")) c = new HTML(body, contentType, name, generateHash, generateFingerprint);
        else if(contentType.startsWith("image/")) c = new Image(body, contentType, name, generateHash, generateFingerprint);
        else if(contentType.startsWith("audio/")) c = new Audio(body, contentType, name, generateHash, generateFingerprint);
        else if(contentType.startsWith("video/")) c = new Video(body, contentType, name, generateHash, generateFingerprint);
        else if(contentType.startsWith("application/json"))  c = new JSON(body, contentType, name, generateHash, generateFingerprint);
        return c;
    }

    public Content() {
        type = getClass().getName();
    }

    public Content(byte[] body, String contentType) {
        this(body, contentType, null, true, true);
    }

    public Content(byte[] body, String contentType, String name, boolean generateHash, boolean generateFingerprint) {
        type = getClass().getName();
        setBody(body, generateHash, generateFingerprint);
        this.size = (long)body.length;
        this.contentType = contentType;
        this.name = name;
        if(contentType!=null && contentType.contains("charset:")) {
            bodyEncoding = contentType.substring(contentType.indexOf("charset:")+1);
        }
        String msg = "Content Instantiated : {";
        msg += "\n\tName: "+name;
        msg += "\n\tType: "+type;
        msg += "\n\tContent Type: " + contentType;
        if (this instanceof Text)
            msg += "\n\tBody: " + new String(body).substring(0, 80) + "...";
        msg += "\n\tBody Encoding: " + bodyEncoding;
        msg += "\n\tSize: " + size;
        if(generateFingerprint)
            msg += "\n\tFingerprint: " + fingerprint.getHash();
        if(generateHash)
            msg += "\n\tHash: " + hash.getHash().substring(0, 80) + "...";
        LOG.info(msg+"\n}");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void advanceVersion() {
        version++;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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

    public void setBody(byte[] body, boolean generateHash, boolean generateFingerprint) {
        this.body = body;
        try {
            if(generateHash) {
                hash = HashUtil.generateHash(body, hashAlgorithm);
            }
            if(generateFingerprint && hash != null) {
                fingerprint = HashUtil.generateFingerprint(hash.getHash().getBytes(), fingerprintAlgorithm);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        incrementVersion();
    }

    public String base64EncodeBody() {
        if(body==null) return null;
        String encoded = null;
        try {
            encoded = Base64.getEncoder().encodeToString(body);
        } catch (Exception e) {
            LOG.warning(e.getLocalizedMessage());
        }
        return encoded;
    }

    public byte[] base64DecodeBody(String body) {
        if(body==null) return null;
        byte[] decoded = null;
        try {
            decoded = Base64.getDecoder().decode(body);
        } catch (Exception e) {
            LOG.warning(e.getLocalizedMessage());
        }
        return decoded;
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

    public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public Hash.Algorithm getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(Hash.Algorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public boolean addChild(Content content) {
        return children.add(content);
    }

    public boolean removeChild(Content content) {
        return children.remove(content);
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

    public boolean readable() {
        return readable;
    }

    public void readable(boolean readable) {
        this.readable = readable;
    }

    public boolean writeable() {
        return writeable;
    }

    public void writeable(boolean writeable) {
        this.writeable = writeable;
    }

    public boolean metaOnly() {
        return body == null;
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
        if(hash != null && hashAlgorithm != null) {
            if(body != null) m.append("&");
            m.append("xt=urn:");
            m.append(hashAlgorithm.getName().toLowerCase());
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
        if(contentType!=null) m.put("contentType",contentType);
        if(version!=null) m.put("version",String.valueOf(version));
        if(name!=null) m.put("name",name);
        if(size!=null) m.put("size",String.valueOf(size));
        if(body != null) {
            if(this instanceof Binary)
                m.put("body", base64EncodeBody());
            else
                m.put("body", new String(body));
        }
        if(bodyEncoding != null) m.put("bodyEncoding",bodyEncoding);
        if(createdAt != null) m.put("createdAt",String.valueOf(createdAt));
        if(hash != null) m.put("hash", hash.getHash());
        if(hashAlgorithm != null) m.put("hashAlgorithm",hashAlgorithm.getName());
        if(children != null && children.size() > 0) {
            List<Map<String,Object>> l = new ArrayList<>();
            for(Content c : children) {
                l.add(c.toMap());
            }
            m.put("children", l);
        }
        if(authorAddress != null) m.put("authorAddress", authorAddress);
        if(encrypted!=null) m.put("encrypted",encrypted.toString());
        if(encryptionAlgorithm!=null) m.put("encryptionAlgorithm",encryptionAlgorithm);
        if(keywords != null && keywords.size() > 0) m.put("keywords", keywords);
        if(readable!=null) m.put("readable",readable.toString());
        if(writeable!=null) m.put("writeable",writeable.toString());
        return m;
    }

    public void fromMap(Map<String,Object> m) {
        if(m.get("type")!=null) type = (String)m.get("type");
        if(m.get("contentType")!=null) contentType = (String)m.get("contentType");
        if(m.get("version")!=null) version = Integer.parseInt((String)m.get("version"));
        if(m.get("name")!=null) name = (String)m.get("name");
        if(m.get("size")!=null) size = Long.parseLong((String)m.get("size"));
        if(m.get("body")!=null) {
            if(this instanceof Binary)
                body = base64DecodeBody((String)m.get("body"));
            else
                body = ((String)m.get("body")).getBytes();
        }
        if(m.get("bodyEncoding")!=null) bodyEncoding = (String)m.get("bodyEncoding");
        if(m.get("createdAt")!=null) createdAt = Long.parseLong((String)m.get("createdAt"));
        if(m.get("hashAlgorithm")!=null) hashAlgorithm = Hash.Algorithm.value((String)m.get("hashAlgorithm"));
        if(m.get("hash")!=null) hash = new Hash((String)m.get("hash"), hashAlgorithm);
        if(m.get("children")!=null) {
            List<Map<String,Object>> l = (List<Map<String,Object>>)m.get("children");
            Content c;
            for(Map<String,Object> mc : l) {
                try {
                    c = (Content)Class.forName((String)mc.get("type")).newInstance();
                    c.fromMap(mc);
                    children.add(c);
                } catch (Exception e) {
                    LOG.warning(e.getMessage());
                }
            }
        }
        if(m.get("authorAddress")!=null) authorAddress = (String)m.get("authorAddress");
        if(m.get("encrypted")!=null) encrypted = Boolean.parseBoolean((String)m.get("encrypted"));
        if(m.get("encryptionAlgorithm")!=null) encryptionAlgorithm = (String)m.get("encryptionAlgorithm");
        if(m.get("keywords")!=null) keywords = (List<String>)m.get("keywords");
        if(m.get("readable")!=null) readable = Boolean.parseBoolean((String)m.get("readable"));
        if(m.get("writeable")!=null) writeable = Boolean.parseBoolean((String)m.get("writeable"));
    }

    @Override
    public String toString() {
        return JSONParser.toString(toMap());
    }

}

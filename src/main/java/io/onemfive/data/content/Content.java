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
    private Hash shortHash;
    private Hash.Algorithm shortHashAlgorithm = Hash.Algorithm.SHA256; // default
    private Hash fullHash;
    private Hash.Algorithm fullHashAlgorithm = Hash.Algorithm.SHA512; // default
    private Hash fingerprint;
    private Hash.Algorithm fingerprintHashAlgorithm = Hash.Algorithm.SHA1; // default
    private List<Content> children = new ArrayList<>();
    private Boolean encrypted = false;
    private String encryptionAlgorithm;
    private List<String> keywords = new ArrayList<>();
    // Everyone is given read access (e.g. article)
    private Boolean readable = false;
    // Everyone is given write access (e.g. wiki)
    private Boolean writeable = false;

    public static Content buildContent(byte[] body, String contentType) {
        return buildContent(body, contentType, null, false, false, false);
    }

    public static Content buildContent(byte[] body, String contentType, String name) {
        return buildContent(body, contentType, name, false, false, false);
    }

    public static Content buildContent(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        Content c = null;
        if(contentType==null) return null;
        else if(contentType.startsWith("text/plain")) c = new Text(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        else if(contentType.startsWith("text/html")) c = new HTML(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        else if(contentType.startsWith("image/")) c = new Image(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        else if(contentType.startsWith("audio/")) c = new Audio(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        else if(contentType.startsWith("video/")) c = new Video(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        else if(contentType.startsWith("application/json"))  c = new JSON(body, contentType, name, generateFullHash, generateShortHash, generateFingerprint);
        return c;
    }

    public Content() {
        type = getClass().getName();
    }

    public Content(byte[] body, String contentType) {
        this(body, contentType, null, true, true, true);
    }

    public Content(byte[] body, String contentType, String name, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        type = getClass().getName();
        setBody(body, generateFullHash, generateShortHash, generateFingerprint);
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
            msg += "\n\tBody: " + new String(body).substring(0, 20) + "...";
        msg += "\n\tBody Encoding: " + bodyEncoding;
        msg += "\n\tSize: " + size;
        if(generateFingerprint)
            msg += "\n\tFingerprint: " + fingerprint.toHexString();
        if(generateShortHash)
            msg += "\n\tShort Hash: " + shortHash.getHash().substring(0, 20) + "...";
        if(generateFullHash)
            msg += "\n\tFull Hash: " + fullHash.getHash().substring(0, 20) + "...";
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

    public void setBody(byte[] body, boolean generateFullHash, boolean generateShortHash, boolean generateFingerprint) {
        this.body = body;
        try {
            if(generateFullHash) {
                fullHash = HashUtil.generateHash(body, fullHashAlgorithm);
            }
            if(generateShortHash) {
                shortHash = HashUtil.generateHash(body, shortHashAlgorithm);
            }
            if(generateFingerprint && fullHash != null) {
                fingerprint = HashUtil.generateHash(fullHash.getHash(), fingerprintHashAlgorithm);
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
        if(shortHash != null) m.put("shortHash", shortHash.getHash());
        if(shortHashAlgorithm != null) m.put("shortHashAlgorithm",shortHashAlgorithm.getName());
        if(fullHash != null) m.put("fullHash", fullHash.getHash());
        if(fullHashAlgorithm != null) m.put("fullHashAlgorithm",fullHashAlgorithm.getName());
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
        if(m.get("shortHashAlgorithm")!=null) shortHashAlgorithm = Hash.Algorithm.value((String)m.get("shortHashAlgorithm"));
        if(m.get("shortHash")!=null) shortHash = new Hash((String)m.get("shortHash"), shortHashAlgorithm);
        if(m.get("fullHashAlgorithm")!=null) fullHashAlgorithm = Hash.Algorithm.value((String)m.get("fullHashAlgorithm"));
        if(m.get("fullHash")!=null) fullHash = new Hash((String)m.get("fullHash"), fullHashAlgorithm);
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

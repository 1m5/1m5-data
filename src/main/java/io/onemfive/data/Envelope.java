package io.onemfive.data;

import io.onemfive.data.util.Multipart;
import io.onemfive.data.util.RandomUtil;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * Wraps all data passed around in application to ensure a space for header type information.
 *
 * @author objectorange
 */
public final class Envelope implements Persistable, Serializable {

    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_JSON = "application/json";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}
    public enum Action{ADD,UPDATE,REMOVE,VIEW}

    private Long id;
    private Boolean external = false;
    private DynamicRoutingSlip dynamicRoutingSlip;
    private Route route = null;
    private DID did = new DID();
    private Long client = 0L;
    private Boolean replyToClient = false;
    private String clientReplyAction = null;
    private URL url = null;
    private Multipart multipart = null;

    private Action action = null;
    private String commandPath = null;

    private Map<String, Object> headers;
    private Message message;
    private Sensitivity sensitivity = Sensitivity.VERYHIGH; // Default to I2P

    public enum Sensitivity { // with default sensors chosen
        NONE, // HTTP
        LOW, // HTTPS
        MEDIUM, // Tor
        HIGH, // I2P
        VERYHIGH, // I2P Delayed
        EXTREME, // Direct Mesh
        NEO // Intelligent Combination of Tor, I2P, I2P Delayed, and Direct Mesh
    }

    public static Envelope commandFactory() {
        return new Envelope(RandomUtil.nextRandomLong(), new CommandMessage());
    }

    public static Envelope documentFactory() {
        return new Envelope(RandomUtil.nextRandomLong(), new DocumentMessage());
    }

    public static Envelope documentFactory(Long id) {
        return new Envelope(id, new DocumentMessage());
    }

    public static Envelope headersOnlyFactory() {
        return new Envelope(RandomUtil.nextRandomLong(), null);
    }

    public static Envelope eventFactory(EventMessage.Type type) {
        return new Envelope(RandomUtil.nextRandomLong(), new EventMessage(type.name()));
    }

    public static Envelope textFactory() {
        return new Envelope(RandomUtil.nextRandomLong(), new TextMessage());
    }

    public static Envelope envelopeFactory(Envelope envelope){
        Envelope e = new Envelope(envelope.getId(), envelope.getHeaders(), envelope.getMessage(), envelope.getDynamicRoutingSlip());
        e.setExternal(envelope.getExternal());
        e.setClient(envelope.getClient());
        e.setClientReplyAction(envelope.getClientReplyAction());
        e.setDID(envelope.getDID());
        e.setReplyToClient(envelope.replyToClient());
        e.setRoute(envelope.getRoute());
        e.setURL(envelope.getURL());
        e.setAction(envelope.getAction());
        e.setCommandPath(envelope.getCommandPath());
        e.setContentType(envelope.getContentType());
        e.setMultipart(envelope.getMultipart());
        e.setMessage(envelope.getMessage());
        e.setSensitivity(envelope.getSensitivity());
        return e;
    }

    public Envelope(Long id, Message message) {
        this(id, message, new HashMap<String, Object>());
    }

    public Envelope(Long id, Message message, Map<String,Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
        this.dynamicRoutingSlip = new DynamicRoutingSlip();
    }

    private Envelope(Long id, Map<String,Object> headers, Message message, DynamicRoutingSlip dynamicRoutingSlip) {
        this(id, message, headers);
        this.dynamicRoutingSlip = dynamicRoutingSlip;
    }

    public Long getId() {
        return id;
    }

    public Boolean getExternal() {
        return external;
    }

    public void setExternal(Boolean external) {
        this.external = external;
    }

    public void setHeader(String name, Object value) {
        headers.put(name, value);
    }

    public boolean headerExists(String name) {
        return headers.containsKey(name);
    }

    public void removeHeader(String name) {
        headers.remove(name);
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    private void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public DynamicRoutingSlip getDynamicRoutingSlip() {
        return dynamicRoutingSlip;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public DID getDID() {
        return did;
    }

    public void setDID(DID did) {
        this.did = did;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Boolean replyToClient() {
        return replyToClient;
    }

    public void setReplyToClient(Boolean replyToClient) {
        this.replyToClient = replyToClient;
    }

    public String getClientReplyAction() {
        return clientReplyAction;
    }

    public void setClientReplyAction(String clientReplyAction) {
        this.clientReplyAction = clientReplyAction;
    }

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public Multipart getMultipart() {
        return multipart;
    }

    public void setMultipart(Multipart multipart) {
        this.multipart = multipart;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getCommandPath() {
        return commandPath;
    }

    public void setCommandPath(String commandPath) {
        this.commandPath = commandPath;
    }

    public String getContentType() {
        return (String)headers.get(HEADER_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        headers.put(HEADER_CONTENT_TYPE, contentType);
    }

    public Sensitivity getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }
}

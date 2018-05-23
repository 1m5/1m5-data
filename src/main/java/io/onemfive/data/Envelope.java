package io.onemfive.data;

import io.onemfive.data.util.Multipart;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Wraps all data passed around in application to ensure a space for header type information.
 *
 * @author objectorange
 */
public final class Envelope implements Persistable, Serializable {

    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_USER_AGENT = "User-Agent";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}
    public enum Action{ADD,UPDATE,REMOVE,VIEW}

    private Long id;
    private DynamicDirectedRouteGraph graph;
    private Route route = null;
    private DID did = null;
    private Long client = 0L;
    private Boolean replyToClient = false;
    private String clientReplyAction = null;
    private URL url = null;
    private Multipart multipart = null;

    private Action action = null;
    private String contentType = null;

    private Map<String, Object> headers;
    private Message message;

    public static Envelope documentFactory() {
        return new Envelope(new Random(9743129723981731L).nextLong(), new DocumentMessage());
    }

    public static Envelope documentFactory(Long id) {
        return new Envelope(id, new DocumentMessage());
    }

    public static Envelope messageFactory(MessageType type) {
        return messageFactory(new Random(94878279281414L).nextLong(), type);
    }

    public static Envelope messageFactory(Long id, MessageType type) {
        switch (type) {
            case COMMAND: return new Envelope(id, new CommandMessage());
            case DOCUMENT: return new Envelope(id, new DocumentMessage());
            case TEXT: return new Envelope(id, new TextMessage());
            case EVENT: return new Envelope(id, new EventMessage());
            default: return new Envelope(id, null);
        }
    }

    public static Envelope envelopeFactory(Envelope envelope){
        Envelope e = new Envelope(envelope.getId(), envelope.getHeaders(), envelope.getMessage(), envelope.getDRG());
        e.setClient(envelope.getClient());
        e.setClientReplyAction(envelope.getClientReplyAction());
        e.setDID(envelope.getDID());
        e.setReplyToClient(envelope.replyToClient());
        e.setRoute(envelope.getRoute());
        e.setURL(envelope.getURL());
        e.setAction(envelope.getAction());
        e.setContentType(envelope.getContentType());
        e.setMultipart(envelope.getMultipart());
        return e;
    }

    public Envelope(Long id, Message message) {
        this(id, message, new HashMap<String, Object>());
    }

    public Envelope(Long id, Message message, Map<String,Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
        this.graph = new DynamicDirectedRouteGraph();
    }

    private Envelope(Long id, Map<String,Object> headers, Message message, DirectedRouteGraph graph) {
        this(id, message, headers);
        this.graph = (DynamicDirectedRouteGraph)graph;
    }

    public Long getId() {
        return id;
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

    public Message getMessage() {
        return message;
    }

    public DirectedRouteGraph getDRG() {
        return graph;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

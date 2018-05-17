package io.onemfive.data;

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
public class Envelope implements Persistable, Serializable {

    public static final String DRG = "DirectedRouteGraph";
    public static final String ROUTE = "ROUTE";
    public static final String DID = "DID";

    public static final String REPLY = "REPLY";
    public static final String NONE = "NONE";

    public static final String URL = "URL";

    public static final String CLIENT = "CLIENT";
    public static final String CLIENT_REPLY = "CLIENT_REPLY";
    public static final String CLIENT_REPLY_ACTION = "CLIENT_REPLY_ACTION";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}

    private Long id;
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
        Envelope e = new Envelope(envelope.getId(), envelope.getMessage());
        e.setHeaders(envelope.getHeaders());
        return e;
    }

    public Envelope(Long id, Message message) {
        this(id, message, new HashMap<String, Object>());
    }

    public Envelope(Long id, Message message, Map<String,Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
        this.headers.put(Envelope.DRG, new DynamicDirectedRouteGraph());
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

    // Helpers
    public DirectedRouteGraph getDRG() {
        return (DirectedRouteGraph)headers.get(Envelope.DRG);
    }

    public Route getRoute() {
        return (Route)headers.get(ROUTE);
    }

    public void setRoute(Route route) {
        headers.put(ROUTE, route);
    }

    public DID getDID() {
        return (DID)headers.get(DID);
    }

    public void setDID(DID did) {
        headers.put(DID, did);
    }

    public Long getClient() {
        return (Long)headers.get(CLIENT);
    }

    public void setClient(Long client) {
        headers.put(CLIENT,client);
    }

    public Boolean replyToClient() {
        return headers.get(CLIENT_REPLY) != null;
    }

    public void setReplyToClient(Boolean replyToClient) {
        headers.put(CLIENT_REPLY, replyToClient);
    }

    public URL getURL() {
        return (URL)headers.get(URL);
    }

    public void setURL(URL url) {
        headers.put(URL,url);
    }
}

package io.onemfive.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Wraps all data passed around in application to ensure a space for header type information.
 *
 * @author objectorange
 */
public class Envelope implements Persistable, Serializable {

    public static final String ROUTE = "ROUTE";
    public static final String DID = "DID";

    public static final String REPLY = "REPLY";
    public static final String NONE = "NONE";

    public static final String CLIENT = "CLIENT";
    public static final String CLIENT_REPLY = "CLIENT_REPLY";
    public static final String CLIENT_REPLY_ACTION = "CLIENT_REPLY_ACTION";

    public static final String DATA_TYPE = "DATA_TYPE";
    public static final String DATA_IS_LIST = "DATA_IS_LIST";

    public enum MessageType {DOCUMENT, TEXT, EVENT, COMMAND, NONE}

    private long id;
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
        Envelope newEnvelope = new Envelope(envelope.getId(), envelope.getMessage());
        newEnvelope.setHeaders(envelope.getHeaders());
        return newEnvelope;
    }

    public Envelope(long id, Message message) {
        this(id, message, new HashMap<String, Object>());
    }

    public Envelope(long id, Message message, Map<String,Object> headers) {
        this.id = id;
        this.message = message;
        this.headers = headers;
    }

    public long getId() {
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
}

package io.onemfive.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public final class DocumentMessage extends BaseMessage {

    public List<Map<String,Object>> data;

    public DocumentMessage() {
        data = new ArrayList<>();
        data.add(new HashMap<String, Object>());
    }
}

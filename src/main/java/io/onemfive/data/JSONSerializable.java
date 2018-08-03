package io.onemfive.data;

import java.util.Map;

public interface JSONSerializable {
    Map<String,Object> toMap();
    void fromMap(Map<String,Object> m);
}

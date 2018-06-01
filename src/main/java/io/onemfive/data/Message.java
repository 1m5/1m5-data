package io.onemfive.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Brian on 3/27/18.
 */
public interface Message extends Serializable {
    void addErrorMessage(String errorMessage);
    List<String> getErrorMessages();
}

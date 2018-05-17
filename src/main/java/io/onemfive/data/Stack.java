package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Stack<T> extends Serializable {
    void push(T object);
    T pop();
    T peek();
    int numberRemainingRoutes();
}

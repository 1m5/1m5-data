package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Stack<T> {
    void push(T object);
    T pop();
    T peek();
    int numberRemainingRoutes();
}

package com.viviant.earngold.api;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

    void onFailure(String message);

}

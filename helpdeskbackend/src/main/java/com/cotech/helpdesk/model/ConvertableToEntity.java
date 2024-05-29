package com.cotech.helpdesk.model;

public interface ConvertableToEntity<T, K> {

    public K findEntityById(T id);
}

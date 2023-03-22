package com.bitmotel.lanshuxiao.content.services;

public interface EditI<T> {
    public Object create(T data);
    public Object delete(T data);
    public Object update(T data);
    public Object create(T data, Integer user_id);
}

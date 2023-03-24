package com.bitmotel.lanshuxiao.content.services;

public interface EditableI<T> {
    public Object create(T data);
    public Object create(T data, Integer user_id);
    public Object delete(T data);
    public Object update(T data);
    public Object query(Integer id);
    public Object queryByObject(T data);
    public Object queryByObject(T data, Integer offset, Integer limit);
    public Object queryByUserId(Integer user_id);
    public Object queryByObjectByUserId(T data, Integer user_id);
    public Object queryByObjectByUserId(T data, Integer user_id, Integer offset, Integer limit);
}

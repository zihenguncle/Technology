package com.example.component.mvp.persenter;

import java.util.Map;

public interface IPersenter {
    void startRequestGet(String url,Class clazz);
    void startRequestDelete(String url,Class clazz);
    void startRequestPost(String url, Map<String,String> map,Class clazz);
    void startRequestPut(String url,Map<String,String> map,Class clazz);

}

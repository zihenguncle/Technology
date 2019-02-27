package com.example.component.mvp.model;

import com.example.component.mvp.callback.ICallBack;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;

public interface IModel {
    void requestDataGet(String url, Class clazz, ICallBack iCallBack);
    void requestDataDelete(String url,Class clazz,ICallBack iCallBack);
    void requestDataPost(String url, Map<String,String> map,Class clazz,ICallBack iCallBack);
    void requestDataPut(String url,Map<String,String> map,Class clazz,ICallBack iCallBack);
    void requestDataFile(String url,Map<String,String> map,Class clazz,ICallBack iCallBack);
    void requestDataMoreFile(String url, Map<String,String> map, List<File> list,Class clazz,ICallBack iCallBack);
}

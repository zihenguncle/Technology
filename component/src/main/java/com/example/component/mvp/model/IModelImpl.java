package com.example.component.mvp.model;

import com.example.component.mvp.callback.ICallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, Class clazz, ICallBack iCallBack) {

    }

    @Override
    public void requestDataDelete(String url, Class clazz, ICallBack iCallBack) {

    }

    @Override
    public void requestDataPost(String url, Map<String, String> map, Class clazz, ICallBack iCallBack) {

    }

    @Override
    public void requestDataPut(String url, Map<String, String> map, Class clazz, ICallBack iCallBack) {

    }

    @Override
    public void requestDataMoreFile(String url, Map<String, String> map, List<File> list, ICallBack iCallBack) {

    }
}

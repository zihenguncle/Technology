package com.example.component.mvp.model;

import com.example.component.mvp.callback.ICallBack;
import com.example.component.retrofit.RetrofitUtils;
import com.google.gson.Gson;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().get(url, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDataDelete(String url, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().delete(url, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDataPost(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().post(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDataPut(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().put(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDataFile(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().postFile(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestDataMoreFile(String url, Map<String, String> map, List<File> list, final Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().postMoreFile(url, map, list, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.getData(o);
                    }
                }

                @Override
                public void getError(String error) {
                    if(iCallBack != null){
                        iCallBack.getError(error);
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}

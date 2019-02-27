package com.example.component.mvp.model;

import com.example.component.mvp.callback.ICallBack;
import com.example.component.retrofit.RetrofitUtils;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().get(url, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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
    public void requestDataDelete(String url, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().delete(url, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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
    public void requestDataPost(String url, Map<String, String> map, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().post(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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
    public void requestDataPut(String url, Map<String, String> map, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().put(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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
    public void requestDataFile(String url, Map<String, String> map, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().postFile(url, map, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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
    public void requestDataMoreFile(String url, Map<String, String> map, List<File> list, Class clazz, final ICallBack iCallBack) {
        try {
            RetrofitUtils.getInstance().postMoreFile(url, map, list, new RetrofitUtils.HttpCallBack() {
                @Override
                public void getData(String data) {
                    if(iCallBack != null){
                        iCallBack.getData(data);
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

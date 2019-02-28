package com.example.component.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.example.component.GetContent;
import com.example.component.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private OkHttpClient mClient;
    private static final String BASE_URL = "https://172.17.8.100/techApi/";

    public static RetrofitUtils instance;
    private final BaseApis baseApis;

    //懒汉式单例
    public static RetrofitUtils getInstance() throws NoSuchAlgorithmException, KeyManagementException {
        if(instance == null){
            synchronized (RetrofitUtils.class){
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }

    private RetrofitUtils() throws KeyManagementException, NoSuchAlgorithmException {

        SSLContext sc = SSLContext.getInstance("TLS");
        //信任证书管理,这个是由我们自己生成的,信任我们自己的服务器证书
        TrustManager tm = new MyTrustManager(MyTrustManager.readCert(MyTrustManager.readRaw(GetContent.getContent())));
        sc.init(null, new TrustManager[]{
                tm
        }, null);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .sslSocketFactory(sc.getSocketFactory(), (X509TrustManager) tm)
                .hostnameVerifier(hostnameVerifier)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        //取出登陆时获得的两个id
                        SpUtils spUtils = new SpUtils("User",GetContent.getContent());
                        String userId = (String) spUtils.getString("userId", "");
                        String sessionId = (String) spUtils.getString( "sessionId", "");

                        //重写构造请求
                        Request.Builder builder = request.newBuilder();
                        //把原来请求的数据原样放进去
                        builder.method(request.method(),request.body());

                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder.addHeader("userId",userId);
                            builder.addHeader("sessionId",sessionId);
                            builder.addHeader("ak","0110010010000");
                            builder.addHeader("Content-Type","application/x-www-form-urlencoded");
                        }

                        //打包
                        Request request1 = builder.build();

                        return chain.proceed(request1);
                    }
                })

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .build();
        baseApis = retrofit.create(BaseApis.class);

    }


    //主机地址验证
    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("172.17.8.100");
        }
    };



    //get请求
    public void get(String url, HttpCallBack callBack) {
        baseApis.get(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }




    //post请求
    public void post(String url,Map<String,String> map,HttpCallBack listener){
        if(map == null){
            map = new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }



    //delete请求
    public void delete(String url, HttpCallBack callBack) {
        baseApis.delete(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }



    //put请求
    public void put(String url, Map<String, String> params, HttpCallBack callBack) {
        if (params == null) {
            params = new HashMap<>();
        }
        baseApis.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }

    //单图上传
    public void postFile(String url, Map<String, String> map,HttpCallBack listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        MultipartBody multipartBody = filesToMultipartBody(map);

        baseApis.postFile(url, multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public static MultipartBody filesToMultipartBody(Map<String,String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), "图片1.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    //多图上传
    public void postMoreFile(String url, Map<String,String> params, List<File> list, HttpCallBack listener){
        MultipartBody.Part[] parts=new MultipartBody.Part[list.size()];
        int index=0;
        for (File file: list){
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part filePart=MultipartBody.Part.createFormData("image",file.getName(),requestBody);
            parts[index]=filePart;
            index++;
        }

        baseApis.postMoreFile(url,params,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }



    private Observer getObserver(final HttpCallBack callBack){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(callBack != null){
                    callBack.getError(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if(callBack != null){
                        callBack.getData(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(callBack != null){
                        callBack.getError(e.getMessage());
                    }
                }
            }


        };
        return observer;
    }

    public interface HttpCallBack{
        void getData(String data);
        void getError(String error);
    }


}

package com.example.component.retrofit;

import android.text.TextUtils;

import com.example.component.GetContent;
import com.example.component.R;
import com.example.component.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
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
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private OkHttpClient mClient;
  //  private static final String BASE_URL = "https://172.17.8.100/techApi/";
    private static final String BASE_URL = "https://mobile.bwstudent.com/techApi/";


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

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        mClient = new OkHttpClient.Builder()
                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .sslSocketFactory(sc.getSocketFactory(), (X509TrustManager) tm)
                .hostnameVerifier(hostnameVerifier)
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        //取出登陆时获得的两个id
                        String userId = (String) SpUtils.getString("userId", "");
                        String sessionId = (String) SpUtils.getString( "sessionId", "");

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
                })*/

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .baseUrl(BASE_URL)
                .build();
        baseApis = retrofit.create(BaseApis.class);

    }
    /*
     * 默认信任所有的证书
     * */
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }

    public static X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {

            tm =   new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意客户端证书
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意服务端证书
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            InputStream trustStream = GetContent.getContent().getResources().openRawResource(R.raw.server);
            testReadX509CerFile(trustStream);
        } catch (Exception e) {

        }
        return tm;
    }
    //主机地址验证
    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("mobile.bwstudent.com");
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
    /***
     * 读取*.cer公钥证书文件， 获取公钥证书信息
     * @author xgh
     */
    public static  void testReadX509CerFile(InputStream inStream) throws Exception{

        try {
            // 读取证书文件

            // 创建X509工厂类
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // 创建证书对象
            X509Certificate oCert = (X509Certificate) cf
                    .generateCertificate(inStream);
            inStream.close();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
            String info = null;
            // 获得证书版本
            info = String.valueOf(oCert.getVersion());
            System.out.println("证书版本:" + info);
            // 获得证书序列号
            info = oCert.getSerialNumber().toString(16);
            System.out.println("证书序列号:" + info);
            // 获得证书有效期
            Date beforedate = oCert.getNotBefore();
            info = dateformat.format(beforedate);
            System.out.println("证书生效日期:" + info);
            Date afterdate = oCert.getNotAfter();
            info = dateformat.format(afterdate);
            System.out.println("证书失效日期:" + info);
            // 获得证书主体信息
            info = oCert.getSubjectDN().getName();
            System.out.println("证书拥有者:" + info);
            // 获得证书颁发者信息
            info = oCert.getIssuerDN().getName();
            System.out.println("证书颁发者:" + info);
            // 获得证书签名算法名称
            info = oCert.getSigAlgName();
            System.out.println("证书签名算法:" + info);

        } catch (Exception e) {
            System.out.println("解析证书出错！");
            e.printStackTrace();
        }
    }

}

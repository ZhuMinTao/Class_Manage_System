package cn.source.new_class_system.base.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Session {

    private CloseableHttpClient httpClients = null;

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager  = null;

    /** @PropertyDescription 用于匹配带有cookie的字符串 **/
    private final static String COOKIE_REGULAR = "(?i).*cookie.*";

    public Session(){

        if(httpClients == null)
            init();
    }

    /**
    * @Date 2023/1/18 16:39
    * @MethodDescription 初始化操作
    */
    public  void init(){

        if(poolingHttpClientConnectionManager==null){
            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(10);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
        }
        httpClients = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();

    }


    /**
    * @Date 2023/1/18 17:27
    * @MethodDescription 发出get请求
    * @Param 1. String
    */
    public Map<String,String> get(String url) throws IOException {

        HttpGet httpGet = new HttpGet(url);


        CloseableHttpResponse execute = httpClients.execute(httpGet);


        return backContent(execute);

    }
    
    /**
    * @Date 2023/1/18 20:25
    * @MethodDescription Post以application/x-www-form-urlencoded提交数据
    * @Param 1. String 2.Map
    */
    public Map<String,String> postFormUrlEncoded(String url,Map<String,String> date) throws IOException {

        //创建一个post请求
        HttpPost httpPost = new HttpPost(url);

        //用于保存参数
        List<BasicNameValuePair> pairList = null;

        if(date!=null){
            pairList = new ArrayList<>();

            //将参数设置到list中
            for(Map.Entry<String,String> entry : date.entrySet()){
                pairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
        }

        CloseableHttpResponse execute = httpClients.execute(httpPost);


        return backContent(execute);

    }

    /**
    * @Date 2023/1/18 17:43
    * @MethodDescription Post以form_date提交数据
    * @Param 1.
    */
    public Map<String,String> postForm(String url,Map<String,String> date) throws IOException {

        //创建一个post请求
        HttpPost httpPost = new HttpPost(url);


        if(date!=null){
            //创建一个MultipartEntityBuilder用于放置数据
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));

            //将所以的参数设置到MultipartEntityBuilder
            for(Map.Entry<String, String> entry: date.entrySet()) {
                builder.addTextBody(entry.getKey(),entry.getValue(),strContent);
            }

            //将MultipartEntityBuilder设置到请求体中
            httpPost.setEntity(builder.build());
        }

        CloseableHttpResponse execute = httpClients.execute(httpPost);


        return backContent(execute);
    }





    /**
    * @Date 2023/1/18 17:28
    * @MethodDescription 最后返回内容
    * @Param 1.CloseableHttpResponse
    */
    public Map<String,String> backContent(CloseableHttpResponse closeableHttpResponse) throws IOException {

        Map<String,String> content  = new HashMap();

        //保存状态码
        content.put("code",String.valueOf(closeableHttpResponse.getStatusLine().getStatusCode()));

        HttpEntity entity = closeableHttpResponse.getEntity();
        try {
            //保存响应的数据
            content.put("text",EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Arrays.stream(closeableHttpResponse.getAllHeaders()).forEach(e->{
            //保存请求头
            content.put(e.getName(),e.getValue());
        });

        content.put("Cookie",getCookie(closeableHttpResponse.getAllHeaders()));

        return content;
        
    }


    /**
     * @Date 2023/1/18 17:26
     * @MethodDescription 得到Cookie字符串
     * @Param 1. Header[]
     */
    private String getCookie(Header[] allHeaders){
        String cookie = "";
        for (Header header :allHeaders){
            boolean matches = header.getName().matches(COOKIE_REGULAR);
            if(matches){
                cookie+=header.getValue().split(";")[0]+";";
            }
        }
        return cookie;
    }
}

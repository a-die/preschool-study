package com.adie.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "XE8ktZYN-gNb8MYC6RQ1JrWNEz5YJnwXF1YvbQWm";
    private static final String SECRET_KEY = "aws5p98QgU4C7awaTLoz3Ok2eJUgsLPKqJl41tCg";

    // 要上传的空间
    private static final String bucketname = "bw27du5";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    private static final String DOMAIN = "http://r6rnbeqiv.hn-bkt.clouddn.com";

//    private static final String style = "\timageView2/0/q/75";

    public String getUpToken() {        
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }

    //base64方式上传
    public String put64image(byte[] base64, String key) throws Exception{
        String file64 = Base64.encodeToString(base64, 0);
        Integer l = base64.length;
        String url = "http://upload-z2.qiniup.com/putb64/" + l + "/key/"+ UrlSafeBase64.encodeToString(key);
        //http://r3zvcgduw.hn-bkt.clouddn.com/image-20211013014051293.png
        //http://r3zvcgduw.hn-bkt.clouddn.com/e996e671-c65f-4f57-abc0-a3d3ab0d9ab1
        //r3zvcgduw.hn-bkt.clouddn.come996e671-c65f-4f57-abc0-a3d3ab0d9ab
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //如果不需要添加图片样式，使用以下方式
        //return DOMAIN + key;
        return DOMAIN + "/"+key;
    }
}
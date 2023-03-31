package cn.source.new_class_system.base.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.utils.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class OSSUtils {

    // endpoint
    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // accessKey
    private String accessKeyId = "LTAI5tAViPvPWcKu7FTjdLiC";
    private String accessKeySecret = "ON8s3IIyPhWeI1dhRYoJID7pijEZWo";
    // 空间
    private String bucketName = "class-system";
    // 文件存储目录
    private String filedir = "";

    private OSSClient ossClient;

    private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);

    public OSSUtils(){
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public void destroy() {
        ossClient.shutdown();
    }





    /**
     * 上传图片,通过文件路径上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url) throws Exception {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new FileUploadException("图片上传失败");
        }
    }

    private void uploadFile2OSS(FileInputStream fin, String s) {
    }

    public String uploadImg2Oss(MultipartFile file) throws Exception {
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new FileUploadException("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new FileUploadException("图片上传失败");
        }
    }


    /**
     * 获得图片路径,通过网络路径上传文件
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        System.out.println("fileuel"+fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }


    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        System.out.println("key"+key);
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }



    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream
     *            文件流
     * @param fileName
     *            文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);

            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     *            文件后缀
     * @return String
     */
    public static String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }


    public byte[] getObjectByte(String url) throws Exception {
        String fileKey = url.substring(url.lastIndexOf('/')+1);

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, fileKey);
        InputStream in = ossObject.getObjectContent();
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
        IOUtils.copyLarge(in, out);
        in.close();
        out.close();
        return out.toByteArray();
    }



}

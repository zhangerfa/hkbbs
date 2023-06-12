package site.zhangerfa.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 图床工具类
 */
@Component
public class ImgShackUtil {
    private static Logger logger = LoggerFactory.getLogger(ImgShackUtil.class);

    @Resource
    private HostHolder hostHolder;
    @Value("${cos.secretId}")
    private String secretId;
    @Value("${cos.secretKey}")
    private String secretKey;
    @Value("${cos.region}")
    private String cosRegion;
    @Value("${cos.bucket}")
    private String bucketName;
    @Value("${cos.path}")
    private String path;

    /**
     * 将图片上传到图床并将URL集合返回
     * @param images
     * @return
     */
    public List<String> getImageUrls(List<MultipartFile> images){
        // 将传入图片上传到图床
        List<String> imageUrl = new ArrayList<>();
        for (MultipartFile image : images) {
            imageUrl.add(add(image));
        }
        return imageUrl;
    }

    /**
     * 以UUID作为图片名上传到图床并返回访问url
     * @param image
     * @return
     */
    public String add(MultipartFile image){
        // 生成唯一标识符作为文件名
        String uuidFilename = UUID.randomUUID().toString() + getSuffix(image);
        return add(image, uuidFilename);
    }

    /**
     * 将传入图片添加到图床中，返回其访问路径
     * @param image
     * @param imageName
     * @return
     */
    public String add(MultipartFile image, String imageName){
        // 将图片文件上传到图床
        // 创建cos客户端
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 上传文件
        try {
            User user = hostHolder.getUser();
            String suffix = getSuffix(image);
            if (suffix == null || suffix.equals("")){
                logger.error("图片上传图床：文件格式不正确");
                return null;
            }
            File file = File.createTempFile(imageName, suffix);
            image.transferTo(file);
            String key = path + imageName + suffix;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // 更新当前用户的头像访问路径
            return "https://" + bucketName + ".cos." + cosRegion + ".myqcloud.com/" + key;
        } catch (IOException e) {
            logger.error("头像上传失败" + e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }

    public String getSuffix(MultipartFile image){
        // 修改图片文件名：学号_原文件名.原后缀名
        String originalFilename = image.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}

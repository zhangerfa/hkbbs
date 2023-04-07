package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Image;

import java.util.List;

public interface ImageService {
    boolean add(Image image);

    /**
     * 获取帖子的所有图片：id，url
     *
     * @param
     * @return
     */
    List<String> getImagesForEntity(int entityType, int entityId);

    boolean deleteImageById(int id);

    Image getImageById(int id);

    /**
     * 删除帖子所有图片，返回删除图片的个数
     * @param
     * @return
     */
    Result<Integer> deleteImagesForEntity(int entityType, int entityId);
}

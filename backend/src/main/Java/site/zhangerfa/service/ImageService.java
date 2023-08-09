package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.entity.Image;

import java.util.List;

public interface ImageService {
    boolean add(Image image);

    /**
     * 获取帖子的所有图片：id，url
     *
     * @param
     * @return
     */
    List<String> getImagesForEntity(Entity entity);

    boolean deleteImageById(int id);

    Image getImageById(int id);

    /**
     * 删除帖子所有图片，返回删除图片的个数
     * @param
     * @return
     */
    Result<Integer> deleteImagesForEntity(Entity entity);
}

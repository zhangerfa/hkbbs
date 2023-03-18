package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Image;

import java.util.List;

public interface ImageService {
    boolean add(Image image);

    /**
     * 获取帖子的所有图片：id，url
     *
     * @param postId
     * @return
     */
    List<Image> getImagesForPost(int postId);

    boolean deleteImageById(int id);

    Image getImageById(int id);

    /**
     * 删除帖子所有图片，返回删除图片的个数
     * @param postId
     * @return
     */
    Result<Integer> deleteImagesForPost(int postId);
}

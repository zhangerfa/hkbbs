package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.ImageMapper;
import site.zhangerfa.pojo.Image;
import site.zhangerfa.service.ImageService;

import java.util.ArrayList;
import java.util.List;

public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageMapper imageMapper;

    @Override
    public boolean add(Image image) {
        return imageMapper.addImage(image) > 0;
    }

    @Override
    public List<Image> getImagesForPost(int postId) {
        List<Image> imageForPost = imageMapper.getImageForPost(postId);
        if (imageForPost == null) return new ArrayList<>();
        else return imageForPost;
    }

    @Override
    public boolean deleteImageById(int id) {
        return imageMapper.deleteImageById(id) > 0;
    }

    @Override
    public Image getImageById(int id) {
        return imageMapper.getImageById(id);
    }

    @Override
    public Result<Integer> deleteImagesForPost(int postId) {
        return new Result<>(Code.DELETE_OK, imageMapper.deleteImageForPost(postId));
    }
}

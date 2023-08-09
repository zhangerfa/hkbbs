package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.ImageMapper;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.service.ImageService;
import site.zhangerfa.entity.Image;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageMapper imageMapper;

    @Override
    public boolean add(Image image) {
        return imageMapper.insert(image) > 0;
    }

    @Override
    public List<String> getImagesForEntity(Entity entity) {
        int entityType = entity.getEntityType();
        int entityId = entity.getEntityId();
        return imageMapper.selectList(new LambdaQueryWrapper<Image>()
                .eq(Image::getEntityType, entityType).
                eq(Image::getEntityId, entityId)).stream().map(Image::getUrl).toList();
    }

    @Override
    public boolean deleteImageById(int id) {
        return imageMapper.deleteById(new Image(id)) > 0;
    }

    @Override
    public Image getImageById(int id) {
        return imageMapper.selectById(id);
    }

    @Override
    public Result<Integer> deleteImagesForEntity(Entity entity) {
        return new Result<>(Code.DELETE_OK,
                imageMapper.delete(new LambdaQueryWrapper<Image>()
                        .eq(Image::getEntityType, entity.getEntityType()).
                        eq(Image::getEntityId, entity.getEntityId())));
    }
}

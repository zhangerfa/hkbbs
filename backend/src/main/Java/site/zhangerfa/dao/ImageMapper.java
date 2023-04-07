package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import site.zhangerfa.pojo.Image;

import java.util.List;

@Mapper
public interface ImageMapper {
    /**
     * 向指定帖子中添加图片
     *
     * @param image
     * @return
     */
    @Insert("insert into image(entity_type, entity_id, url) values(#{entityType} ,#{entityId}, #{url})")
    int addImage(Image image);

    @Delete("delete from image where id = #{id}")
    int deleteImageById(int id);

    /**
     * 删除实体对向所有图片
     * @param
     * @return
     */
    @Delete("delete from image where entity_type = #{entityType} and entity_id = #{entityId}")
    int deleteImageForEntity(int entityType, int entityId);

    /**
     * 获取帖子所有图片
     * @param
     * @return
     */
    @Select("select url from image where entity_type = #{entityType} and entity_id = #{entityId}")
    List<String> getImageForEntity(int entityType, int entityId);

    @Select("select * from image where id = #{id}")
    Image getImageById(int id);
}

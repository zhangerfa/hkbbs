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
    @Insert("insert into image(post_id, url) values(#{postId}, #{url})")
    int addImage(Image image);

    @Delete("delete from image where id = #{id}")
    int deleteImageById(int id);

    /**
     * 删除帖子所有图片
     * @param postId
     * @return
     */
    @Delete("delete from image where post_id = #{postId}")
    int deleteImageForPost(int postId);

    /**
     * 获取帖子所有图片
     * @param postId
     * @return
     */
    @Select("select * from image where post_id = #{postId}")
    List<Image> getImageForPost(int postId);

    @Select("select * from image where id = #{id}")
    Image getImageById(int id);
}

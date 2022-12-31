package dao;

import org.apache.ibatis.annotations.*;
import pojo.User;

@Mapper
public interface UserMapper {
    @Select("select stu_id as stuId, username, password, create_at as createTime " +
            "from user where stu_id = #{stuId}")
    public User selectByStuId(String stuId);
    
    @Insert("insert into user(stu_id, username, password) " +
            "values(#{stuId}, #{username}, #{password})")
    public int add(User user);

    @Update("update user set username = #{username} where stu_id = #{stuId}")
    public int updateUsername(@Param("stuId") String stuId, @Param("username") String username);

    @Update("update user set password = #{password} where stu_id = #{stuId}")
    public int updatePassword(@Param("stuId") String stuId, @Param("password") String password);

    @Delete("delete from user where stu_id = #{stuId}")
    public int delete(String stuId);
}

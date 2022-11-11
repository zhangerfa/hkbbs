package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import pojo.User;

public interface UserMapper {
    @Select("select stu_id as stuId, username, password from user where stu_id = #{id}")
    public User selectUserById(String id);
    
    @Insert("insert into user(stu_id, username, password) " +
            "values(#{stuId}, #{username}, #{password})")
    public void addUser(User user);
}

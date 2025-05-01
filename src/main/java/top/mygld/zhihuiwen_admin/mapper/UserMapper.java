package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.*;
import top.mygld.zhihuiwen_admin.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    // 新增用户
    int insertUser(User user);

    // 根据ID删除用户
    int deleteUser(Long id);

    // 更新用户信息
    int updateUser(User user);

    // 根据ID查询用户
    User selectUserById(Long id);

    // 根据用户名模糊搜索用户（支持分页）
    List<User> selectUsersByUsername(@Param("username") String username);
}

package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.User;

public interface UserService {

    // 新增用户
    int addUser(User user);

    // 删除用户
    int deleteUser(Long id);

    // 更新用户
    int updateUser(User user);

    // 根据ID查询用户
    User getUserById(Long id);

    // 分页查询用户（支持用户名模糊搜索）
    PageInfo<User> getUsers(String username, int pageNum, int pageSize);
}

package top.mygld.zhihuiwen_admin.service;

import top.mygld.zhihuiwen_admin.pojo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    // 新增管理员
    int addAdmin(Admin admin);

    // 删除管理员
    int deleteAdmin(Long id);

    // 更新管理员
    int updateAdmin(Admin admin);

    // 根据id查询管理员
    Admin getAdminById(Long id);

    // 分页查询所有管理员
    PageInfo<Admin> getAllAdmins(int pageNum, int pageSize);

    // 管理员登录
    Admin login(String name, String password);

    List<Admin> selectAdminByName(String name);
}

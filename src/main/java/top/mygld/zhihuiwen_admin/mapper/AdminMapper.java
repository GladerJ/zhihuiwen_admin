package top.mygld.zhihuiwen_admin.mapper;

import top.mygld.zhihuiwen_admin.pojo.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 新增管理员
    int insertAdmin(Admin admin);

    // 根据id删除管理员
    int deleteAdmin(Long id);

    // 更新管理员信息
    int updateAdmin(Admin admin);

    // 根据id查询管理员
    Admin selectById(Long id);

    // 查询所有管理员
    List<Admin> selectAll();

    // 管理员登录
    Admin login(@Param("name") String name, @Param("password") String password);

    // 根据管理员名称精确查找
    List<Admin> selectAdminByName(String name);

    // 根据管理员名称模糊搜索（支持分页）
    List<Admin> selectAdminsByNameLike(@Param("name") String name);
}
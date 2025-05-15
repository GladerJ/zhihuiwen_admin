package top.mygld.zhihuiwen_admin.service.impl;

import top.mygld.zhihuiwen_admin.mapper.AdminMapper;
import top.mygld.zhihuiwen_admin.pojo.Admin;
import top.mygld.zhihuiwen_admin.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.insertAdmin(admin);
    }

    @Override
    public int deleteAdmin(Long id) {
        return adminMapper.deleteAdmin(id);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminMapper.selectById(id);
    }

    @Override
    public PageInfo<Admin> getAdmins(String name, int pageNum, int pageSize) {
        // 使用 PageHelper 进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectAdminsByNameLike(name);
        return new PageInfo<>(admins);
    }

    @Override
    public Admin login(String name, String password) {
        return adminMapper.login(name, password);
    }

    @Override
    public List<Admin> selectAdminByName(String name) {
        return adminMapper.selectAdminByName(name);
    }
}
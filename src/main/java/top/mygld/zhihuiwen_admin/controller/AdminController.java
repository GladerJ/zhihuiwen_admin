package top.mygld.zhihuiwen_admin.controller;

import top.mygld.zhihuiwen_admin.pojo.Admin;
import top.mygld.zhihuiwen_admin.service.AdminService;
import top.mygld.zhihuiwen_admin.utils.JWTUtil;
import top.mygld.zhihuiwen_admin.common.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 新增管理员
    @PostMapping("/add")
    public Result<String> addAdmin(@RequestBody Admin admin) {
        List<Admin> admins = adminService.selectAdminByName(admin.getName());
        if(admins.size() != 0) return Result.error("管理员用户名已存在!");
        int result = adminService.addAdmin(admin);
        return result > 0 ? Result.success("新增管理员成功") : Result.error("新增管理员失败");
    }

    // 删除管理员
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteAdmin(@PathVariable("id") Long id) {
        int result = adminService.deleteAdmin(id);
        return result > 0 ? Result.success("删除管理员成功") : Result.error("删除管理员失败");
    }

    // 更新管理员
    @PutMapping("/update")
    public Result<String> updateAdmin(@RequestBody Admin admin) {
        Admin existingAdmin = adminService.getAdminById(admin.getId());
        if (existingAdmin == null) {
            return Result.error("管理员不存在!");
        }

        if (!existingAdmin.getName().equals(admin.getName())) {
            List<Admin> admins = adminService.selectAdminByName(admin.getName());
            if (!admins.isEmpty()) return Result.error("管理员用户名已存在!");
        }

        int result = adminService.updateAdmin(admin);
        return result > 0 ? Result.success("更新管理员成功") : Result.error("更新管理员失败");
    }


    // 根据id查询管理员
    @GetMapping("/{id}")
    public Result<Admin> getAdminById(@PathVariable("id") Long id) {
        Admin admin = adminService.getAdminById(id);
        return admin != null ? Result.success(admin) : Result.error("管理员不存在");
    }

    // 分页查询所有管理员
    @GetMapping("/list")
    public Result<PageInfo<Admin>> getAllAdmins(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Admin> pageInfo = adminService.getAllAdmins(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    // 管理员登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody Admin admin) {
        Admin loginAdmin = adminService.login(admin.getName(), admin.getPassword());
        if (loginAdmin != null) {
            // 生成 token，此处 token 内容为用户的 id
            String token = JWTUtil.generateToken(loginAdmin.getId());
            return Result.success(token);
        } else {
            return Result.error("登录失败，用户名或密码错误");
        }
    }
}

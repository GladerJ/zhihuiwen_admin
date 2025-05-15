package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Category;
import top.mygld.zhihuiwen_admin.pojo.User;
import top.mygld.zhihuiwen_admin.service.CategoryService;
import top.mygld.zhihuiwen_admin.service.QuestionnaireService;
import top.mygld.zhihuiwen_admin.service.TemplateService;
import top.mygld.zhihuiwen_admin.service.TotalReportService;
import top.mygld.zhihuiwen_admin.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TotalReportService totalReportService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private QuestionnaireService questionnaireService;

    // 新增用户
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(user.getUsername())) {
            return Result.error("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userService.existsByEmail(user.getEmail())) {
            return Result.error("邮箱已存在");
        }

        if(user.getAvatar() == null || !user.getAvatar().contains("http")){
            user.setAvatar("https://b0.bdstatic.com/0df6c8c7f109aa7b67e7cb15e6f8d025.jpg@h_1280");
        }
        int result = userService.addUser(user);
        return result > 0 ? Result.success("新增用户成功") : Result.error("新增用户失败");
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        // 获取用户信息
        User user = userService.getUserById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 1. 删除用户所有的分类及关联数据
        List<Category> categories = categoryService.getCategoriesByUserId(id);
        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                // CategoryService.deleteCategory 方法会级联删除分类下的问卷、模板、报告等
                categoryService.deleteCategory(category);
            }
        }

        // 2. 删除用户的个人总结报告
        totalReportService.deleteTotalReportByUserId(id);

        // 3. 删除用户
        int result = userService.deleteUser(id);

        return result > 0 ? Result.success("删除用户成功") : Result.error("删除用户失败");
    }

    // 更新用户
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        // 获取当前用户信息
        User existingUser = userService.getUserById(user.getId());
        if (existingUser == null) {
            return Result.error("用户不存在");
        }

        // 如果修改了用户名，检查新用户名是否已存在
        if (user.getUsername() != null && !user.getUsername().equals(existingUser.getUsername())) {
            if (userService.existsByUsername(user.getUsername())) {
                return Result.error("用户名已存在");
            }
        }

        // 如果修改了邮箱，检查新邮箱是否已存在
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (userService.existsByEmail(user.getEmail())) {
                return Result.error("邮箱已存在");
            }
        }

        if(user.getAvatar() == null || !user.getAvatar().contains("http")){
            user.setAvatar("https://b0.bdstatic.com/0df6c8c7f109aa7b67e7cb15e6f8d025.jpg@h_1280");
        }

        int result = userService.updateUser(user);
        return result > 0 ? Result.success("更新用户成功") : Result.error("更新用户失败");
    }

    // 根据ID查询用户
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }

    // 分页查询用户（支持用户名模糊搜索）
    @GetMapping("/list")
    public Result<PageInfo<User>> getUsers(@RequestParam(required = false) String username,
                                           @RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<User> pageInfo = userService.getUsers(username, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.User;
import top.mygld.zhihuiwen_admin.service.TotalReportService;
import top.mygld.zhihuiwen_admin.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TotalReportService totalReportService;

    // 新增用户
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody User user) {
        int result = userService.addUser(user);
        return result > 0 ? Result.success("新增用户成功") : Result.error("新增用户失败");
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        int result = userService.deleteUser(id);
        totalReportService.deleteTotalReportByUserId(id);
        return result > 0 ? Result.success("删除用户成功") : Result.error("删除用户失败");
    }

    // 更新用户
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
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

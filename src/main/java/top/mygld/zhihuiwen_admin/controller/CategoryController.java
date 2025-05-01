package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Category;
import top.mygld.zhihuiwen_admin.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     */
    @PostMapping("/add")
    public Result<String> addCategory(@RequestBody Category category) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        category.setUserId(userId);
        categoryService.addCategory(category);
        return Result.success("分类新增成功");
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCategory(@PathVariable("id") Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = new Category();
        category.setId(id);
        category.setUserId(userId);
        categoryService.deleteCategory(category);
        return Result.success("分类删除成功");
    }

    /**
     * 更新分类
     */
    @PutMapping("/update")
    public Result<String> updateCategory(@RequestBody Category category) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        category.setUserId(userId);
        categoryService.updateCategory(category);
        return Result.success("分类更新成功");
    }

    /**
     * 根据ID查询分类详情
     */
    @GetMapping("/{id}")
    public Result<Category> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return Result.error("分类不存在");
        }
        return Result.success(category);
    }

    /**
     * 分页查询当前用户所有分类
     */
    @GetMapping("/list")
    public Result<PageInfo<Category>> listCategories(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Category> pageInfo = categoryService.getCategories(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 模糊搜索分类（按名称）
     */
    @GetMapping("/search")
    public Result<PageInfo<Category>> searchCategories(
            @RequestParam String content,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Category> pageInfo = categoryService.searchCategoriesByName(content, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}

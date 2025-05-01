package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Category;

public interface CategoryService {

    // 新增分类
    void addCategory(Category category);

    // 删除分类
    void deleteCategory(Category category);

    // 更新分类
    void updateCategory(Category category);

    // 根据ID查询分类
    Category getCategoryById(Long id);

    // 分页查询指定用户的所有分类
    PageInfo<Category> getCategories(int pageNum, int pageSize);

    PageInfo<Category> searchCategoriesByName(String content, int pageNum, int pageSize);
}

package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    void deleteCategory(Category category);

    void updateCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getCategoriesByUserId(Long userId);

    PageInfo<Category> getCategories(int pageNum, int pageSize);

    PageInfo<Category> searchCategoriesByName(String content, int pageNum, int pageSize);
}
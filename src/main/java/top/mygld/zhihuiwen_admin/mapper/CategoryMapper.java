package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.mygld.zhihuiwen_admin.pojo.Category;
import java.util.List;

@Mapper
public interface CategoryMapper {

    // 新增分类
    int addCategory(Category category);

    // 删除分类
    int deleteCategory(Category category);

    // 更新分类
    int updateCategory(Category category);

    // 根据ID查询分类
    Category selectCategoryById(Long id);

    // 根据用户ID查询该用户所有分类（支持分页，由 Service 层调用 PageHelper）
    List<Category> selectCategories();
    List<Category> selectCategoriesByUserId(Long userId);

    List<Category> selectCategoriesByNameLike(String content);
}

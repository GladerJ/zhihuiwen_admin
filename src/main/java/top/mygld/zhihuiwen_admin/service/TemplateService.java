package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Template;

public interface TemplateService {
    // 新增模板（级联插入题目与选项）
    void insertTemplate(Template template);

    // 更新模板（先级联删除旧题目和选项，再重新插入新的题目和选项）
    void updateTemplate(Template template);

    // 删除模板（级联删除题目和选项）
    void deleteTemplate(Template template);

    // 根据模板ID和当前用户查询模板详情（包含题目和选项）
    Template selectTemplateByIdAndUserId(Long id, Long userId);

    // 根据模板ID查询模板详情（供公开查询使用）
    Template selectTemplateById(Long id);

    // 分页查询当前用户在指定分类下的所有模板
    PageInfo<Template> listTemplatesByCategory(int pageNum, int pageSize);

    // 模糊搜索当前用户在指定分类下的模板（按标题）
    PageInfo<Template> searchTemplates(Long categoryId, String title, int pageNum, int pageSize);

    // 公开模板：分页查询所有已发布的模板
    PageInfo<Template> listAllPublicTemplates(int pageNum, int pageSize);

    // 公开模板：模糊搜索（按标题）已发布的模板
    PageInfo<Template> searchPublicTemplates(String content, int pageNum, int pageSize);

    // 根据模板ID查询公开模板详情
    Template selectPublicTemplateById(Long id);

    // 增加模板的使用次数
    void addUsageCount(Long templateId);
}

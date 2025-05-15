package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mygld.zhihuiwen_admin.mapper.CategoryMapper;
import top.mygld.zhihuiwen_admin.mapper.QuestionnaireMapper;
import top.mygld.zhihuiwen_admin.mapper.ReportMapper;
import top.mygld.zhihuiwen_admin.mapper.TemplateMapper;
import top.mygld.zhihuiwen_admin.pojo.Category;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;
import top.mygld.zhihuiwen_admin.pojo.Template;
import top.mygld.zhihuiwen_admin.service.CategoryService;
import top.mygld.zhihuiwen_admin.service.QuestionnaireService;
import top.mygld.zhihuiwen_admin.service.ReportService;
import top.mygld.zhihuiwen_admin.service.TemplateService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ReportService reportService;

    @Override
    public void addCategory(Category category) {
        if (category.getCreatedAt() == null) category.setCreatedAt(new Date());
        if (category.getUpdatedAt() == null) category.setUpdatedAt(new Date());
        categoryMapper.addCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        // 级联删除该分类下所有问卷
        List<Questionnaire> questionnaires = questionnaireMapper.selectAllById(category.getUserId(), category.getId());
        for (Questionnaire questionnaire : questionnaires) {
            // 删除该问卷的报告
            reportService.deleteReportByQuestionnaireId(questionnaire.getId());

            // 删除问卷级联删除其题目和选项
            questionnaireService.deleteQuestionnaire(questionnaire.getId());
        }

        List<Template> templates = templateMapper.selectAllById(category.getId());
        for (Template template : templates) {
            // 删除模板
            templateService.deleteTemplate(template);
        }
        categoryMapper.deleteCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        if (category.getUpdatedAt() == null) category.setUpdatedAt(new Date());
        categoryMapper.updateCategory(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryMapper.selectCategoriesByUserId(userId);
    }

    @Override
    public PageInfo<Category> getCategories(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.selectCategories();
        return new PageInfo<>(categories);
    }

    @Override
    public PageInfo<Category> searchCategoriesByName(String content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectCategoriesByNameLike(content);
        return new PageInfo<>(list);
    }
}
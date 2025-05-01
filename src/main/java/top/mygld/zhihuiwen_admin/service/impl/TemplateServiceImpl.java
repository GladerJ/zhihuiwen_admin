package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mygld.zhihuiwen_admin.mapper.TemplateMapper;
import top.mygld.zhihuiwen_admin.pojo.Template;
import top.mygld.zhihuiwen_admin.pojo.TemplateOption;
import top.mygld.zhihuiwen_admin.pojo.TemplateQuestion;
import top.mygld.zhihuiwen_admin.service.TemplateService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public void insertTemplate(Template template) {
        Date now = new Date();
        template.setCreatedAt(now);
        template.setUpdatedAt(now);
        templateMapper.insertTemplate(template);
        // 插入题目和选项
        if (template.getQuestions() != null) {
            for (TemplateQuestion question : template.getQuestions()) {
                question.setTemplateId(template.getId());
                templateMapper.insertTemplateQuestion(question);
                if (question.getOptions() != null) {
                    for (TemplateOption option : question.getOptions()) {
                        option.setQuestionId(question.getId());
                        templateMapper.insertTemplateOption(option);
                    }
                }
            }
        }
    }

    @Override
    public void updateTemplate(Template template) {
        template.setUpdatedAt(new Date());
        // 级联删除旧的题目和选项
        templateMapper.deleteOptionsByTemplateId(template.getId());
        templateMapper.deleteQuestionsByTemplateId(template.getId());
        // 更新模板主记录
        templateMapper.updateTemplate(template);
        // 重新插入新的题目和选项
        if (template.getQuestions() != null) {
            for (TemplateQuestion question : template.getQuestions()) {
                question.setTemplateId(template.getId());
                templateMapper.insertTemplateQuestion(question);
                if (question.getOptions() != null) {
                    for (TemplateOption option : question.getOptions()) {
                        option.setQuestionId(question.getId());
                        templateMapper.insertTemplateOption(option);
                    }
                }
            }
        }
    }

    @Override
    public void deleteTemplate(Template template) {
        // 级联删除：先删除选项，再删除题目，最后删除模板
        templateMapper.deleteOptionsByTemplateId(template.getId());
        templateMapper.deleteQuestionsByTemplateId(template.getId());
        templateMapper.deleteTemplate(template.getId(), template.getUserId());
    }

    @Override
    public Template selectTemplateByIdAndUserId(Long id, Long userId) {
        return templateMapper.selectTemplateByIdAndUserId(id, userId);
    }

    @Override
    public Template selectTemplateById(Long id) {
        return templateMapper.selectTemplateById(id);
    }

    @Override
    public PageInfo<Template> listTemplatesByCategory(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Template> list = templateMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Template> searchTemplates(Long categoryId, String title, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Template> list = templateMapper.selectTemplateLike(categoryId, title);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Template> listAllPublicTemplates(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Template> list = templateMapper.selectAllPublicTemplates();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Template> searchPublicTemplates(String content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Template> list = templateMapper.selectAllPublicTemplatesLike(content);
        return new PageInfo<>(list);
    }

    @Override
    public Template selectPublicTemplateById(Long id) {
        return templateMapper.selectPublicTemplateById(id);
    }

    @Override
    public void addUsageCount(Long templateId) {
        templateMapper.addUsageCount(templateId);
    }
}

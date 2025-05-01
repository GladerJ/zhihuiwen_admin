package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mygld.zhihuiwen_admin.pojo.*;

import java.util.List;

@Mapper
public interface TemplateMapper {
    int insertTemplate(Template template);
    int insertTemplateQuestion(TemplateQuestion question);
    int insertTemplateOption(TemplateOption option);
    int deleteTemplate(@Param("id") Long id, @Param("userId") Long userId);
    int deleteQuestionsByTemplateId(@Param("templateId") Long templateId);
    int deleteOptionsByTemplateId(@Param("templateId") Long templateId);
    int updateTemplate(Template template);
    Template selectTemplateByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
    List<Template> selectAllById(@Param("categoryId") Long categoryId);
    List<Template> selectTemplateLike(@Param("categoryId") Long categoryId,
                                                @Param("title") String title);
    Template selectTemplateById(@Param("id") Long id);

    int addUsageCount(@Param("templateId") Long templateId);

    List<Template> selectAllPublicTemplates();
    List<Template> selectAllPublicTemplatesLike(String content);
    Template selectPublicTemplateById(@Param("id") Long id);

    List<Template> selectAllTemplatesByUserId(@Param("userId") Long userId);

    List<Template> selectAll();
}

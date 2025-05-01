package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Template;
import top.mygld.zhihuiwen_admin.service.TemplateService;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 公开模板：根据模板ID查询模板详情（包含题目和选项）
     * 前端无需 token
     */
    @GetMapping("/public/{id}")
    public Result<Template> getPublicTemplate(@PathVariable Long id) {
        Template template = templateService.selectPublicTemplateById(id);
        return template != null ? Result.success(template) : Result.error("模板不存在");
    }

    /**
     * 公开模板：分页查询所有已发布模板
     */
    @GetMapping("/public/list")
    public Result<PageInfo<Template>> listPublicTemplates(@RequestParam(defaultValue = "1") int pageNum,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(templateService.listAllPublicTemplates(pageNum, pageSize));
    }

    /**
     * 公开模板：模糊搜索已发布模板（按标题）
     */
    @GetMapping("/public/search")
    public Result<PageInfo<Template>> searchPublicTemplates(@RequestParam String content,
                                                            @RequestParam(defaultValue = "1") int pageNum,
                                                            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(templateService.searchPublicTemplates(content, pageNum, pageSize));
    }

    /**
     * 删除模板（级联删除题目和选项）
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteTemplate(@PathVariable Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Template template = new Template();
        template.setId(id);
        templateService.deleteTemplate(template);
        return Result.success("删除成功");
    }

    /**
     * 分页查询当前用户在指定分类下的所有模板
     */
    @GetMapping("/list")
    public Result<PageInfo<Template>> listTemplates(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Template> pageInfo = templateService.listTemplatesByCategory(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 模糊搜索当前用户在指定分类下的模板（按标题）
     */
    @GetMapping("/search")
    public Result<PageInfo<Template>> searchTemplates(@RequestParam Long categoryId,
                                                      @RequestParam String title,
                                                      @RequestParam(defaultValue = "1") int pageNum,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Template> pageInfo = templateService.searchTemplates(categoryId, title, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 根据模板ID查询当前用户模板详情（包含题目和选项）
     */
    @GetMapping("/detail/{id}")
    public Result<Template> getTemplateDetail(@PathVariable Long id) {
        Template template = templateService.selectTemplateById(id);
        return template != null ? Result.success(template) : Result.error("模板不存在");
    }

    /**
     * 增加模板使用次数
     */
    @PostMapping("/addUsageCount")
    public Result<String> addUsageCount(@RequestParam Long templateId) {
        templateService.addUsageCount(templateId);
        return Result.success("使用次数增加成功");
    }
}

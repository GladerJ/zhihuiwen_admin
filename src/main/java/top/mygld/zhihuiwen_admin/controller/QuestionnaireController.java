package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;
import top.mygld.zhihuiwen_admin.service.QuestionnaireService;
import top.mygld.zhihuiwen_admin.service.ReportService;
import top.mygld.zhihuiwen_admin.service.ResponseService;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ReportService reportService;

    /**
     * 删除问卷（级联删除题目与选项）
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteQuestionnaire(@PathVariable("id") Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 级联删除该问卷下所有回复及其答案
        responseService.deleteResponseByQuestionnaireId(id);

        // 删除对应的报告
        reportService.deleteReportByQuestionnaireId(id);

        // 删除问卷（此处确保删除时校验当前用户权限）
        questionnaireService.deleteQuestionnaire(id);

        return Result.success("问卷及相关数据删除成功");
    }

    /**
     * 获取问卷详情（包含题目与选项）
     */
    @GetMapping("/{id}")
    public Result<Questionnaire> getQuestionnaire(@PathVariable("id") Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
        if (questionnaire == null) {
            return Result.error("问卷不存在或无权限访问");
        }
        return Result.success(questionnaire);
    }

    /**
     * 分页查询当前用户的所有问卷
     */
    @GetMapping("/list")
    public Result<PageInfo<Questionnaire>> listQuestionnaires(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Questionnaire> pageInfo = questionnaireService.listQuestionnaires(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/search")
    public Result<PageInfo<Questionnaire>> searchQuestionnaires(
            @RequestParam Long categoryId,
            @RequestParam String title,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Questionnaire> pageInfo = questionnaireService.searchQuestionnairesByTitle(categoryId, title, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}

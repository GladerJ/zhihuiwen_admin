package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Report;
import top.mygld.zhihuiwen_admin.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 新增报告
     */
    @PostMapping("/add")
    public Result<String> addReport(@RequestBody Report report) {
        int result = reportService.insertReport(report);
        return result > 0 ? Result.success("报告新增成功") : Result.error("新增失败");
    }

    /**
     * 更新报告
     */
    @PutMapping("/update")
    public Result<String> updateReport(@RequestBody Report report) {
        int result = reportService.updateReport(report);
        return result > 0 ? Result.success("报告更新成功") : Result.error("更新失败");
    }

    /**
     * 根据报告ID查询报告详情
     */
    @GetMapping("/{id}")
    public Result<Report> getReportById(@PathVariable("id") Long id) {
        Report report = reportService.selectReportById(id);
        return report != null ? Result.success(report) : Result.error("报告不存在");
    }

    /**
     * 根据问卷ID查询报告详情
     */
    @GetMapping("/questionnaire/{questionnaireId}")
    public Result<Report> getReportByQuestionnaireId(@PathVariable("questionnaireId") Long questionnaireId) {
        Report report = reportService.selectReportByQuestionnaireId(questionnaireId);
        return report != null ? Result.success(report) : Result.error("报告不存在");
    }

    /**
     * 分页查询所有报告
     */
    @GetMapping("/list")
    public Result<PageInfo<Report>> listReports(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Report> pageInfo = reportService.listReports(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 模糊搜索报告内容（支持分页）
     */
    @GetMapping("/search")
    public Result<PageInfo<Report>> searchReports(@RequestParam String keyword,
                                                  @RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Report> pageInfo = reportService.searchReports(keyword, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 根据问卷ID删除报告
     */
    @DeleteMapping("/delete/{questionnaireId}")
    public Result<String> deleteReportByQuestionnaireId(@PathVariable("questionnaireId") Long questionnaireId) {
        int result = reportService.deleteReportByQuestionnaireId(questionnaireId);
        return result > 0 ? Result.success("报告删除成功") : Result.error("删除失败");
    }
}

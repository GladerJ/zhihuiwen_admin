package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.TotalReport;
import top.mygld.zhihuiwen_admin.service.TotalReportService;

@RestController
@RequestMapping("/totalReport")
public class TotalReportController {

    @Autowired
    private TotalReportService totalReportService;

    /**
     * 插入总报告
     */
    @PostMapping("/insert")
    public Result<String> insertTotalReport(@RequestBody TotalReport totalReport) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        totalReport.setUserId(userId);
        int result = totalReportService.insertTotalReport(totalReport);
        return result > 0 ? Result.success("插入成功") : Result.error("插入失败");
    }

    /**
     * 更新总报告
     */
    @PutMapping("/update")
    public Result<String> updateTotalReport(@RequestBody TotalReport totalReport) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        totalReport.setUserId(userId);
        int result = totalReportService.updateTotalReport(totalReport);
        return result > 0 ? Result.success("更新成功") : Result.error("更新失败");
    }

    /**
     * 根据用户ID查询单条总报告（原有方法）
     */
    @GetMapping("/get")
    public Result<TotalReport> getTotalReport() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TotalReport report = totalReportService.selectTotalReportByUserId(userId);
        return report != null ? Result.success(report) : Result.error("未查询到总报告");
    }

    /**
     * 分页查询当前用户的所有总报告
     */
    @GetMapping("/list")
    public Result<PageInfo<TotalReport>> listTotalReports(@RequestParam(defaultValue = "1") int pageNum,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<TotalReport> pageInfo = totalReportService.listTotalReports(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 模糊搜索总报告内容（支持分页）
     */
    @GetMapping("/search")
    public Result<PageInfo<TotalReport>> searchTotalReports(@RequestParam String keyword,
                                                            @RequestParam(defaultValue = "1") int pageNum,
                                                            @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<TotalReport> pageInfo = totalReportService.searchTotalReportsByContent(userId, keyword, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 根据用户ID删除总报告
     */
    @DeleteMapping("/delete")
    public Result<String> deleteTotalReport() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int result = totalReportService.deleteTotalReportByUserId(userId);
        return result > 0 ? Result.success("删除成功") : Result.error("删除失败");
    }
}

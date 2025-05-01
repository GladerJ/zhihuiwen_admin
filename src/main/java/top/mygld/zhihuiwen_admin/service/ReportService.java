package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Report;

public interface ReportService {
    int insertReport(Report report);
    int updateReport(Report report);
    Report selectReportById(Long id);
    Report selectReportByQuestionnaireId(Long questionnaireId);
    int deleteReportByQuestionnaireId(Long questionnaireId);

    // 分页查询所有报告
    PageInfo<Report> listReports(int pageNum, int pageSize);

    // 根据关键词模糊搜索报告内容（支持分页）
    PageInfo<Report> searchReports(String keyword, int pageNum, int pageSize);
}

package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mygld.zhihuiwen_admin.mapper.ReportMapper;
import top.mygld.zhihuiwen_admin.pojo.Report;
import top.mygld.zhihuiwen_admin.service.ReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public int insertReport(Report report) {
        return reportMapper.insertReport(report);
    }

    @Override
    public int updateReport(Report report) {
        return reportMapper.updateReport(report);
    }

    @Override
    public Report selectReportById(Long id) {
        return reportMapper.selectReportById(id);
    }

    @Override
    public Report selectReportByQuestionnaireId(Long questionnaireId) {
        return reportMapper.selectReportByQuestionnaireId(questionnaireId);
    }

    @Override
    public int deleteReportByQuestionnaireId(Long questionnaireId) {
        return reportMapper.deleteReportByQuestionnaireId(questionnaireId);
    }

    @Override
    public PageInfo<Report> listReports(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Report> list = reportMapper.selectAllReports();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Report> searchReports(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // keyword 封装到 map 中传入
        List<Report> list = reportMapper.searchReportsByContent(keyword);
        return new PageInfo<>(list);
    }
}

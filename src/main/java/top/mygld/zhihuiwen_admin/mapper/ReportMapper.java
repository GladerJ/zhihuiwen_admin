package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mygld.zhihuiwen_admin.pojo.Report;

import java.util.List;

@Mapper
public interface ReportMapper {
    int insertReport(Report report);
    int updateReport(Report report);
    Report selectReportById(Long id);
    Report selectReportByQuestionnaireId(Long questionnaireId);
    int deleteReportByQuestionnaireId(Long questionnaireId);
    List<Report> selectAllReports();
    List<Report> searchReportsByContent(@Param("keyword") String keyword);
}

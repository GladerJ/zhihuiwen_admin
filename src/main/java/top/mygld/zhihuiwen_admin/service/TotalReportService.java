package top.mygld.zhihuiwen_admin.service;
import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.TotalReport;

public interface TotalReportService {
    int insertTotalReport(TotalReport TotalReport);
    int updateTotalReport(TotalReport TotalReport);
    int deleteTotalReportByUserId(Long userId);
    TotalReport selectTotalReportByUserId(Long userId);


    public PageInfo<TotalReport> listTotalReports(int pageNum, int pageSize);

    public PageInfo<TotalReport> searchTotalReportsByContent( String keyword, int pageNum, int pageSize);

}

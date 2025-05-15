package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mygld.zhihuiwen_admin.pojo.TotalReport;

import java.util.List;

@Mapper
public interface TotalReportMapper {
    int insertTotalReport(TotalReport totalReport);
    int updateTotalReport(TotalReport totalReport);
    int deleteTotalReportByUserId(@Param("userId")Long userId);

    // 原有方法返回单条记录（如果每个用户只有一个总报告，可保留）
    TotalReport selectTotalReportByUserId(@Param("userId")Long userId);

    // 新增：返回指定用户的所有总报告（支持分页）
    List<TotalReport> selectTotalReports();

    // 新增：根据关键词模糊搜索，总报告内容（限定用户）
    List<TotalReport> searchTotalReportsByContent(@Param("keyword") String keyword);
}

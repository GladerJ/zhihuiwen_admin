package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mygld.zhihuiwen_admin.mapper.TotalReportMapper;
import top.mygld.zhihuiwen_admin.pojo.TotalReport;
import top.mygld.zhihuiwen_admin.service.TotalReportService;

import java.util.List;

@Service
public class TotalReportServiceImpl implements TotalReportService {

    @Autowired
    private TotalReportMapper totalReportMapper;

    @Override
    public int insertTotalReport(TotalReport totalReport) {
        return totalReportMapper.insertTotalReport(totalReport);
    }

    @Override
    public int updateTotalReport(TotalReport totalReport) {
        return totalReportMapper.updateTotalReport(totalReport);
    }

    @Override
    public int deleteTotalReportByUserId(Long userId) {
        return totalReportMapper.deleteTotalReportByUserId(userId);
    }

    @Override
    public TotalReport selectTotalReportByUserId(Long userId) {
        return totalReportMapper.selectTotalReportByUserId(userId);
    }

    @Override
    public PageInfo<TotalReport> listTotalReports(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TotalReport> list = totalReportMapper.selectTotalReports();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TotalReport> searchTotalReportsByContent(Long userId, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TotalReport> list = totalReportMapper.searchTotalReportsByContent(userId, keyword);
        return new PageInfo<>(list);
    }
}

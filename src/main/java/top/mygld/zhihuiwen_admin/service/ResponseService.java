package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import top.mygld.zhihuiwen_admin.pojo.Response;

public interface ResponseService {
    // 新增回复（含答案），保存时自动设置 IP、UserAgent 和提交时间
    Response saveResponse(Response response, HttpServletRequest request);

    // 更新回复（可包含更新答案信息，具体逻辑可根据需求调整）
    Response updateResponse(Response response);

    // 根据 id 查询回复详情（包括答案）
    Response getResponseById(Long id);

    // 分页查询某问卷下所有回复（含答案）
    PageInfo<Response> listResponses(int pageNum, int pageSize);

    // 删除回复（级联删除答案）
    int deleteResponseById(Long id);

    // 根据问卷ID删除所有回复（级联删除答案）
    int deleteResponseByQuestionnaireId(Long questionnaireId);
}

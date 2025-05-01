package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mygld.zhihuiwen_admin.mapper.AnswerMapper;
import top.mygld.zhihuiwen_admin.mapper.ResponseMapper;
import top.mygld.zhihuiwen_admin.pojo.Answer;
import top.mygld.zhihuiwen_admin.pojo.Response;
import top.mygld.zhihuiwen_admin.service.ResponseService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Response saveResponse(Response response, HttpServletRequest request) {
        // 设置IP地址和UserAgent
        response.setIpAddress(getClientIp(request));
        response.setUserAgent(request.getHeader("User-Agent"));
        if (response.getSubmittedAt() == null) {
            response.setSubmittedAt(new Date());
        }
        responseMapper.insertResponse(response);
        // 保存答案
        List<Answer> answers = response.getAnswers();
        if (answers != null && !answers.isEmpty()) {
            for (Answer answer : answers) {
                answer.setResponseId(response.getId());
                answerMapper.insertAnswer(answer);
            }
        }
        return response;
    }

    @Override
    public Response updateResponse(Response response) {
        response.setSubmittedAt(new Date());
        responseMapper.updateResponse(response);
        // 此处不做级联更新答案，答案可单独通过 AnswerService 更新
        return responseMapper.selectResponseById(response.getId());
    }

    @Override
    public Response getResponseById(Long id) {
        Response response = responseMapper.selectResponseById(id);
        if (response != null) {
            List<Answer> answers = answerMapper.selectAllAnswersByResponseId(response.getId());
            response.setAnswers(answers);
        }
        return response;
    }

    @Override
    public PageInfo<Response> listResponses(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Response> responses = responseMapper.selectAllResponses();
        for (Response r : responses) {
            List<Answer> answers = answerMapper.selectAllAnswersByResponseId(r.getId());
            r.setAnswers(answers);
        }
        return new PageInfo<>(responses);
    }

    @Override
    public int deleteResponseById(Long id) {
        // 先删除对应答案，再删除回复
        answerMapper.deleteAnswerByResponseId(id);
        return responseMapper.deleteResponseById(id);
    }

    @Override
    public int deleteResponseByQuestionnaireId(Long questionnaireId) {
        List<Response> responses = responseMapper.selectAllResponsesByQuestionnaireId(questionnaireId);
        for (Response r : responses) {
            answerMapper.deleteAnswerByResponseId(r.getId());
        }
        return responseMapper.deleteResponseByQuestionnaireId(questionnaireId);
    }

    // 获取客户端真实IP地址
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }
}

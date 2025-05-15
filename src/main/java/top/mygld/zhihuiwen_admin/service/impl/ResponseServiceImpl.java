package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import top.mygld.zhihuiwen_admin.dto.AnswerDTO;
import top.mygld.zhihuiwen_admin.dto.ResponseDTO;
import top.mygld.zhihuiwen_admin.mapper.AnswerMapper;
import top.mygld.zhihuiwen_admin.mapper.QuestionnaireMapper;
import top.mygld.zhihuiwen_admin.mapper.ResponseMapper;
import top.mygld.zhihuiwen_admin.pojo.Answer;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;
import top.mygld.zhihuiwen_admin.pojo.QuestionnaireOption;
import top.mygld.zhihuiwen_admin.pojo.QuestionnaireQuestion;
import top.mygld.zhihuiwen_admin.pojo.Response;
import top.mygld.zhihuiwen_admin.service.ResponseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

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
    public ResponseDTO getResponseDTOById(Long id) {
        // 获取基本的回复信息
        Response response = getResponseById(id);
        if (response == null) {
            return null;
        }

        // 创建DTO对象
        ResponseDTO responseDTO = new ResponseDTO(response);

        // 补充答案的详细信息
        enrichAnswerDTOs(responseDTO);

        return responseDTO;
    }

    @Override
    public PageInfo<Response> listResponses(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Response> responses = responseMapper.selectAllResponses();
        return new PageInfo<>(responses);
    }

    @Override
    public PageInfo<ResponseDTO> listResponseDTOs(int pageNum, int pageSize) {
        // 获取基本的回复信息列表
        PageInfo<Response> pageInfo = listResponses(pageNum, pageSize);

        // 转换为DTO列表
        List<ResponseDTO> responseDTOs = pageInfo.getList().stream()
                .map(ResponseDTO::new)
                .collect(Collectors.toList());

        // 为每个ResponseDTO补充答案的详细信息
        for (ResponseDTO dto : responseDTOs) {
            enrichAnswerDTOs(dto);
        }

        // 创建新的PageInfo对象，保留分页信息
        PageInfo<ResponseDTO> dtoPageInfo = new PageInfo<>(responseDTOs);
        dtoPageInfo.setTotal(pageInfo.getTotal());
        dtoPageInfo.setPages(pageInfo.getPages());
        dtoPageInfo.setPageNum(pageInfo.getPageNum());
        dtoPageInfo.setPageSize(pageInfo.getPageSize());
        dtoPageInfo.setStartRow(pageInfo.getStartRow());
        dtoPageInfo.setEndRow(pageInfo.getEndRow());
        dtoPageInfo.setSize(pageInfo.getSize());
        dtoPageInfo.setPrePage(pageInfo.getPrePage());
        dtoPageInfo.setNextPage(pageInfo.getNextPage());
        dtoPageInfo.setIsFirstPage(pageInfo.isIsFirstPage());
        dtoPageInfo.setIsLastPage(pageInfo.isIsLastPage());
        dtoPageInfo.setHasPreviousPage(pageInfo.isHasPreviousPage());
        dtoPageInfo.setHasNextPage(pageInfo.isHasNextPage());
        dtoPageInfo.setNavigatePages(pageInfo.getNavigatePages());
        dtoPageInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
        dtoPageInfo.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        dtoPageInfo.setNavigateLastPage(pageInfo.getNavigateLastPage());

        return dtoPageInfo;
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

    // 补充AnswerDTO的详细信息（问题文本和选项文本）
    private void enrichAnswerDTOs(ResponseDTO responseDTO) {
        if (responseDTO == null || responseDTO.getAnswers() == null || responseDTO.getAnswers().isEmpty()) {
            return;
        }

        // 获取问卷信息，用于查找问题和选项的文本内容
        Questionnaire questionnaire = questionnaireMapper.selectQuestionnaireById(responseDTO.getQuestionnaireId());
        if (questionnaire == null || questionnaire.getQuestions() == null) {
            return;
        }

        // 创建两个Map用于快速查找问题和选项
        Map<Long, QuestionnaireQuestion> questionMap = new HashMap<>();
        Map<Long, Map<Long, QuestionnaireOption>> optionMap = new HashMap<>();

        // 填充Map数据
        for (QuestionnaireQuestion question : questionnaire.getQuestions()) {
            questionMap.put(question.getId(), question);

            if (question.getOptions() != null) {
                Map<Long, QuestionnaireOption> questionOptions = new HashMap<>();
                for (QuestionnaireOption option : question.getOptions()) {
                    questionOptions.put(option.getId(), option);
                }
                optionMap.put(question.getId(), questionOptions);
            }
        }

        // 为每个答案DTO补充问题文本和选项文本
        for (AnswerDTO answerDTO : responseDTO.getAnswers()) {
            Long questionId = answerDTO.getQuestionId();
            QuestionnaireQuestion question = questionMap.get(questionId);

            if (question != null) {
                // 设置问题文本和类型
                answerDTO.setQuestionText(question.getQuestionText());
                answerDTO.setQuestionType(question.getQuestionType());

                // 如果答案是选项类型，则设置选项文本
                if ("option".equals(answerDTO.getAnswerType()) && answerDTO.getAnswerContent() != null) {
                    // 获取原始回答内容
                    Object originalContent = answerDTO.getAnswerContent();
                    String content = originalContent.toString();

                    try {
                        // 尝试解析为JSON数组（针对多选题）
                        List<String> optionTexts = new ArrayList<>();
                        JSONArray optionIds = null;

                        // 检查内容是否为JSON数组格式
                        if (content.startsWith("[") && content.endsWith("]")) {
                            optionIds = JSON.parseArray(content);
                        } else {
                            // 单个选项ID的情况
                            optionIds = new JSONArray();
                            optionIds.add(Long.valueOf(content));
                        }

                        // 获取选项Map
                        Map<Long, QuestionnaireOption> questionOptions = optionMap.get(questionId);

                        // 遍历所有选项ID，查找对应的选项文本
                        if (questionOptions != null && !optionIds.isEmpty()) {
                            for (int i = 0; i < optionIds.size(); i++) {
                                Long optionId = optionIds.getLongValue(i);
                                QuestionnaireOption option = questionOptions.get(optionId);
                                if (option != null) {
                                    optionTexts.add(option.getOptionText());
                                }
                            }

                            // 将选项文本连接成字符串并设置到DTO中
                            if (!optionTexts.isEmpty()) {
                                answerDTO.setOptionText(String.join("、", optionTexts));

                                // 将原始的选项ID列表保留在answerContent中，同时添加选项文本到DTO
                                // 创建一个包含ID和文本的对象
                                Map<String, Object> contentWithText = new HashMap<>();
                                contentWithText.put("optionIds", optionIds);
                                contentWithText.put("optionTexts", optionTexts);
                                // 更新answerContent为包含文本的完整信息
                                answerDTO.setAnswerContent(contentWithText);
                            }
                        }
                    } catch (Exception e) {
                        // 解析失败的情况，保留原始内容
                        System.err.println("解析选项答案失败: " + e.getMessage());
                    }
                }
            }
        }
    }
    @Override
    public PageInfo<Response> listResponsesByQuestionnaireId(Long questionnaireId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Response> responses = responseMapper.selectAllResponsesByQuestionnaireId(questionnaireId);
        return new PageInfo<>(responses);
    }
}
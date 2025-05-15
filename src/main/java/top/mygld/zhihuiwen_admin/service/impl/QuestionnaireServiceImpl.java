package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mygld.zhihuiwen_admin.mapper.QuestionnaireMapper;
import top.mygld.zhihuiwen_admin.mapper.ReportMapper;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;
import top.mygld.zhihuiwen_admin.pojo.QuestionnaireOption;
import top.mygld.zhihuiwen_admin.pojo.QuestionnaireQuestion;
import top.mygld.zhihuiwen_admin.service.QuestionnaireService;
import top.mygld.zhihuiwen_admin.service.ReportService;
import top.mygld.zhihuiwen_admin.service.ResponseService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResponseService responseService;

    @Override
    public PageInfo<Questionnaire> searchQuestionnairesByTitle(Long categoryId, String title, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Questionnaire> list = questionnaireMapper.selectQuestionnaireByTitleLike(categoryId, title);
        return new PageInfo<>(list);
    }
    @Override
    public void addQuestionnaire(Questionnaire questionnaire) {
        Date now = new Date();
        questionnaire.setCreatedAt(now);
        questionnaire.setUpdatedAt(now);
        // 插入主问卷记录（插入后需确保自动生成的 ID 被设置到对象中）
        questionnaireMapper.insertQuestionnaire(questionnaire);
        // 级联插入题目及选项
        if (questionnaire.getQuestions() != null) {
            for (QuestionnaireQuestion question : questionnaire.getQuestions()) {
                question.setQuestionnaireId(questionnaire.getId());
                questionnaireMapper.insertQuestionnaireQuestion(question);
                if (question.getOptions() != null) {
                    for (QuestionnaireOption option : question.getOptions()) {
                        option.setQuestionId(question.getId());
                        questionnaireMapper.insertQuestionnaireOption(option);
                    }
                }
            }
        }
    }

    @Override
    public void updateQuestionnaire(Questionnaire questionnaire) {
        questionnaire.setUpdatedAt(new Date());
        // 更新问卷主记录
        questionnaireMapper.updateQuestionnaire(questionnaire);
        // 先级联删除旧的题目与选项
        questionnaireMapper.deleteOptionsByQuestionnaireId(questionnaire.getId());
        questionnaireMapper.deleteQuestionsByQuestionnaireId(questionnaire.getId());
        // 插入新的题目和选项
        if (questionnaire.getQuestions() != null) {
            for (QuestionnaireQuestion question : questionnaire.getQuestions()) {
                question.setQuestionnaireId(questionnaire.getId());
                questionnaireMapper.insertQuestionnaireQuestion(question);
                if (question.getOptions() != null) {
                    for (QuestionnaireOption option : question.getOptions()) {
                        option.setQuestionId(question.getId());
                        questionnaireMapper.insertQuestionnaireOption(option);
                    }
                }
            }
        }
    }

    @Override
    public void deleteQuestionnaire(Long questionnaireId) {
        // 删除问卷对应的报告
        reportService.deleteReportByQuestionnaireId(questionnaireId);

        // 删除问卷对应的所有回复及答案
        responseService.deleteResponseByQuestionnaireId(questionnaireId);

        // 级联删除题目与选项后删除问卷
        questionnaireMapper.deleteOptionsByQuestionnaireId(questionnaireId);
        questionnaireMapper.deleteQuestionsByQuestionnaireId(questionnaireId);
        questionnaireMapper.deleteQuestionnaire(questionnaireId);
    }

    @Override
    public Questionnaire getQuestionnaireById(Long id) {
        // 查询问卷详情（包含题目和选项），需在 XML 映射中编写级联查询
        return questionnaireMapper.selectQuestionnaireByIdAndUserId(id);
    }

    @Override
    public PageInfo<Questionnaire> listQuestionnaires(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Questionnaire> list = questionnaireMapper.selectAllQuestionnaires();
        return new PageInfo<>(list);
    }
}
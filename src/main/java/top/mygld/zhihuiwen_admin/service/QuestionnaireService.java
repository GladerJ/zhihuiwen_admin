package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;

public interface QuestionnaireService {
    // 新增问卷（级联插入题目和选项）
    void addQuestionnaire(Questionnaire questionnaire);

    // 更新问卷（级联删除旧题目与选项，重新插入新数据）
    void updateQuestionnaire(Questionnaire questionnaire);

    // 删除问卷（级联删除题目与选项）
    void deleteQuestionnaire(Long questionnaireId);

    // 根据问卷ID和当前用户查询问卷详情（包含题目和选项）
    Questionnaire getQuestionnaireById(Long id);

    // 分页查询当前用户所有问卷（简单查询，不含级联数据）
    PageInfo<Questionnaire> listQuestionnaires(int pageNum, int pageSize);

    PageInfo<Questionnaire> searchQuestionnairesByTitle(Long categoryId, String title, int pageNum, int pageSize);
}

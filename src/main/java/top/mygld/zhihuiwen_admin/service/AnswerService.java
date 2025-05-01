package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import top.mygld.zhihuiwen_admin.pojo.Answer;

public interface AnswerService {
    // 新增答案
    Answer addAnswer(Answer answer);

    // 更新答案
    Answer updateAnswer(Answer answer);

    // 根据 id 查询答案详情
    Answer getAnswerById(Long id);

    // 分页查询指定 responseId 的答案
    PageInfo<Answer> listAnswersByResponseId(Long responseId, int pageNum, int pageSize);

    // 根据 responseId 和关键词进行模糊搜索答案（针对答案内容）
    PageInfo<Answer> searchAnswersByContent(Long responseId, String keyword, int pageNum, int pageSize);

    // 删除答案
    int deleteAnswerById(Long id);
}

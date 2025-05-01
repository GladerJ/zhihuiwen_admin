package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mygld.zhihuiwen_admin.mapper.AnswerMapper;
import top.mygld.zhihuiwen_admin.pojo.Answer;
import top.mygld.zhihuiwen_admin.service.AnswerService;

import java.util.List;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Answer addAnswer(Answer answer) {
        answerMapper.insertAnswer(answer);
        return answer;
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        answerMapper.updateAnswer(answer);
        return answerMapper.selectAnswerById(answer.getId());
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerMapper.selectAnswerById(id);
    }

    @Override
    public PageInfo<Answer> listAnswersByResponseId(Long responseId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> list = answerMapper.selectAllAnswersByResponseId(responseId);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Answer> searchAnswersByContent(Long responseId, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> list = answerMapper.searchAnswersByContent(responseId, keyword);
        return new PageInfo<>(list);
    }

    @Override
    public int deleteAnswerById(Long id) {
        return answerMapper.deleteAnswerById(id);
    }
}

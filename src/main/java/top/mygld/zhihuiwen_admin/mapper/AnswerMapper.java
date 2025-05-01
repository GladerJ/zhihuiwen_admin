package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mygld.zhihuiwen_admin.pojo.Answer;

import java.util.List;

@Mapper
public interface AnswerMapper {
    int insertAnswer(Answer answer);

    int updateAnswer(Answer answer);

    Answer selectAnswerById(@Param("id") Long id);

    List<Answer> selectAllAnswersByResponseId(@Param("responseId") Long responseId);

    // 根据答案内容进行模糊搜索，限定当前 responseId
    List<Answer> searchAnswersByContent(@Param("responseId") Long responseId, @Param("keyword") String keyword);

    int deleteAnswerById(@Param("id") Long id);

    int deleteAnswerByResponseId(@Param("responseId") Long responseId);
}

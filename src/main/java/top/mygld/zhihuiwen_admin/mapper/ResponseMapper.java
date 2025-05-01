package top.mygld.zhihuiwen_admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mygld.zhihuiwen_admin.pojo.Response;

import java.util.List;

@Mapper
public interface ResponseMapper {
    int insertResponse(Response response);

    int updateResponse(Response response);

    Response selectResponseById(@Param("id") Long id);
    List<Response> selectAllResponsesByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);
    List<Response> selectAllResponses();

    int deleteResponseById(@Param("id") Long id);

    // 根据问卷ID删除所有回复（级联删除答案由 AnswerMapper 完成）
    int deleteResponseByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);
}

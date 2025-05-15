package top.mygld.zhihuiwen_admin.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.mygld.zhihuiwen_admin.pojo.Answer;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnswerDTO {
    // 原始Answer对象的字段
    private Long id;
    private Long responseId;
    private Long questionId;
    private String answerType;
    private Object answerContent; // 可能是字符串、数字或复杂对象
    private Date createdAt;

    // 增加的展示字段
    private String questionText;
    private Object questionType;
    private String optionText;   // 选项文本，多个选项以分隔符连接
    private List<String> optionTexts; // 新增：存储多个选项的文本列表

    // 从Answer对象创建DTO的构造函数
    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.responseId = answer.getResponseId();
        this.questionId = answer.getQuestionId();
        this.answerType = answer.getAnswerType();
        this.answerContent = answer.getAnswerContent();
        this.createdAt = answer.getCreatedAt();
    }
}
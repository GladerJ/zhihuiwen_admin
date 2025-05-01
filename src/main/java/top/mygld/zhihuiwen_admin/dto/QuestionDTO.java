package top.mygld.zhihuiwen_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.mygld.zhihuiwen_admin.pojo.QuestionnaireQuestion;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionDTO {
    private String title;
    private String suggestion;
    private QuestionnaireQuestion question;
}

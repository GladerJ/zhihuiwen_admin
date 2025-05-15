package top.mygld.zhihuiwen_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.mygld.zhihuiwen_admin.pojo.Response;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDTO {
    private Long id;
    private Long questionnaireId;
    private Long userId;
    private Long duration;
    private String ipAddress;
    private String userAgent;
    private Date submittedAt;
    private List<AnswerDTO> answers;

    // 从Response对象创建DTO的构造函数
    public ResponseDTO(Response response) {
        this.id = response.getId();
        this.questionnaireId = response.getQuestionnaireId();
        this.userId = response.getUserId();
        this.duration = response.getDuration();
        this.ipAddress = response.getIpAddress();
        this.userAgent = response.getUserAgent();
        this.submittedAt = response.getSubmittedAt();

        // 如果有答案，转换为DTO
        if (response.getAnswers() != null) {
            this.answers = response.getAnswers().stream()
                    .map(AnswerDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
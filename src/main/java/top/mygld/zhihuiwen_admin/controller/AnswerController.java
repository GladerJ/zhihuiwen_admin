package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.pojo.Answer;
import top.mygld.zhihuiwen_admin.service.AnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * 新增答案
     */
    @PostMapping("/add")
    public Result<Answer> addAnswer(@RequestBody Answer answer) {
        Answer saved = answerService.addAnswer(answer);
        return Result.success(saved);
    }

    /**
     * 更新答案
     */
    @PutMapping("/update")
    public Result<Answer> updateAnswer(@RequestBody Answer answer) {
        Answer updated = answerService.updateAnswer(answer);
        return Result.success(updated);
    }

    /**
     * 根据ID查询答案详情
     */
    @GetMapping("/{id}")
    public Result<Answer> getAnswer(@PathVariable("id") Long id) {
        Answer answer = answerService.getAnswerById(id);
        if (answer == null) {
            return Result.error("答案不存在");
        }
        return Result.success(answer);
    }

    /**
     * 分页查询指定回复的所有答案
     */
    @GetMapping("/list/{responseId}")
    public Result<PageInfo<Answer>> listAnswers(@PathVariable("responseId") Long responseId,
                                                @RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Answer> pageInfo = answerService.listAnswersByResponseId(responseId, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 根据回复ID和关键词模糊搜索答案（针对答案内容）
     */
    @GetMapping("/search/{responseId}")
    public Result<PageInfo<Answer>> searchAnswers(@PathVariable("responseId") Long responseId,
                                                  @RequestParam String keyword,
                                                  @RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Answer> pageInfo = answerService.searchAnswersByContent(responseId, keyword, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 根据ID删除答案
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteAnswer(@PathVariable("id") Long id) {
        int count = answerService.deleteAnswerById(id);
        return count > 0 ? Result.success("删除成功") : Result.error("删除失败");
    }
}

package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
import top.mygld.zhihuiwen_admin.dto.ResponseDTO;
import top.mygld.zhihuiwen_admin.pojo.Response;
import top.mygld.zhihuiwen_admin.service.ResponseService;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    /**
     * 根据ID查询回复详情（含答案）
     */
    @GetMapping("/{id}")
    public Result<ResponseDTO> getResponse(@PathVariable("id") Long id) {
        ResponseDTO responseDTO = responseService.getResponseDTOById(id);
        if (responseDTO == null) {
            return Result.error("回复不存在");
        }
        return Result.success(responseDTO);
    }

    /**
     * 分页查询回复（支持按问卷ID筛选）
     */
    @GetMapping("/list")
    public Result<PageInfo<Response>> listResponses(
            @RequestParam(required = false) Long questionnaireId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Response> pageInfo;
        if (questionnaireId != null) {
            // 如果指定了问卷ID，则筛选该问卷下的回复
            pageInfo = responseService.listResponsesByQuestionnaireId(questionnaireId, pageNum, pageSize);
        } else {
            // 否则查询所有回复
            pageInfo = responseService.listResponses(pageNum, pageSize);
        }
        return Result.success(pageInfo);
    }
    /**
     * 根据回复ID删除回复（级联删除答案）
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteResponse(@PathVariable("id") Long id) {
        int count = responseService.deleteResponseById(id);
        return count > 0 ? Result.success("删除成功") : Result.error("删除失败");
    }
}
package top.mygld.zhihuiwen_admin.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mygld.zhihuiwen_admin.common.Result;
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
    public Result<Response> getResponse(@PathVariable("id") Long id) {
        Response response = responseService.getResponseById(id);
        if (response == null) {
            return Result.error("回复不存在");
        }
        return Result.success(response);
    }

    /**
     * 分页查询指定问卷下所有回复（含答案）
     */
    @GetMapping("/list")
    public Result<PageInfo<Response>> listResponses(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<Response> pageInfo = responseService.listResponses(pageNum, pageSize);
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

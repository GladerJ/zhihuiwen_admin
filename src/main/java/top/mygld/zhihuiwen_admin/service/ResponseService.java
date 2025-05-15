package top.mygld.zhihuiwen_admin.service;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import top.mygld.zhihuiwen_admin.dto.ResponseDTO;
import top.mygld.zhihuiwen_admin.pojo.Response;

public interface ResponseService {

    Response saveResponse(Response response, HttpServletRequest request);

    Response updateResponse(Response response);

    Response getResponseById(Long id);

    ResponseDTO getResponseDTOById(Long id);

    PageInfo<Response> listResponses(int pageNum, int pageSize);

    PageInfo<Response> listResponsesByQuestionnaireId(Long questionnaireId, int pageNum, int pageSize);

    PageInfo<ResponseDTO> listResponseDTOs(int pageNum, int pageSize);

    int deleteResponseById(Long id);

    int deleteResponseByQuestionnaireId(Long questionnaireId);
}
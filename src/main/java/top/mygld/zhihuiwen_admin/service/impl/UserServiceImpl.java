package top.mygld.zhihuiwen_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mygld.zhihuiwen_admin.mapper.CategoryMapper;
import top.mygld.zhihuiwen_admin.mapper.QuestionnaireMapper;
import top.mygld.zhihuiwen_admin.mapper.UserMapper;
import top.mygld.zhihuiwen_admin.pojo.Category;
import top.mygld.zhihuiwen_admin.pojo.Questionnaire;
import top.mygld.zhihuiwen_admin.pojo.User;
import top.mygld.zhihuiwen_admin.service.CategoryService;
import top.mygld.zhihuiwen_admin.service.QuestionnaireService;
import top.mygld.zhihuiwen_admin.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;


    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int deleteUser(Long id) {
        // 1. 级联删除用户下所有分类（分类删除时已级联删除问卷）
        List<Category> categories = categoryMapper.selectCategoriesByUserId(id);
        if (categories != null) {
            for (Category category : categories) {
                category.setUserId(id);
                categoryService.deleteCategory(category);
            }
        }
        // 2. 删除用户可能存在的单独问卷（不在任何分类下的问卷），根据实际情况判断是否需要删除
        List<Questionnaire> questionnaires = questionnaireMapper.selectAllQuestionnairesByUserId(id);
        if (questionnaires != null) {
            for (Questionnaire q : questionnaires) {
                // 如果问卷的 categoryId 为 null 或者不需要保留，可以进行删除
                questionnaireService.deleteQuestionnaire(q.getId());
            }
        }
        // 3. 删除用户记录
        return userMapper.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public PageInfo<User> getUsers(String username, int pageNum, int pageSize) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectUsersByUsername(username);
        return new PageInfo<>(users);
    }
}

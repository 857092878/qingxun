package com.ustb.evaluation.mod05test.controller;

import com.ustb.evaluation.mod01common.controller.BaseController;
import com.ustb.evaluation.mod01common.domain.http.ApiResult;
import com.ustb.evaluation.mod01common.domain.http.ResponseStatus;
import com.ustb.evaluation.mod01common.domain.page.PageRequest;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod05test.domain.StudentPageshow.StudentPageshow;
import com.ustb.evaluation.mod05test.service.impl.StudentPageshowServiceImpl;
import com.ustb.evaluation.mod05test.service.impl.TstPageshowServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"学生分页显示"})
@RestController
@RequestMapping("/v1/student/StudentPageshow")
@CrossOrigin
public class StudentPageshowController extends BaseController<StudentPageshow> {

    @Autowired
    private StudentPageshowServiceImpl studentPageshowService;

    @Override
    protected void setTransactOption() {
        transInsert = false;
        transUpdate = true;
        transDeleteByID = false;
    }

    @PostMapping("/insert")
    public ApiResult insert(@Valid @RequestBody StudentPageshow object, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> ls = result.getFieldErrors();
            throw new PromptException(result.getFieldErrors().get(0).getDefaultMessage());
        }
        return super.insert(object);
    }

    @PostMapping("/update")
    public ApiResult update(@Valid @RequestBody StudentPageshow object, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> ls = result.getFieldErrors();
            throw new PromptException(result.getFieldErrors().get(0).getDefaultMessage());
        }
        return super.update(object);
    }

    @Override
    @GetMapping("/delete/{id}")
    public ApiResult deleteById(@PathVariable Long id) {
        return super.deleteById(id);
    }

    @Override
    @GetMapping("/find/{id}")
    public ApiResult findById(@PathVariable Long id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("/findPage")
    protected ApiResult findPage(@RequestBody PageRequest pageRequest) {
        return super.findPage(pageRequest);
    }

    @GetMapping("/findFgender")
    public ApiResult findFgender() {
        return new ApiResult(ResponseStatus.SUCCESS, "执行成功！", studentPageshowService.findFgender());
    }

}


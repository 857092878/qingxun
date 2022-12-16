package com.ustb.evaluation.mod05test.controller;

import com.ustb.evaluation.mod01common.controller.BaseController;
import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.domain.http.ApiResult;
import com.ustb.evaluation.mod01common.domain.http.ResponseStatus;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod05test.domain.StudentTreeshow.StudentTreeshow;
import com.ustb.evaluation.mod05test.domain.StudentTreeshow.StudentTreeshowTree;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshow;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshowTree;
import com.ustb.evaluation.mod05test.service.impl.StudentTreeshowServiceImpl;
import com.ustb.evaluation.mod05test.service.impl.TstTreeshowServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"学生树形显示"})
@RestController
@RequestMapping("/v1/student/StudentTreeshow")
@CrossOrigin
public class StudentTreeshowController extends BaseController<StudentTreeshow> {

    @Autowired
    private StudentTreeshowServiceImpl studentTreeshowService;

    @Override
    protected void setTransactOption() {
        transInsert = false;
        transUpdate = true;
        transDeleteByID = false;
    }

    @PostMapping("/insert")
    public ApiResult insert(@Valid @RequestBody StudentTreeshow object, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> ls = result.getFieldErrors();
            throw new PromptException(result.getFieldErrors().get(0).getDefaultMessage());
        }
        return super.insert(object);
    }

    @PostMapping("/update")
    public ApiResult update(@Valid @RequestBody StudentTreeshow object, BindingResult result) {
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

    @GetMapping("/findTree")
    public ApiResult findTree() {
        List<StudentTreeshowTree> ls = studentTreeshowService.findTree();
        return new ApiResult(ResponseStatus.SUCCESS, "执行成功！", ls);
    }

    @GetMapping("/findChoice")
    public ApiResult findChoice() {
        List<PairedData> lspd = studentTreeshowService.findChoice();
        return new ApiResult(ResponseStatus.SUCCESS, "执行成功！", lspd);
    }
}


package com.ustb.evaluation.mod05test.controller;

import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.utils.dictionary.PairedDataConvert;
import com.ustb.evaluation.mod01common.controller.BaseController;
import com.ustb.evaluation.mod01common.domain.http.ApiResult;
import com.ustb.evaluation.mod01common.domain.page.PageRequest;
import com.ustb.evaluation.mod01common.domain.query.QueryConditions;
import com.ustb.evaluation.mod01common.domain.http.ResponseStatus;
import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshow;
import com.ustb.evaluation.mod05test.service.impl.TstTreeshowServiceImpl;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshowTree;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import com.ustb.evaluation.mod01common.exception.PromptException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"树形显示"})
@RestController
@RequestMapping("/v1/tst/TstTreeshow")
@CrossOrigin
public class TstTreeshowController extends BaseController<TstTreeshow> {

@Autowired
private TstTreeshowServiceImpl tstTreeshowService;
@Override
protected void setTransactOption() {
transInsert = false;
transUpdate = true;
transDeleteByID = false;
}

@PostMapping("/insert")
public ApiResult insert(@Valid @RequestBody TstTreeshow object, BindingResult result) {
if(result.hasErrors()){
List<FieldError> ls=result.getFieldErrors();
throw new PromptException(result.getFieldErrors().get(0).getDefaultMessage());
}
return super.insert(object);
}

@PostMapping("/update")
public ApiResult update(@Valid @RequestBody TstTreeshow object, BindingResult result) {
if(result.hasErrors()){
List<FieldError> ls=result.getFieldErrors();
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
List<TstTreeshowTree> ls = tstTreeshowService.findTree();
return new ApiResult(ResponseStatus.SUCCESS, "执行成功！", ls);
}

@GetMapping("/findChoice")
public ApiResult findChoice() {
List<PairedData> lspd=tstTreeshowService.findChoice();
return new ApiResult(ResponseStatus.SUCCESS, "执行成功！", lspd);
}
}


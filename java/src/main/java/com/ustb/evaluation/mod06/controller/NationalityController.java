package com.ustb.evaluation.mod06.controller;


import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.pojo.Nationality;
import com.ustb.evaluation.mod06.pojo.Politics;
import com.ustb.evaluation.mod06.service.INationalityService;
import com.ustb.evaluation.mod06.service.IPoliticsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/stu/Nationality")
public class NationalityController {
    @Resource
    private INationalityService nationalityService;

    @PostMapping("/findAll")
    public Response<Nationality> findAll(){
        List<Nationality> list = nationalityService.list();
        return Response.OK(list);
    }

}

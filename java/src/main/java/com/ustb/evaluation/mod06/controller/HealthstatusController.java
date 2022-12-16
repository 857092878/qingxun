package com.ustb.evaluation.mod06.controller;


import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.pojo.Healthstatus;
import com.ustb.evaluation.mod06.service.IHealthstatusService;
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
@RequestMapping("/stu/HealthStatus")
public class HealthstatusController {
    @Resource
    private IHealthstatusService healthstatusService;
    @PostMapping("findAll")
    public Response<List<Healthstatus>> findAll(){
        List<Healthstatus> list = healthstatusService.list();
        return Response.OK(list);
    }
}

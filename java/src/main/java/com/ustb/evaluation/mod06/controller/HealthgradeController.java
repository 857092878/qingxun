package com.ustb.evaluation.mod06.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.dto.PageDto;
import com.ustb.evaluation.mod06.pojo.Healthgrade;
import com.ustb.evaluation.mod06.service.IHealthgradeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@RestController
@RequestMapping("/stu/Healthgrade/")
public class HealthgradeController {
    @Resource
    private IHealthgradeService healthgradeService;

    @PostMapping("findAll")
    public Response<Healthgrade> findAll(){
        List<Healthgrade> list = healthgradeService.list();
        return Response.OK(list);
    }

    /*
    分页
     */
    @PostMapping("/findPage")
    public Response<PageInfo<Healthgrade>> findPage(@RequestBody PageDto pageDto){

        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());

        if (pageDto.getSearch() == null || "".equals(pageDto.getSearch())){
            List<Healthgrade> result = healthgradeService.list();
            PageInfo<Healthgrade> pageInfo = new PageInfo<>(result);
            return Response.OK(pageInfo);
        }

        List<Healthgrade> result = healthgradeService.findPage(pageDto.getSearch());

        PageInfo<Healthgrade> pageInfo = new PageInfo<>(result);

        return Response.OK(pageInfo);
    }
    /*
    添加
     */
    @PostMapping("/add")
    public Response findPage(@RequestBody Healthgrade healthgrade){
        healthgradeService.add(healthgrade);
        return Response.OK();
    }
    /*
    删除
     */
    @PostMapping("/del/{id}")
    public Response del(@PathVariable Integer id){
        healthgradeService.del(id);
        return Response.OK();
    }
    @PostMapping("/dels/{ids}")
    public Response dels(@RequestBody List<Integer> ids){
        healthgradeService.dels(ids);
        return Response.OK();
    }
    /*
    excel导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Healthgrade> list = healthgradeService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("健康", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Healthgrade> list = reader.readAll(Healthgrade.class);
        healthgradeService.saveBatch(list);
        return true;
    }
}

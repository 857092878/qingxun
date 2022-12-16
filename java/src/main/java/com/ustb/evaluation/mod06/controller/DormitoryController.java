package com.ustb.evaluation.mod06.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.dto.PageDto;
import com.ustb.evaluation.mod06.pojo.Dormitory;
import com.ustb.evaluation.mod06.service.IDormitoryService;
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
@RequestMapping("/stu/Dormitory")
public class DormitoryController {
    @Resource
    private IDormitoryService dormitoryService;

    @PostMapping("/findAll")
    public Response<Dormitory> finAll(){
        List<Dormitory> list = dormitoryService.list();
        return Response.OK(list);
    }
    /*
    分页
     */
    @PostMapping("/findPage")
    public Response<PageInfo<Dormitory>> findPage(@RequestBody PageDto pageDto){

        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());

        if (pageDto.getSearch() == null || "".equals(pageDto.getSearch())){
            List<Dormitory> result = dormitoryService.list();
            PageInfo<Dormitory> pageInfo = new PageInfo<>(result);
            return Response.OK(pageInfo);
        }

        List<Dormitory> result = dormitoryService.findPage(pageDto.getSearch());

        PageInfo<Dormitory> pageInfo = new PageInfo<>(result);

        return Response.OK(pageInfo);
    }
    /*
    添加
     */
    @PostMapping("/add")
    public Response findPage(@RequestBody Dormitory dormitory){
        dormitoryService.add(dormitory);
        return Response.OK();
    }
    /*
    删除
     */
    @PostMapping("/del/{id}")
    public Response del(@PathVariable Integer id){
        dormitoryService.del(id);
        return Response.OK();
    }
    @PostMapping("/dels/{ids}")
    public Response dels(@RequestBody List<Integer> ids){
        dormitoryService.dels(ids);
        return Response.OK();
    }
    /*
    excel导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Dormitory> list = dormitoryService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("班级表", "UTF-8");
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
        List<Dormitory> list = reader.readAll(Dormitory.class);
        dormitoryService.saveBatch(list);
        return true;
    }
}

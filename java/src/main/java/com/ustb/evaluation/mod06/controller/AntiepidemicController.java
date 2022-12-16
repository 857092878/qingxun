package com.ustb.evaluation.mod06.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ustb.evaluation.mod06.VO.AntiepidemicVO;
import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.dto.PageDto;
import com.ustb.evaluation.mod06.pojo.Antiepidemic;
import com.ustb.evaluation.mod06.service.IAntiepidemicService;
import com.ustb.evaluation.mod06.utils.BeanCopyUtil;
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
@RequestMapping("/stu/Antiepidemic")
public class AntiepidemicController {
    @Resource
    private IAntiepidemicService antiepidemicService;

    /*
    分页
     */
    @PostMapping("/findPage")
    public Response<PageInfo<AntiepidemicVO>> findPage(@RequestBody PageDto pageDto){

        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());

        if (pageDto.getSearch() == null || "".equals(pageDto.getSearch())){
            List<AntiepidemicVO> result = antiepidemicService.findAll();
            PageInfo<AntiepidemicVO> pageInfo = new PageInfo<>(result);
            return Response.OK(pageInfo);
        }

        List<AntiepidemicVO> result = antiepidemicService.findPage(pageDto.getSearch());

        PageInfo<AntiepidemicVO> pageInfo = new PageInfo<>(result);

        return Response.OK(pageInfo);
    }
    /*
    添加
     */
    @PostMapping("/add")
    public Response findPage(@RequestBody AntiepidemicVO antiepidemicVO){
        antiepidemicService.add(antiepidemicVO);
        return Response.OK();
    }
    /*
    删除
     */
    @PostMapping("/del/{id}")
    public Response del(@PathVariable Integer id){
        antiepidemicService.del(id);
        return Response.OK();
    }
    @PostMapping("/dels/{ids}")
    public Response dels(@RequestBody List<Integer> ids){
        antiepidemicService.dels(ids);
        return Response.OK();
    }
    /*
    excel导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<AntiepidemicVO> list = antiepidemicService.findAll();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生防疫信息表", "UTF-8");
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
        List<AntiepidemicVO> list = reader.readAll(AntiepidemicVO.class);

        List<Antiepidemic> antiepidemics = BeanCopyUtil.copyList(list, Antiepidemic.class);

        antiepidemicService.saveBatch(antiepidemics);
        return true;
    }


}


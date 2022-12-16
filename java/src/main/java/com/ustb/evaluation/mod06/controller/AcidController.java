package com.ustb.evaluation.mod06.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ustb.evaluation.mod06.VO.AcidVO;
import com.ustb.evaluation.mod06.content.Response;
import com.ustb.evaluation.mod06.dto.PageDto;
import com.ustb.evaluation.mod06.pojo.Acid;
import com.ustb.evaluation.mod06.service.IAcidService;
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
@RequestMapping("/stu/Acid")
public class AcidController {

    @Resource
    private IAcidService acidService;

    /*
    分页
     */
    @PostMapping("/findPage")
    public Response<PageInfo<AcidVO>> findPage(@RequestBody PageDto pageDto){

        PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize());

        if (pageDto.getSearch() == null || "".equals(pageDto.getSearch())){
            List<AcidVO> result = acidService.findAll();
            PageInfo<AcidVO> pageInfo = new PageInfo<>(result);
            return Response.OK(pageInfo);
        }

        List<AcidVO> result = acidService.findPage(pageDto.getSearch());

        PageInfo<AcidVO> pageInfo = new PageInfo<>(result);

        return Response.OK(pageInfo);
    }
    /*
    添加
     */
    @PostMapping("/add")
    public Response findPage(@RequestBody AcidVO acidVO){
        acidService.add(acidVO);
        return Response.OK();
    }
    /*
    删除
     */
    @PostMapping("/del/{id}")
    public Response del(@PathVariable Integer id){
        acidService.del(id);
        return Response.OK();
    }
    @PostMapping("/dels/{ids}")
    public Response dels(@RequestBody List<Integer> ids){
        acidService.dels(ids);
        return Response.OK();
    }
    /*
    excel导出
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<AcidVO> list = acidService.findAll();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("基本信息表", "UTF-8");
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
        List<AcidVO> list = reader.readAll(AcidVO.class);
        List<Acid> acids = BeanCopyUtil.copyList(list, Acid.class);
        acidService.saveBatch(acids);
        return true;
    }

}

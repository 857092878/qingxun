package com.ustb.evaluation.mod05test.domain.StudentTreeshow;

import com.ustb.evaluation.mod01common.domain.query.QueryField;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.TableConstant;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshow;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentTreeshowConstant extends TableConstant<StudentTreeshow> {
    public StudentTreeshowConstant() {
        super();
    }

    @Override
    public void initFirtSevenData() {
        selectFields = "select a.id id,a.fcode fcode,a.fname fname,a.fmajor fmajor,a.memo memo";
        selectFrom = " from student_treeshow a";
        selectFieldsSimple = selectFields;
        selectFromSimple = selectFrom;
        selectFieldsComplex = selectFields;
        selectFromComplex = selectFrom;
        tableName = "student_treeshow";//表单名
        hasSafe = false;
        defaultOrders.add(new QueryOrder("fmajor", true));
    }

    @Override
    public void initQueryFields() {
        List<QueryField> ls = safeQueryFields;
        ls.add(new QueryField("id", "a.id", "id", "", ""));
        ls.add(new QueryField("fcode", "a.fcode", "部门编码", "'", "'"));
        ls.add(new QueryField("fname", "a.fname", "部门名称", "'", "'"));
        ls.add(new QueryField("fmajor", "a.fmajor", "专业", "", ""));
        ls.add(new QueryField("memo", "a.memo", "备注", "'", "'"));
        queryFields = ls;
    }

    @Override
    public void initInsertColumns() {
        List<String> ls = new ArrayList<>();
        ls.add("fcode");
        ls.add("fname");
        ls.add("fmajor");
        ls.add("memo");
        insertColumns = ls;
    }

    @Override
    public void initInsertValues() {
        List<String> ls = new ArrayList<>();
        ls.add("#{object.fcode}");
        ls.add("#{object.fname}");
        ls.add("#{object.fmajor}");
        ls.add("#{object.memo}");
        insertValues = ls;
    }

    @Override
    public void initUpdateContent() {
        List<String> ls = new ArrayList<>();
        ls.add("fcode = #{object.fcode}");
        ls.add("fname = #{object.fname}");
        ls.add("fmajor = #{object.fmajor}");
        ls.add("memo = #{object.memo}");
        updateContent = ls;
    }

}


package com.ustb.evaluation.mod05test.domain.TstPageshow;

import com.ustb.evaluation.mod01common.domain.query.QueryField;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.TableConstant;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TstPageshowConstant  extends TableConstant<TstPageshow> {
public TstPageshowConstant() {
 super();
 }

@Override
public void initFirtSevenData() {
selectFields = "select a.id id,a.deptId deptId,b.fname deptId_fname,b.fcode deptId_fcode,a.fname fname,a.fgender fgender,a.fage fage,a.fnation fnation,c.fname fnation_fname,a.memo memo";
selectFrom = " from tst_pageshow a left outer join tst_treeshow b on a.deptId = b.id left outer join tst_allshow c on a.fnation = c.id";
selectFieldsSimple = selectFields;
selectFromSimple = selectFrom;
selectFieldsComplex = selectFields;
selectFromComplex = selectFrom;
tableName = "tst_pageshow";//表单名
 hasSafe = false;
}

@Override
public void initQueryFields() {
List<QueryField> ls = safeQueryFields;
ls.add(new QueryField("id", "a.id", "id", "", ""));
ls.add(new QueryField("deptId", "a.deptId", "部门Id", "", ""));
ls.add(new QueryField("deptId_fname", "b.fname", "部门名称", "'", "'"));
ls.add(new QueryField("deptId_fcode", "b.fcode", "部门编码", "'", "'"));
ls.add(new QueryField("fname", "a.fname", "姓名", "'", "'"));
ls.add(new QueryField("fgender", "a.fgender", "性别", "'", "'"));
ls.add(new QueryField("fage", "a.fage", "年龄", "", ""));
ls.add(new QueryField("fnation", "a.fnation", "民族", "", ""));
ls.add(new QueryField("fnation_fname", "c.fname", "民族名称", "'", "'"));
ls.add(new QueryField("memo", "a.memo", "备注", "'", "'"));
queryFields = ls;
}

@Override
public void initInsertColumns() {
List<String> ls = new ArrayList<>();
ls.add("deptId");
ls.add("fname");
ls.add("fgender");
ls.add("fage");
ls.add("fnation");
ls.add("memo");
insertColumns = ls;
}

@Override
public void initInsertValues() {
List<String> ls = new ArrayList<>();
ls.add("#{object.deptId}");
ls.add("#{object.fname}");
ls.add("#{object.fgender}");
ls.add("#{object.fage}");
ls.add("#{object.fnation}");
ls.add("#{object.memo}");
insertValues = ls;
}

@Override
public void initUpdateContent() {
List<String> ls = new ArrayList<>();
ls.add("deptId = #{object.deptId}");
ls.add("fname = #{object.fname}");
ls.add("fgender = #{object.fgender}");
ls.add("fage = #{object.fage}");
ls.add("fnation = #{object.fnation}");
ls.add("memo = #{object.memo}");
updateContent = ls;
}

}


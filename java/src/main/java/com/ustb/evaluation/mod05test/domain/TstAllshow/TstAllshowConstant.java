package com.ustb.evaluation.mod05test.domain.TstAllshow;

import com.ustb.evaluation.mod01common.domain.query.QueryField;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.TableConstant;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TstAllshowConstant  extends TableConstant<TstAllshow> {
public TstAllshowConstant() {
 super();
 }

@Override
public void initFirtSevenData() {
selectFields = "select a.id id,a.fcode fcode,a.fname fname,a.fsort fsort,a.memo memo";
selectFrom = " from tst_allshow a";
selectFieldsSimple = selectFields;
selectFromSimple = selectFrom;
selectFieldsComplex = selectFields;
selectFromComplex = selectFrom;
tableName = "tst_allshow";//表单名
 hasSafe = false;
defaultOrders.add(new QueryOrder("fsort", true));
}

@Override
public void initQueryFields() {
List<QueryField> ls = safeQueryFields;
ls.add(new QueryField("id", "a.id", "id", "", ""));
ls.add(new QueryField("fcode", "a.fcode", "民族编码", "'", "'"));
ls.add(new QueryField("fname", "a.fname", "民族名称", "'", "'"));
ls.add(new QueryField("fsort", "a.fsort", "排序数值", "", ""));
ls.add(new QueryField("memo", "a.memo", "备注", "'", "'"));
queryFields = ls;
}

@Override
public void initInsertColumns() {
List<String> ls = new ArrayList<>();
ls.add("fcode");
ls.add("fname");
ls.add("fsort");
ls.add("memo");
insertColumns = ls;
}

@Override
public void initInsertValues() {
List<String> ls = new ArrayList<>();
ls.add("#{object.fcode}");
ls.add("#{object.fname}");
ls.add("#{object.fsort}");
ls.add("#{object.memo}");
insertValues = ls;
}

@Override
public void initUpdateContent() {
List<String> ls = new ArrayList<>();
ls.add("fcode = #{object.fcode}");
ls.add("fname = #{object.fname}");
ls.add("fsort = #{object.fsort}");
ls.add("memo = #{object.memo}");
updateContent = ls;
}

}


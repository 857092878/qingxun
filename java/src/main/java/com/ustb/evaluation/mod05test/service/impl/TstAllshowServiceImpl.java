package com.ustb.evaluation.mod05test.service.impl;

import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.utils.dictionary.PairedDataConvert;
import com.ustb.evaluation.mod01common.domain.query.QueryConditionsFlexible;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.QuerySingle;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod01common.service.CurdService;
import com.ustb.evaluation.mod01common.service.impl.CurdServiceImpl;
import com.ustb.evaluation.mod05test.domain.TstAllshow.TstAllshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
 public class TstAllshowServiceImpl extends CurdServiceImpl<TstAllshow> {

@Override
protected void insertUpdateCheck(TstAllshow object, Boolean update) {
List<QuerySingle> list;
String fldNameTip = "";
list = new ArrayList<>();
if (update) {
list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
}
list.add(new QuerySingle(true, "and", "fcode", "=", object.getFcode()));
fldNameTip="民族编码-"+object.getFcode();
if (isExisted(list)) {
throw new PromptException("存在重复数据："+ fldNameTip);
}

list = new ArrayList<>();
if (update) {
list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
}
list.add(new QuerySingle(true, "and", "fname", "=", object.getFname()));
fldNameTip="民族名称-"+object.getFname();
if (isExisted(list)) {
throw new PromptException("存在重复数据："+ fldNameTip);
}

}

@Override
protected void deleteCheck(Long id) {
}

@Override
public List<PairedData> findChoice() {
String sql = "select id,fname from tst_allshow";// order by sortNumber";
List<TstAllshow> ls = selectBySQL(sql);
Integer len = ls.size();
if (len < 1) return null;
List<PairedData> lspd = new ArrayList<>();
for (int i = 0; i < len; i++) {
lspd.add(new PairedData(ls.get(i).getId().toString(), ls.get(i).getFname()));
}
return lspd;
}

}


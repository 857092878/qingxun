package com.ustb.evaluation.mod05test.service.impl;

import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.utils.dictionary.PairedDataConvert;
import com.ustb.evaluation.mod01common.domain.query.QueryConditionsFlexible;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.QuerySingle;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod01common.service.CurdService;
import com.ustb.evaluation.mod01common.service.impl.CurdServiceImpl;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshow;
import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshowTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
 public class TstTreeshowServiceImpl extends CurdServiceImpl<TstTreeshow> {

@Override
protected void insertUpdateCheck(TstTreeshow object, Boolean update) {
List<QuerySingle> list;
String fldNameTip = "";
list = new ArrayList<>();
if (update) {
list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
}
list.add(new QuerySingle(true, "and", "fcode", "=", object.getFcode()));
fldNameTip="部门编码-"+object.getFcode();
if (isExisted(list)) {
throw new PromptException("存在重复数据："+ fldNameTip);
}

list = new ArrayList<>();
if (update) {
list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
}
list.add(new QuerySingle(true, "and", "fname", "=", object.getFname()));
fldNameTip="部门名称-"+object.getFname();
if (isExisted(list)) {
throw new PromptException("存在重复数据："+ fldNameTip);
}

}

@Override
protected void deleteCheck(Long id) {
}

@Override
public List<PairedData> findChoice() {
String sql = "select id,fname from tst_treeshow";// order by sortNumber";
List<TstTreeshow> ls = selectBySQL(sql);
Integer len = ls.size();
if (len < 1) return null;
List<PairedData> lspd = new ArrayList<>();
for (int i = 0; i < len; i++) {
lspd.add(new PairedData(ls.get(i).getId().toString(), ls.get(i).getFname()));
}
return lspd;
}

public List<TstTreeshowTree> findTree() {
QueryConditionsFlexible queryConditionsMore = new QueryConditionsFlexible();
List<QuerySingle> lsquery = new ArrayList<>();
queryConditionsMore.setDirect(false);
queryConditionsMore.setListCondition(lsquery);
List<QueryOrder> lsorder = new ArrayList<>();
lsorder.add(new QueryOrder("fcode", true));
queryConditionsMore.setListOrder(lsorder);
List<TstTreeshow> lsTree = find(queryConditionsMore);
List<TstTreeshow> lsTop = new ArrayList<>();
String code = "";
for (int i = 0; i < lsTree.size(); i++) {
code = lsTree.get(i).getFcode();
if (code.length() == 2) {
lsTop.add(lsTree.get(i));
}
}
List<TstTreeshowTree> ls = new ArrayList<>();
for (int i = 0; i < lsTop.size(); i++) {
TstTreeshow obj = lsTop.get(i);
TstTreeshowTree objTree = new TstTreeshowTree(obj.getId(),obj.getFcode(),obj.getFname(),obj.getFsort(),obj.getMemo());
;findTreeChildren(objTree, lsTree);
ls.add(objTree);
}
return ls;
}

private void findTreeChildren(TstTreeshowTree nodeNow, List<TstTreeshow> ls) {
String codeNow = nodeNow.getFcode();
int lenNow = codeNow.length();
int len;
String code, codePrefix;
TstTreeshowTree objTree;
TstTreeshow obj;
for (int i = 0; i < ls.size(); i++) {
obj = ls.get(i);
code = obj.getFcode();
len = code.length();
if (len == (lenNow + 2)) {
codePrefix = code.substring(0, lenNow);
if (codePrefix.equals(codeNow)) {
objTree = new TstTreeshowTree(obj.getId(),obj.getFcode(),obj.getFname(),obj.getFsort(),obj.getMemo());
;nodeNow.getChildren().add(objTree);
findTreeChildren(objTree, ls);
}
}
}
}

}


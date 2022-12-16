package com.ustb.evaluation.mod05test.service.impl;

import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.domain.query.QuerySingle;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod01common.service.impl.CurdServiceImpl;
import com.ustb.evaluation.mod05test.domain.TstPageshow.TstPageshow;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
 public class TstPageshowServiceImpl extends CurdServiceImpl<TstPageshow> {

@Override
protected void insertUpdateCheck(TstPageshow object, Boolean update) {
List<QuerySingle> list;
String fldNameTip = "";
list = new ArrayList<>();
if (update) {
list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
}
list.add(new QuerySingle(true, "and", "fname", "=", object.getFname()));
fldNameTip="姓名-"+object.getFname();
if (isExisted(list)) {
throw new PromptException("存在重复数据："+ fldNameTip);
}

}

@Override
protected void deleteCheck(Long id) {
}

public List<PairedData> findFgender() {
List<PairedData> ls = new ArrayList<>();
ls.add(new PairedData("男", "男"));
ls.add(new PairedData("女", "女"));
return ls;
}

}


package com.ustb.evaluation.mod05test.service.impl;

import com.ustb.evaluation.mod01common.domain.dictionary.PairedData;
import com.ustb.evaluation.mod01common.domain.query.QueryConditionsFlexible;
import com.ustb.evaluation.mod01common.domain.query.QueryOrder;
import com.ustb.evaluation.mod01common.domain.query.QuerySingle;
import com.ustb.evaluation.mod01common.exception.PromptException;
import com.ustb.evaluation.mod01common.service.impl.CurdServiceImpl;
import com.ustb.evaluation.mod05test.domain.StudentTreeshow.StudentTreeshow;
import com.ustb.evaluation.mod05test.domain.StudentTreeshow.StudentTreeshowTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTreeshowServiceImpl extends CurdServiceImpl<StudentTreeshow> {

    @Override
    protected void insertUpdateCheck(StudentTreeshow object, Boolean update) {
        List<QuerySingle> list;
        String fldNameTip = "";
        list = new ArrayList<>();
        if (update) {
            list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
        }
        list.add(new QuerySingle(true, "and", "fcode", "=", object.getFcode()));
        fldNameTip = "部门编码-" + object.getFcode();
        if (isExisted(list)) {
            throw new PromptException("存在重复数据：" + fldNameTip);
        }

        list = new ArrayList<>();
        if (update) {
            list.add(new QuerySingle(true, "and", "id", "<>", object.getId().toString()));
        }
        list.add(new QuerySingle(true, "and", "fname", "=", object.getFname()));
        fldNameTip = "部门名称-" + object.getFname();
        if (isExisted(list)) {
            throw new PromptException("存在重复数据：" + fldNameTip);
        }

    }

    @Override
    protected void deleteCheck(Long id) {
    }

    @Override
    public List<PairedData> findChoice() {
        String sql = "select id,fname from student_treeshow";// order by sortNumber";
        List<StudentTreeshow> ls = selectBySQL(sql);
        Integer len = ls.size();
        if (len < 1) return null;
        List<PairedData> lspd = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            lspd.add(new PairedData(ls.get(i).getId().toString(), ls.get(i).getFname()));
        }
        return lspd;
    }

    public List<StudentTreeshowTree> findTree() {
        QueryConditionsFlexible queryConditionsMore = new QueryConditionsFlexible();
        List<QuerySingle> lsquery = new ArrayList<>();
        queryConditionsMore.setDirect(false);
        queryConditionsMore.setListCondition(lsquery);
        List<QueryOrder> lsorder = new ArrayList<>();
        lsorder.add(new QueryOrder("fcode", true));
        queryConditionsMore.setListOrder(lsorder);
        List<StudentTreeshow> lsTree = find(queryConditionsMore);
        List<StudentTreeshow> lsTop = new ArrayList<>();
        String code = "";
        for (int i = 0; i < lsTree.size(); i++) {
            code = lsTree.get(i).getFcode();
            if (code.length() == 2) {
                lsTop.add(lsTree.get(i));
            }
        }
        List<StudentTreeshowTree> ls = new ArrayList<>();
        for (int i = 0; i < lsTop.size(); i++) {
            StudentTreeshow obj = lsTop.get(i);
            StudentTreeshowTree objTree = new StudentTreeshowTree(obj.getId(), obj.getFcode(), obj.getFname(), obj.getFmajor(), obj.getMemo());
            ;
            findTreeChildren(objTree, lsTree);
            ls.add(objTree);
        }
        return ls;
    }

    private void findTreeChildren(StudentTreeshowTree nodeNow, List<StudentTreeshow> ls) {
        String codeNow = nodeNow.getFcode();
        int lenNow = codeNow.length();
        int len;
        String code, codePrefix;
        StudentTreeshowTree objTree;
        StudentTreeshow obj;
        for (int i = 0; i < ls.size(); i++) {
            obj = ls.get(i);
            code = obj.getFcode();
            len = code.length();
            if (len == (lenNow + 2)) {
                codePrefix = code.substring(0, lenNow);
                if (codePrefix.equals(codeNow)) {
                    objTree = new StudentTreeshowTree(obj.getId(), obj.getFcode(), obj.getFname(), obj.getFmajor(), obj.getMemo());
                    ;
                    nodeNow.getChildren().add(objTree);
                    findTreeChildren(objTree, ls);
                }
            }
        }
    }

}


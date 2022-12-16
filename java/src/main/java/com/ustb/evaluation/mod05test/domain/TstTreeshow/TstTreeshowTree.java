package com.ustb.evaluation.mod05test.domain.TstTreeshow;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class TstTreeshowTree extends TstTreeshow {

private List<TstTreeshowTree> children;
public TstTreeshowTree(Long id,String fcode,String fname,Long fsort,String memo){
this.setId(id);
this.setFcode(fcode);
this.setFname(fname);
this.setFsort(fsort);
this.setMemo(memo);
this.setChildren(new ArrayList<>());
}
}

package com.ustb.evaluation.mod05test.domain.StudentTreeshow;

import com.ustb.evaluation.mod05test.domain.TstTreeshow.TstTreeshow;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentTreeshowTree extends StudentTreeshow {

    private List<StudentTreeshowTree> children;

    public StudentTreeshowTree(Long id, String fcode, String fname, Long fmajor, String memo) {
        this.setId(id);
        this.setFcode(fcode);
        this.setFname(fname);
        this.setFmajor(fmajor);
        this.setMemo(memo);
        this.setChildren(new ArrayList<>());
    }
}

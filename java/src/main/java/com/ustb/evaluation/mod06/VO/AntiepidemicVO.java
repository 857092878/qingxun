package com.ustb.evaluation.mod06.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 蒲锐
 * @since 2022-12-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AntiepidemicVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Long fstudentId;
    private String finCampus;
    private String flocation;
    private Date fleaveTime;
    private Long fhealthId;
    private String fhealthMemo;
    private Long fstatusId;
    private String fvaccinCompleted;
    private String fvaccinMemo;
    private String fjourney;
    private String memo;
    private String fcode;
    private String fname;
    private String fstatusName;
    private String fhealth;


}

package com.ustb.evaluation.mod06.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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
public class AccommodationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 学生id
     */
    private Long fstudentId;

    /**
     * 宿舍id
     */
    private Long fdormitoryId;

    /**
     * 床位
     */
    private Integer fbed;

    /**
     * 是否在宿舍
     */
    private String fin;

    /**
     * 备注
     */
    private String memo;

    private String fcode;

    private String fname;

    private String fcode1;




}

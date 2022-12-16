package com.ustb.evaluation.mod06.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class AcidVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Long fstudentId;

    /**
     * 采样时间
     */
    private Date fsampleTime;

    /**
     * 检测时间
     */
    private Date ftestTime;

    /**
     * 检测单位
     */
    private String ftestUnit;

    /**
     * 检测结果
     */
    private String fresult;

    /**
     * 采样地点
     */
    private String ftestAddress;

    /**
     * 备注
     */
    private String memo;


    private String fcode;

    private String fname;


}

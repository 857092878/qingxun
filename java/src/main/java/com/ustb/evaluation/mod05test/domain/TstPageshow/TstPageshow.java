package com.ustb.evaluation.mod05test.domain.TstPageshow;

import com.ustb.evaluation.mod01common.domain.field.FieldsBasic;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TstPageshow  extends FieldsBasic {

@NotNull(message = "部门Id不能为空！")
private Long deptId;

private String deptId_fname;

private String deptId_fcode;

@NotNull(message = "姓名不能为空！")
@Size(max = 20, message = "姓名的最大长度为20个字符！")
private String fname;

@NotNull(message = "性别不能为空！")
@Size(min= 1,max = 1, message = "性别的长度必须为1个字符！")
private String fgender;

private String fage;

@NotNull(message = "民族不能为空！")
private Long fnation;

private String fnation_fname;

}

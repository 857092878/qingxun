package com.ustb.evaluation.mod05test.domain.TstAllshow;

import com.ustb.evaluation.mod01common.domain.field.FieldsBasic;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TstAllshow  extends FieldsBasic {

@NotNull(message = "民族编码不能为空！")
@Size(min= 2,max = 2, message = "民族编码的长度必须为2个字符！")
private String fcode;

@NotNull(message = "民族名称不能为空！")
@Size(max = 20, message = "民族名称的最大长度为20个字符！")
private String fname;

@NotNull(message = "排序数值不能为空！")
private Long fsort;

}

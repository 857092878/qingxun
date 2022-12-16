package com.ustb.evaluation.mod05test.domain.StudentTreeshow;

import com.ustb.evaluation.mod01common.domain.field.FieldsBasic;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StudentTreeshow extends FieldsBasic {

@NotNull(message = "部门编码不能为空！")
@Size(max = 20, message = "部门编码的最大长度为20个字符！")
private String fcode;

@NotNull(message = "部门名称不能为空！")
@Size(max = 20, message = "部门名称的最大长度为20个字符！")
private String fname;

@NotNull(message = "排序数值不能为空！")
private Long fmajor;

}

package com.ustb.evaluation.mod06.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author 蒲锐
 * @CreateTme 2022/12/14 17:04
 * Version1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDto {

    private Integer pageNum;

    private Integer pageSize;

    private String search;
}

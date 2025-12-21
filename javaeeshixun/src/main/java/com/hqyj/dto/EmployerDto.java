package com.hqyj.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EmployerDto {
    private Integer empId;
    private String empName;
    private String empSex;
    private Integer empAge;
    private String empDepartment;
    private String empJob;
    private Integer page=1;
    private Integer row=20;

}

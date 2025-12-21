package com.hqyj.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author rock
 * @since 2025-12-16 03:59:23
 */
@Getter
@Setter
@TableName("employer")
public class Employer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    @TableField("emp_name")
    private String empName;

    @TableField("emp_sex")
    private String empSex;

    @TableField("emp_age")
    private Integer empAge;

    @TableField("emp_department")
    private String empDepartment;

    @TableField("emp_job")
    private String empJob;
    @TableLogic
    private Integer deleteTag;
}

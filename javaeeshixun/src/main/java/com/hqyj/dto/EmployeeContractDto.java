package com.hqyj.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeContractDto {
    private Integer page=1;
    private Integer row=20;
    private Integer contractId;
    private Integer employeeId;
    private String employeeName;
    private String position;
    private String contractContent;
    private String remark;
    private Date startDate;
    private Date endDate;
    private Integer isDeleted;
    private Integer date;
}

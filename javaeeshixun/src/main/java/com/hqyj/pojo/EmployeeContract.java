package com.hqyj.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author rock
 * @since 2025-12-18 08:38:59
 */
@Getter
@Setter
@TableName("employee_contract")
public class EmployeeContract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合同编号（唯一标识，如：HT2025001）
     */
    @TableId(value = "contract_id", type = IdType.AUTO)
    private Integer contractId;

    /**
     * 关联员工编号
     */
    @TableField("employee_id")
    private Integer employeeId;

    /**
     * 合同开始日期
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 合同结束日期
     */
    @TableField("end_date")
    private Date endDate;

    /**
     * 合同约定职务
     */
    @TableField("position")
    private String position;

    /**
     * 合同核心内容
     */
    @TableField("contract_content")
    private String contractContent;

    /**
     * 合同备注（如：续签、试用期等）
     */
    @TableField("remark")
    private String remark;

    /**
     * 逻辑删除标记（0=未删，1=已删）
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @TableField("employee_name")
    private String employeeName;

}

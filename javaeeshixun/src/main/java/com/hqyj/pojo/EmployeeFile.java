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
 * @since 2025-12-18 02:59:36
 */
@Getter
@Setter
@TableName("employee_file")
public class EmployeeFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案编号（主键）
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 员工编号（关联employee表）
     */
    @TableField("employee_id")
    private Integer employeeId;

    /**
     * 档案名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 内容摘要
     */
    @TableField("content_summary")
    private String contentSummary;

    /**
     * 备注
     */
    @TableField("employee_name")
    private String employeeName;

    /**
     * 档案分类（如：合同/考勤/绩效）
     */
    @TableField("archive_type")
    private String archiveType;

    /**
     * 逻辑删除（0-未删，1-已删）
     */
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;


}

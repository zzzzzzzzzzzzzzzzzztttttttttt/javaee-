package com.hqyj.dto;

import lombok.Data;

@Data
public class EmployeeFileDto {
    private Integer page=1;
    private Integer row=20;
    private Integer fileId;
    private Integer employeeId;
    private String fileName;
    private String contentSummary;
    private String employeeName;
    private String archiveType;
    private Integer isDelete;
}

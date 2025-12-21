package com.hqyj.controller;



import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.pojo.EmployeeFile;
import com.hqyj.service.EmployeeFileService;

import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-18 02:59:36
 */
@RestController
@RequestMapping("/employee_file")
public class EmployeeFileController {
    @Autowired
    private EmployeeFileService employeeFileService;
    @PostMapping("/add")
    public ResultVo add(@RequestBody EmployeeFile emp){
        return employeeFileService.add(emp);
    }
    @PutMapping("/update")
    public ResultVo update(@RequestBody EmployeeFile emp){
        return employeeFileService.update(emp);
    }
    @DeleteMapping("/del")
    public ResultVo delete(@RequestParam Integer id){
        return employeeFileService.delete(id);
    }
    @GetMapping("/query")
    public ResultVo query( EmployeeFileDto dto){
        return employeeFileService.queryByCondition(dto);
    }
}


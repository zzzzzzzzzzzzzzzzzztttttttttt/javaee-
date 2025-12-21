package com.hqyj.controller;


import com.hqyj.dto.EmployeeContractDto;
import com.hqyj.dto.EmployeeFileDto;
import com.hqyj.pojo.EmployeeContract;
import com.hqyj.pojo.EmployeeFile;
import com.hqyj.service.EmployeeContractService;
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
 * @since 2025-12-18 08:38:59
 */
@RestController
@RequestMapping("/employee_contract")
public class EmployeeContractController {
    @Autowired
    private EmployeeContractService employeeContractService;
    @PostMapping("/add")
    public ResultVo add(@RequestBody EmployeeContract emp){
        return employeeContractService.add(emp);
    }
    @PutMapping("/update")
    public ResultVo update(@RequestBody EmployeeContract emp){
        return employeeContractService.update(emp);
    }
    @DeleteMapping("/del")
    public ResultVo delete(@RequestParam Integer id){
        return employeeContractService.delete(id);
    }
    @GetMapping("/query")
    public ResultVo query( EmployeeContractDto dto){
        return employeeContractService.queryByCondition(dto);
    }

}


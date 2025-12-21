package com.hqyj.controller;


import com.hqyj.dto.EmployerDto;
import com.hqyj.pojo.Employer;
import com.hqyj.service.EmployerService;
import com.hqyj.service.Impl.EmployerServiceImpl;
import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-16 03:59:23
 */
@RestController
@RequestMapping("/employer")
public class EmployerController {
    @Autowired
    private EmployerService employerService;
    @PostMapping("/add")
    public ResultVo add(@RequestBody Employer emp){
        return employerService.add(emp);
    }
    @PutMapping("/update")
    public ResultVo update(@RequestBody Employer emp){
        return employerService.update(emp);
    }
    @DeleteMapping("/del")
    public ResultVo delete(@RequestParam Integer id){
        return employerService.delete(id);
    }
    @GetMapping("/query")
    public ResultVo query( EmployerDto dto){
        return employerService.queryByCondition(dto);
    }
    @GetMapping("/queryAge")
    public ResultVo queryAge(){
        return employerService.queryAge();
    }

}


package com.ihr.company.controller;

import com.ihr.common.entity.Result;
import com.ihr.common.entity.ReturnCode;
import com.ihr.common.exception.CommonException;
import com.ihr.company.service.CompanyService;
import com.ihr.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value="/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(value="save",method = RequestMethod.POST)
    public Result save(@RequestBody Company company)  {
        //业务操作
        companyService.add(company);
        return new Result(ReturnCode.SUCCESS);
    }

    //根据id更新企业
    /**
     * 1.方法
     * 2.请求参数
     * 3.响应
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id, @RequestBody Company company ) {
        //业务操作
        company.setId(id);
        companyService.update(company);
        return new Result(ReturnCode.SUCCESS);
    }

    //根据id删除企业
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        companyService.deleteById(id);
        return new Result(ReturnCode.SUCCESS);
    }

    //根据id查询企业
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) throws CommonException {
        //throw new CommonException(ReturnCode.UNAUTHORIZE);
        Company company = companyService.findById(id);
        Result result = new Result(ReturnCode.SUCCESS);
        result.setData(company);
        return result;
    }

    //查询全部企业列表
    @RequestMapping(value="list",method = RequestMethod.GET)
    public Result findAll() {
        List<Company> list = companyService.findAll();
        Result result = new Result(ReturnCode.SUCCESS);
        result.setData(list);
        return result;
    }
}

package com.ihr.company.controller;

import com.ihr.common.controller.BaseController;
import com.ihr.common.entity.Result;
import com.ihr.common.entity.ReturnCode;
import com.ihr.company.service.CompanyService;
import com.ihr.company.service.DepartmentService;
import com.ihr.domain.company.Company;
import com.ihr.domain.company.Department;
import com.ihr.domain.company.response.DepartmentListResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/company")   //  company/deparment
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;
    /**
     * 保存
     */
    @RequestMapping(value="/department/save",method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        //1.设置保存的企业id
        /**
         * 企业id：目前使用固定值1，以后会解决
         */
        department.setCompanyId(companyId);
        //2.调用service完成保存企业
        departmentService.save(department);
        //3.构造返回结果
        return new Result(ReturnCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id
     */
    @RequestMapping(value="/department/list",method = RequestMethod.GET)
    public Result findAll(String companyIdSelect) {
        if(StringUtils.isNotEmpty(companyIdSelect)){
            companyId = companyIdSelect;
        }
        //1.指定企业id
        Company company = companyService.findById(companyId);
        //2.完成查询
        List<Department> list = departmentService.findAll(companyId);
        //3.构造返回结果
        DepartmentListResult deptListResult = new DepartmentListResult(company,list);
        return new Result(ReturnCode.SUCCESS,deptListResult);
    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ReturnCode.SUCCESS,department);
    }

    /**
     * 修改Department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id,@RequestBody Department department) {
        //1.设置修改的部门id
        department.setId(id);
        //2.调用service更新
        departmentService.update(department);
        return new Result(ReturnCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        departmentService.deleteById(id);
        return new Result(ReturnCode.SUCCESS);
    }


}

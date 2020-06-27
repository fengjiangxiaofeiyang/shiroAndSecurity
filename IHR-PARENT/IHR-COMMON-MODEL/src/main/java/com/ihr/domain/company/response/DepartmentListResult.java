package com.ihr.domain.company.response;

import com.ihr.domain.company.Company;
import com.ihr.domain.company.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: yangchun
 * @description:
 * @date: Created in 2020-06-15 8:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts;
    public DepartmentListResult(Company company,List<Department> depts){
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getManagerId();
        this.depts = depts;
    }
}

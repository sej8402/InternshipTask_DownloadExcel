package com.downloadExcel.Repository;



import com.downloadExcel.Model.EmployeeExcelDTO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
 
@Repository
public class EmployeeRepository {
 
    private final NamedParameterJdbcTemplate jdbcTemplate;
 
    public EmployeeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    public List<EmployeeExcelDTO> getEmployeeData() {
        String sql = """
            SELECT e.emp_id AS employee_id, e.emp_first_name AS employee_name, e.username,
                   etd.managerId, etd.managerName AS manager_name, mep.project_id, p.project_name,
                   mde.designation_id, d.designation_name, mtp.type_id, t.type_name,
                   GROUP_CONCAT(c.client_brand_name) AS client_nameList
            FROM employee e
            LEFT JOIN employee_transfer_details etd ON e.emp_id = etd.employeeId
            LEFT JOIN mapping_employee_project mep ON e.emp_id = mep.emp_id
            LEFT JOIN project p ON mep.project_id = p.project_id
            LEFT JOIN mapping_designation_employee mde ON e.emp_id = mde.employee_id
            LEFT JOIN designation d ON mde.designation_id = d.designation_id
            LEFT JOIN mapping_type_project mtp ON p.project_id = mtp.project_id
            LEFT JOIN type t ON mtp.type_id = t.type_id
            LEFT JOIN mapping_client_project mcp ON p.project_id = mcp.project_id
            LEFT JOIN client c ON mcp.client_id = c.client_id
            GROUP BY e.emp_id, e.emp_first_name, e.username, etd.managerId, etd.managerName,
                     mep.project_id, p.project_name, mde.designation_id, d.designation_name,
                     mtp.type_id, t.type_name
        """;
 
        return jdbcTemplate.query(sql, Map.of(), new RowMapper<EmployeeExcelDTO>() {
            @Override
            public EmployeeExcelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	EmployeeExcelDTO employeeExcelDto = new EmployeeExcelDTO();
            	employeeExcelDto.setEmployeeId(rs.getString("employee_id"));
            	employeeExcelDto.setEmployeeName(rs.getString("employee_name"));
            	employeeExcelDto.setUsername(rs.getString("username"));
            	employeeExcelDto.setManagerId(rs.getString("managerId"));
            	employeeExcelDto.setManagerName(rs.getString("manager_name"));
            	employeeExcelDto.setProjectId(rs.getString("project_id"));
            	employeeExcelDto.setProjectName(rs.getString("project_name"));
            	employeeExcelDto.setDesignationId(rs.getString("designation_id"));
            	employeeExcelDto.setDesignationName(rs.getString("designation_name"));
            	employeeExcelDto.setTypeId(rs.getString("type_id"));
            	employeeExcelDto.setTypeName(rs.getString("type_name"));
            	employeeExcelDto.setClientNameList(rs.getString("client_nameList"));
                return employeeExcelDto;
            }
        });
    }
}
 
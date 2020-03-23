package com.ability.model;

import com.ability.dao.Db;
import com.ability.dao.DeptDao;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@ManagedBean
@ViewScoped
public class Dept {
    
    private String deptid;
    private String deptnme;
    private String cmpy;
    private String cmpdept;
    private List<Dept> deptList = new ArrayList<Dept>();
    private Integer sno;
    
    public List<Dept> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Dept> deptList) {
        this.deptList = deptList;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptnme() {
        return deptnme;
    }

    public void setDeptnme(String deptnme) {
        this.deptnme = deptnme;
    }

    public String getCmpy() {
        return cmpy;
    }

    public void setCmpy(String cmpy) {
        this.cmpy = cmpy;
    }

    public String getCmpdept() {
        return cmpdept;
    }

    public void setCmpdept(String cmpdept) {
        this.cmpdept = cmpdept;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }
    
    
    //*********** database related code ***************
    private DataSource dataSource;
    private SimpleJdbcCall jdbcCall = null;
    

    public SQLServerDataTable getDEPTTableStructure() throws SQLServerException {

        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        sourceDataTable.addColumnMetadata("DEPTID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("DEPTNME", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
           
       
        sourceDataTable.addRow(deptid, deptnme, cmpy,cmpdept);  
        
        return sourceDataTable;
    }
    
     @PostConstruct
        public void init( ){
            this.deptid = "";
            this.deptnme = "";
            this.cmpy="";
            this.cmpdept="";
        }
        
    public Dept(){
        this.deptid = "";
        this.deptnme = "";
        this.cmpy="";
        this.cmpdept="";
    }
        
     public List<Dept> crud(SQLServerDataTable sourceDataTable, Para.DEPT para) throws SQLException {

        List<Dept> deptList = new ArrayList<Dept>();

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept dep = new Dept();
                            
                            dep.setDeptid(rs.getString("DEPTID"));
                            dep.setDeptnme(rs.getString("DEPTNME"));
                            dep.setCmpy(rs.getString("CMPY"));
                            dep.setCmpdept(rs.getString("CMPDEPT"));

                            return dep;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpDEPT", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCrdn = jdbcCall.execute(pr);
            deptList = (List<Dept>) mapCrdn.get("resultSet");
            String result = (String) mapCrdn.get("result");
            GrowlView.saveMessage(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

        return deptList;

    }
     
    public List<Dept> GetCombo(SQLServerDataTable sourceDataTable, Para.DEPT para) throws SQLException {

        List<Dept> deptList = new ArrayList<Dept>();

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept dep = new Dept();
                            
                            dep.setDeptid(rs.getString("DEPTID"));
                            dep.setDeptnme(rs.getString("DEPTNME"));

                            return dep;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpDEPT", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCrdn = jdbcCall.execute(pr);
            deptList = (List<Dept>) mapCrdn.get("resultSet");
            String result = (String) mapCrdn.get("result");
            GrowlView.saveMessage(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

        return deptList;

    }
    
    public List<Dept> select(){
        this.deptList = new ArrayList<Dept>();
        try {
            this.deptList = GetCombo(this.getDEPTTableStructure(), Para.DEPT.Select_CBO);
        } catch (SQLException ex) {
            Logger.getLogger(Dept.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deptList;
    }
    
}

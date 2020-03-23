package com.ability.model;

import com.ability.dao.Db;
import com.ability.dao.JtypeDao;
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
public class Jtype {
    
    private String jtypecd;
    private String jtypenme;
    private String cmpy;
    private String cmpdept;
    private List<Jtype> jtList = new ArrayList<Jtype>();
    private Integer sno;

    public String getJtypecd() {
        return jtypecd;
    }

    public void setJtypecd(String jtypecd) {
        this.jtypecd = jtypecd;
    }

    public String getJtypenme() {
        return jtypenme;
    }

    public void setJtypenme(String jtypenme) {
        this.jtypenme = jtypenme;
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
    
    
    public SQLServerDataTable getJTYPETableStructure() throws SQLServerException {

        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        sourceDataTable.addColumnMetadata("JTYPECD", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("JTYPENME", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
        
       
        sourceDataTable.addRow(jtypecd, jtypenme,cmpy,cmpdept);  
        
        return sourceDataTable;
    }
    
    @PostConstruct
    public void init( ){
        this.jtypecd = "";
        this.jtypenme = "";
        this.cmpy="";
        this.cmpdept="";
    }
      
    public Jtype( ){
        this.jtypecd = "";
        this.jtypenme = "";
        this.cmpy="";
        this.cmpdept="";
    } 
    
    public List<Jtype> select() throws SQLException{
        try {
            this.jtList = new ArrayList<Jtype>();                     
            this.jtList = crud(this.getJTYPETableStructure(), Para.JTYPE.Select);
        } catch (SQLServerException ex) {
            Logger.getLogger(JtypeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jtList;
    }
    
    public List<Jtype> crud(SQLServerDataTable sourceDataTable, Para.JTYPE para) throws SQLException {

        List<Jtype> jtList = new ArrayList<Jtype>();

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype jt = new Jtype();
                            
                              jt.setJtypecd(rs.getString("JTYPECD"));
                              jt.setJtypenme(rs.getString("JTYPENME"));
                              jt.setCmpy(rs.getString("CMPY"));
                              jt.setCmpdept(rs.getString("CMPDEPT"));

                            return jt;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpJTYPE", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCrdn = jdbcCall.execute(pr);
            jtList = (List<Jtype>) mapCrdn.get("resultSet");
            String result = (String) mapCrdn.get("result");
            GrowlView.saveMessage(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

        return jtList;

    }
    
    //*******************method to fill combo********************
    public List<Jtype> getJtypeCombo() {
  
        try {
            SQLServerDataTable dt = getJTYPETableStructure();
            
            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype jt = new Jtype();
                            
                              jt.setJtypecd(rs.getString("JTYPECD"));
                              jt.setJtypenme(rs.getString("JTYPENME"));
                              jt.setCmpy(rs.getString("CMPY"));
                              jt.setCmpdept(rs.getString("CMPDEPT"));

                            return jt;
                        }
                    });
            
            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpJTYPE", dt);
            pr.addValue("TranType", Para.JTYPE.Select_CBO.toString());
            pr.addValue("result", "");

            Map mapPrdtdn = jdbcCall.execute(pr);
            
            return (List<Jtype>) mapPrdtdn.get("resultSet");
        } catch (SQLServerException ex) {
            Logger.getLogger(Jtype.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
}

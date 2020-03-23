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
public class Apptn {
    
    private String deptid;
    private String appid;
    private String applton;
    private Integer sno;
    private List<Apptn> appList = new ArrayList<Apptn>();

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getApplton() {
        return applton;
    }

    public void setApplton(String applton) {
        this.applton = applton;
    }

    public List<Apptn> getAppList() {
        return appList;
    }

    public void setAppList(List<Apptn> appList) {
        this.appList = appList;
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
    
    public List<Apptn> getAppCombo(String DEPTID) throws SQLException {
        try {
                this.setDeptid(DEPTID);
                DataSource ds = Db.getSQLDataSource();
                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
                        .returningResultSet("resultSet",
                                new ParameterizedBeanPropertyRowMapper<Apptn>() {
                            @Override
                            public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Apptn app = new Apptn();
                            
                                app.setAppid(rs.getString("APPID"));
                                app.setApplton(rs.getString("APPLTON"));
                            
                                return app;
                            }
                        });

                MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpAPPTN", this.getAPPTNTableStructure());
                pr.addValue("TranType", Para.APPTN.Select_CBO.toString());
                pr.addValue("result", "");

                Map mapCt = jdbcCall.execute(pr);
                return (List<Apptn>) mapCt.get("resultSet");
            } catch (SQLServerException ex) {
                GrowlView.saveMessage(ex.toString());
                throw new RuntimeException(ex.toString());
        }
    }
    
    public SQLServerDataTable getAPPTNTableStructure() throws SQLServerException {

        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        sourceDataTable.addColumnMetadata("DEPTID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("APPID", java.sql.Types.VARCHAR);
        sourceDataTable.addColumnMetadata("APPLTON", java.sql.Types.NVARCHAR);
        
       
        sourceDataTable.addRow(deptid, appid,applton);  
        
        return sourceDataTable;
    }

    @PostConstruct
        public void init( ){
        this.deptid = "";
        this.appid = "";
        this.applton = "";
    }

    public Apptn() {
        this.deptid = "";
        this.appid = "";
        this.applton = "";
    }
    
//    public List<Apptn> GetCombo(SQLServerDataTable sourceDataTable, Para.APPTN para) throws SQLException {
//
//        List<Apptn> appList = new ArrayList<Apptn>();
//
//        try {
//            DataSource ds = Db.getSQLDataSource();
//            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
//                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Apptn>() {
//                        @Override
//                        public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            Apptn app = new Apptn();
//                            
//                            app.setAppid(rs.getString("APPID"));
//                            app.setApplton(rs.getString("APPLTON"));
//                            
//                            return app;
//                        }
//                    });
//
//            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpAPPTN", sourceDataTable);
//            pr.addValue("TranType", para.toString());
//            pr.addValue("result", "");
//
//            Map mapCrdn = jdbcCall.execute(pr);
//            appList = (List<Apptn>) mapCrdn.get("resultSet");
//            String result = (String) mapCrdn.get("result");
//            GrowlView.saveMessage(result);
//
//        } catch (Exception ex) {
//            throw new RuntimeException(ex.toString());
//        }
//
//        return appList;
//
//    }
    
    
    
//    public List<Apptn> select(){
//        this.appList = new ArrayList<Apptn>();
//        try {
//            this.appList = getAppCombo(this.getAPPTNTableStructure(),Para.APPTN.Select_CBO,cboDept.getDeptid());
//        } catch (SQLException ex) {
//            Logger.getLogger(Apptn.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return appList;
//    }
    
}

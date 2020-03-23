package com.ability.model;

import com.ability.dao.Db;
import com.ability.dao.UsrDao;
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

public class Usr {
    
    private String uid;
    private String unme;
    private String cmpy;
    private String cmpdept;
    private String pswd;
    private String rk;
    private String mphno;
    private String email;
    private Date pswdcdt;
    private List<Usr> usrList = new ArrayList<Usr>();
    

    public List<Usr> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<Usr> usrList) {
        this.usrList = usrList;
    }
    

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUnme() {
        return unme;
    }

    public void setUnme(String unme) {
        this.unme = unme;
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

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getRk() {
        return rk;
    }

    public void setRk(String rk) {
        this.rk = rk;
    }

    public String getMphno() {
        return mphno;
    }

    public void setMphno(String mphno) {
        this.mphno = mphno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getPswdcdt() {
        return pswdcdt;
    }

    public void setPswdcdt(Date pswdcdt) {
        this.pswdcdt = pswdcdt;
    }
    
    //*********** database related code ***************
    private DataSource dataSource;
    private SimpleJdbcCall jdbcCall = null;
    
    
    public SQLServerDataTable getUSRTableStructure() throws SQLServerException {

        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        sourceDataTable.addColumnMetadata("UID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("UNME", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("PSWD", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("RK", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("MPHNO", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("EMAIL", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("PSWDCDT", java.sql.Types.DATE);
           
       
        sourceDataTable.addRow(uid, unme,  cmpy,  cmpdept,  pswd,  rk,  mphno,  email, pswdcdt);  
        
        return sourceDataTable;
    }
    
    @PostConstruct
    public void init(){
        
        this.uid = "";
        this.unme = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.pswd = "";
        this.rk = "";
        this.mphno = "";
        this.email = "";
        this.pswdcdt=null;
    }

    public Usr() {
        this.uid = "";
        this.unme = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.pswd = "";
        this.rk = "";
        this.mphno = "";
        this.email = "";
        this.pswdcdt=null;
    }
    
    public List<Usr> crud(SQLServerDataTable sourceDataTable, Para.USR para) throws SQLException {

        List<Usr> usrList = new ArrayList<Usr>();

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Usr>() {
                        @Override
                        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usr ur = new Usr();
                            
                            ur.setUid(rs.getString("UID"));
                            ur.setUnme(rs.getString("UNME"));
                            ur.setCmpy(rs.getString("CMPY"));
                            ur.setCmpdept(rs.getString("CMPDEPT"));
                            ur.setRk(rs.getString("RK"));
                            ur.setMphno(rs.getString("MPHNO"));
                            ur.setEmail(rs.getString("EMAIL"));
                            ur.setPswd(rs.getString("PSWD"));
                            ur.setPswdcdt(rs.getDate("PSWDCDT"));

                            return ur;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpUSR", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCrdn = jdbcCall.execute(pr);
            usrList = (List<Usr>) mapCrdn.get("resultSet");
            String result = (String) mapCrdn.get("result");
            GrowlView.saveMessage(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

        return usrList;

    }
    
    //*******************method to fill combo********************
    public List<Usr> getUserCombo() {
  
        try {
            SQLServerDataTable dt = getUSRTableStructure();
            
            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Usr>() {
                        @Override
                        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usr ur = new Usr();
                            
                            ur.setUid(rs.getString("UID"));
                            ur.setUnme(rs.getString("UNME"));
                            ur.setCmpy(rs.getString("CMPY"));
                            ur.setCmpdept(rs.getString("CMPDEPT"));
                            return ur;
                        }
                    });
            
            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpUSR", dt);
            pr.addValue("TranType", Para.USR.Select_CBO.toString());
            pr.addValue("result", "");

            Map mapPrdtdn = jdbcCall.execute(pr);
            
            return (List<Usr>) mapPrdtdn.get("resultSet");
        } catch (SQLServerException ex) {
            Logger.getLogger(Usr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Usr> select() throws SQLException{
        try {
            this.usrList = new ArrayList<Usr>();                     
            this.usrList = crud(this.getUSRTableStructure(), Para.USR.Select);
        } catch (SQLServerException ex) {
            Logger.getLogger(UsrDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usrList;
    }
}

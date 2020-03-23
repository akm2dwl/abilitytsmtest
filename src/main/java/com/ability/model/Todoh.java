package com.ability.model;

import com.ability.dao.Db;
import com.ability.dao.TodohDao;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.ability.util.Util;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sun.corba.se.spi.logging.CORBALogDomains;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
public class Todoh {

    private String mtid;
    private Date edate;
    private String desp;
    private String deptid;
    private String appid;
    private String cmpy;
    private String cmpdept;
    private String jtypecd;
    private Integer eminute;
    private Date tfdate;
    private Date afdate;
    private String remark;
    private String luid;
    private String status;
    private String auid;
    private String mgmremk;
    private String unme;
    private String aunme;
    private String lunme;
    private String applton;
    private String deptnme;
    private List<Todoh> tohList = new ArrayList<Todoh>();
    private Integer sno;
    private String thnme;

    public String getDeptnme() {
        return deptnme;
    }

    public void setDeptnme(String deptnme) {
        this.deptnme = deptnme;
    }

    public String getUnme() {
        return unme;
    }

    public void setUnme(String unme) {
        this.unme = unme;
    }

    public String getMtid() {
        return mtid;
    }

    public void setMtid(String mtid) {
        this.mtid = mtid;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

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

    public String getJtypecd() {
        return jtypecd;
    }

    public void setJtypecd(String jtypecd) {
        this.jtypecd = jtypecd;
    }

    public Integer getEminute() {
        return eminute;
    }

    public void setEminute(Integer eminute) {
        this.eminute = eminute;
    }

    public Date getTfdate() {
        return tfdate;
    }

    public void setTfdate(Date tfdate) {
        this.tfdate = tfdate;
    }

    public Date getAfdate() {
        return afdate;
    }

    public void setAfdate(Date afdate) {
        this.afdate = afdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getMgmremk() {
        return mgmremk;
    }

    public void setMgmremk(String mgmremk) {
        this.mgmremk = mgmremk;
    }

    public String getApplton() {
        return applton;
    }

    public void setApplton(String applton) {
        this.applton = applton;
    }

    public List<Todoh> getTohList() {
        return tohList;
    }

    public void setTohList(List<Todoh> tohList) {
        this.tohList = tohList;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getThnme() {
        return thnme;
    }

    public void setThnme(String thnme) {
        this.thnme = thnme;
    }

    public String getAunme() {
        return aunme;
    }

    public void setAunme(String aunme) {
        this.aunme = aunme;
    }

    public String getLunme() {
        return lunme;
    }

    public void setLunme(String lunme) {
        this.lunme = lunme;
    }
    
    

    //*********** database related code ***************
    private DataSource dataSource;
    private SimpleJdbcCall jdbcCall = null;

    public SQLServerDataTable getToDoHTableStructure() throws SQLServerException {

        SQLServerDataTable sourceDataTable = new SQLServerDataTable();
        sourceDataTable.addColumnMetadata("MTID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("EDATE", java.sql.Types.DATE);
        sourceDataTable.addColumnMetadata("DESP", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("DEPTID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("APPID", java.sql.Types.VARCHAR);
        sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("EMINUTE", java.sql.Types.INTEGER);
        sourceDataTable.addColumnMetadata("TFDATE", java.sql.Types.DATE);
        sourceDataTable.addColumnMetadata("AFDATE", java.sql.Types.DATE);
        sourceDataTable.addColumnMetadata("REMARK", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("LUID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("STATUS", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("AUID", java.sql.Types.NVARCHAR);
        sourceDataTable.addColumnMetadata("MGMREMK", java.sql.Types.NVARCHAR);

        sourceDataTable.addRow(mtid, Util.sqlDate(edate), desp, deptid, appid, cmpy, cmpdept, eminute, Util.sqlDate(tfdate), Util.sqlDate(afdate), remark, luid, status, auid, mgmremk);

        return sourceDataTable;
    }

    @PostConstruct
    public void init() {
        this.mtid = "";
        this.edate = null;
        this.desp = "";
        this.deptid = "";
        this.appid = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.eminute = 0;
        this.tfdate = null;
        this.afdate = null;
        this.remark = "";
        this.luid = "";
        this.status = "";
        this.auid = "";
        this.mgmremk = "";
    }

    public Todoh() {
        this.mtid = "";
        this.edate = null;
        this.desp = "";
        this.deptid = "";
        this.appid = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.eminute = 0;
        this.tfdate = null;
        this.afdate = null;
        this.remark = "";
        this.luid = "";
        this.status = "";
        this.auid = "";
        this.mgmremk = "";
    }

    public List<Todoh> select() throws SQLException {
        try {
            this.tohList = new ArrayList<Todoh>();
            this.tohList = crud(this.getToDoHTableStructure(), Para.ToDoH.Select);
        } catch (SQLServerException ex) {
            Logger.getLogger(TodohDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tohList;
    }

    public List<Todoh> crud(SQLServerDataTable sourceDataTable, Para.ToDoH para) throws SQLException {

        List<Todoh> tohList = new ArrayList<Todoh>();

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh todoh = new Todoh();

                            todoh.setMtid(rs.getString("MTID"));
                            todoh.setEdate(rs.getDate("EDATE"));
                            todoh.setDesp(rs.getString("DESP"));
                            todoh.setCmpy(rs.getString("CMPY"));
                            todoh.setDeptid(rs.getString("DEPTID"));
                            todoh.setAppid(rs.getString("APPID"));
                            todoh.setCmpdept(rs.getString("CMPDEPT"));
                            todoh.setEminute(rs.getInt("EMINUTE"));
                            todoh.setTfdate(rs.getDate("TFDATE"));
                            todoh.setAfdate(rs.getDate("AFDATE"));
                            todoh.setRemark(rs.getString("REMARK"));
                            todoh.setLuid(rs.getString("LUID"));
                            todoh.setStatus(rs.getString("STATUS"));
                            todoh.setAuid(rs.getString("AUID"));
                            todoh.setMgmremk(rs.getString("MGMREMK"));

                            return todoh;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCrdn = jdbcCall.execute(pr);
            tohList = (List<Todoh>) mapCrdn.get("resultSet");
            String result = (String) mapCrdn.get("result");
            GrowlView.saveMessage(result);

        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }

        return tohList;

    }

    //*******************method to fill combo********************
    public List<Todoh> getTodohCombo() {

        try {
            SQLServerDataTable dt = getToDoHTableStructure();

            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh th = new Todoh();

                            th.setMtid(rs.getString("MTID"));
                            th.setDesp(rs.getString("DESP"));

                            return th;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", dt);
            pr.addValue("TranType", Para.ToDoH.Select_Lst.toString());
            pr.addValue("result", "");

            Map mapPrdtdn = jdbcCall.execute(pr);

            return (List<Todoh>) mapPrdtdn.get("resultSet");
        } catch (SQLServerException ex) {
            Logger.getLogger(Todoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

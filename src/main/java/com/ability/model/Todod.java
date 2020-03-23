package com.ability.model;

import com.ability.dao.Db;
import com.ability.dao.TododDao;
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
public class Todod {

    private String mtid;
    private String thnme;
    private String dtid;
    private Date adate;
    private String ptid;
    private String cmpy;
    private String cmpdept;
    private String desp;
    private String jtypecd;
    private Date sdate;
    private Integer eminute;
    private String maindesp;
    private Date tfdate;
    private Date afdate;
    private String remark;
    private String uid;
    private String status;
    private String attfnme;
    private String mgmremk;
    private String apporej;
    private String cuid;
    private Date apporejdt;
    private Date stsupdt;
    private String achead;
    private BigDecimal budamto;
    private BigDecimal budamtc;
    private List<Todod> todList = new ArrayList<Todod>();
    private Integer sno;
    private String deptId;
    private String deptnme;
    private String applton;
    private String appid;
    private String mainDesp;
    private String uname;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    

    public String getDeptnme() {
        return deptnme;
    }

    public void setDeptnme(String deptnme) {
        this.deptnme = deptnme;
    }

    public String getApplton() {
        return applton;
    }

    public void setApplton(String applton) {
        this.applton = applton;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
    
    public String getMtid() {
        return mtid;
    }

    public void setMtid(String mtid) {
        this.mtid = mtid;
    }

    public String getDtid() {
        return dtid;
    }

    public void setDtid(String dtid) {
        this.dtid = dtid;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public String getPtid() {
        return ptid;
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
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

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getJtypecd() {
        return jtypecd;
    }

    public void setJtypecd(String jtypecd) {
        this.jtypecd = jtypecd;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Integer getEminute() {
        return eminute;
    }

    public void setEminute(Integer eminute) {
        this.eminute = eminute;
    }

     public String getMaindesp() {
        return maindesp;
    }

    public void setMaindesp(String maindesp) {
        this.maindesp = maindesp;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttfnme() {
        return attfnme;
    }

    public void setAttfnme(String attfnme) {
        this.attfnme = attfnme;
    }

    public String getMgmremk() {
        return mgmremk;
    }

    public void setMgmremk(String mgmremk) {
        this.mgmremk = mgmremk;
    }

    public String getApporej() {
        return apporej;
    }

    public void setApporej(String apporej) {
        this.apporej = apporej;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public Date getApporejdt() {
        return apporejdt;
    }

    public void setApporejdt(Date apporejdt) {
        this.apporejdt = apporejdt;
    }

    public Date getStsupdt() {
        return stsupdt;
    }

    public void setStsupdt(Date stsupdt) {
        this.stsupdt = stsupdt;
    }

    public String getAchead() {
        return achead;
    }

    public void setAchead(String achead) {
        this.achead = achead;
    }

    public BigDecimal getBudamto() {
        return budamto;
    }

    public void setBudamto(BigDecimal budamto) {
        this.budamto = budamto;
    }

    public BigDecimal getBudamtc() {
        return budamtc;
    }

    public void setBudamtc(BigDecimal budamtc) {
        this.budamtc = budamtc;
    }

    

    public List<Todod> getTodList() {
        return todList;
    }

    public void setTodList(List<Todod> todList) {
        this.todList = todList;
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

    

    public String getMainDesp() {
        return mainDesp;
    }

    public void setMainDesp(String mainDesp) {
        this.mainDesp = mainDesp;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    

    //*********** database related code ***************
    private DataSource dataSource;
    private SimpleJdbcCall jdbcCall = null;

    public SQLServerDataTable getToDoDTableStructure() {

        try {
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();
            sourceDataTable.addColumnMetadata("MTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("DTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ADATE", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("PTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("DESP", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("JTYPECD", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("SDATE", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("EMINUTE", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("TFDATE", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("AFDATE", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("REMARK", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("UID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("STATUS", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ATTFNME", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MGMREMK", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("APPOREJ", java.sql.Types.CHAR);
            sourceDataTable.addColumnMetadata("CUID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("APPOREJDT", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("STSUPDT", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("ACHEAD", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("BUDAMTO", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("BUDAMTC", java.sql.Types.DECIMAL);

            sourceDataTable.addRow(mtid, dtid, Util.sqlDate(adate), ptid, cmpy, cmpdept, desp, jtypecd, Util.sqlDate(sdate), eminute,Util.sqlDate(tfdate), Util.sqlDate(afdate), remark, uid, status, attfnme, mgmremk, apporej, cuid, Util.sqlDate(apporejdt), Util.sqlDate(stsupdt), achead, budamto, budamtc);

            return sourceDataTable;
        } catch (SQLServerException ex) {
            Logger.getLogger(Todod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostConstruct
    public void init() {
        this.mtid = "";
        this.dtid = "";
        this.adate = null;
        this.ptid = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.desp = "";
        this.jtypecd = "";
        this.sdate = null;
        this.eminute = 0;
        this.tfdate = null;
        this.afdate = null;
        this.remark = "";
        this.uid = "";
        this.status = "";
        this.attfnme = "";
        this.mgmremk = "";
        this.apporej = "";
        this.cuid = "";
        this.apporejdt = null;
        this.stsupdt = null;
        this.achead = "";
        this.budamto = null;
        this.budamtc = null;
        this.thnme = null;
    }

    public List<Todod> getTodCombo(String MTID) throws SQLException {
        setMtid(MTID);
        SQLServerDataTable dt = getToDoDTableStructure();
        DataSource ds = Db.getSQLDataSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                .returningResultSet("resultSet",
                        new ParameterizedBeanPropertyRowMapper<Todod>() {
                    @Override
                    public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Todod td = new Todod();
                        td.setDtid(rs.getString("DTID"));
                        td.setDesp(rs.getString("DESP"));

                        return td;
                    }
                });
        MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoD", dt);
        pr.addValue("TranType", Para.ToDoD.Select_Lst.toString());
        pr.addValue("result", "");
        Map mapCt = jdbcCall.execute(pr);
        return (List<Todod>) mapCt.get("resultSet");
    }

}

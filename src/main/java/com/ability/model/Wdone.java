package com.ability.model;

import com.ability.util.Util;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Wdone {

    private String uid;
    private Date wdate;
    private String sno;
    private String mtid;
    private String dtid;
    private String desp;
    private String deptid;
    private String deptnme;
    private String applton;
    private String cmpy;
    private String cmpdept;
    private Date ftime;
    private Date ttime;
    private String jtypecd;
    private String plur;
    private Long minutes;
    private String remark;
    private String status;
    private String mgmremk;
    private String mainTaskName;
    private String detailName;
    private Dept combodept;
    private Apptn comboapp;
    private Jtype combojt;
    private Todoh comboth;
    private Todod combotd;
    private String appid;
    private String uName;
    private String fromTime;
    private String toTime;
    
    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getWdate() {
        return wdate;
    }

    public void setWdate(Date wdate) {
        this.wdate = wdate;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public String getApplton() {
        return applton;
    }

    public void setApplton(String applton) {
        this.applton = applton;
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

    public Date getFtime() {
        return ftime;
    }

    public void setFtime(Date ftime) {
        this.ftime = ftime;
    }

    public Date getTtime() {
        return ttime;
    }

    public void setTtime(Date ttime) {
        this.ttime = ttime;
    }

    public String getJtypecd() {
        return jtypecd;
    }

    public void setJtypecd(String jtypecd) {
        this.jtypecd = jtypecd;
    }

    public String getPlur() {
        return plur;
    }

    public void setPlur(String plur) {
        this.plur = plur;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMgmremk() {
        return mgmremk;
    }

    public void setMgmremk(String mgmremk) {
        this.mgmremk = mgmremk;
    }

    public Dept getCombodept() {
        return combodept;
    }

    public void setCombodept(Dept combodept) {
        this.combodept = combodept;
    }

    public Apptn getComboapp() {
        return comboapp;
    }

    public void setComboapp(Apptn comboapp) {
        this.comboapp = comboapp;
    }

    public Jtype getCombojt() {
        return combojt;
    }

    public void setCombojt(Jtype combojt) {
        this.combojt = combojt;
    }

    public Todoh getComboth() {
        return comboth;
    }

    public void setComboth(Todoh comboth) {
        this.comboth = comboth;
    }

    public Todod getCombotd() {
        return combotd;
    }

    public void setCombotd(Todod combotd) {
        this.combotd = combotd;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDeptnme() {
        return deptnme;
    }

    public void setDeptnme(String deptnme) {
        this.deptnme = deptnme;
    }

    public String getMainTaskName() {
        return mainTaskName;
    }

    public void setMainTaskName(String mainTaskName) {
        this.mainTaskName = mainTaskName;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
    

    public SQLServerDataTable getWDTableStructure() {

        try {
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();

            sourceDataTable.addColumnMetadata("UID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("WDATE", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("SNO", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("DTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("DESP", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("DEPTID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("APPID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("CMPY", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("CMPDEPT", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("FTIME", java.sql.Types.TIMESTAMP);
            sourceDataTable.addColumnMetadata("TTIME", java.sql.Types.TIMESTAMP);
            sourceDataTable.addColumnMetadata("JTYPECD", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("PLUR", java.sql.Types.CHAR);
            sourceDataTable.addColumnMetadata("MINUTES", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("REMARK", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("STATUS", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MGMREMK", java.sql.Types.NVARCHAR);

            sourceDataTable.addRow(uid, Util.sqlDate(wdate), sno, mtid, dtid, desp, deptid, appid, cmpy, cmpdept, Util.sqlTime(ftime), Util.sqlTime(ttime), jtypecd, plur, minutes, remark, status, mgmremk);

            return sourceDataTable;
        } catch (SQLServerException ex) {
            Logger.getLogger(Wdone.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PostConstruct
    public void init() {
        this.uid = "";
        this.wdate = null;
        this.mainTaskName = "";
        this.detailName = "";
        this.sno = "";
        this.mtid = "";
        this.dtid = "";
        this.desp = "";
        this.deptid = "";
        this.appid = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.ftime = null;
        this.ttime = null;
        this.jtypecd = "";
        this.plur = "";
        this.minutes = null;
        this.remark = "";
        this.status = "";
        this.mgmremk = "";
        this.deptnme = "";
        this.applton = "";
    }

    public Wdone() {
        this.uid = "";
        this.wdate = null;
        this.sno = "";
        this.mtid = "";
        this.dtid = "";
        this.desp = "";
        this.deptid = "";
        this.appid = "";
        this.cmpy = "";
        this.cmpdept = "";
        this.ftime = null;
        this.ttime = null;
        this.jtypecd = "";
        this.plur = "";
        this.minutes = null;
        this.remark = "";
        this.status = "";
        this.mgmremk = "";
        this.deptnme = "";
        this.applton = "";
    }

}

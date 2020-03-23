/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.dao;

import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Usr;
import com.ability.util.AutoComplete;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ApprejDao {

    @ManagedProperty("#{todod}")
    private Todod todod;
    private String txtucode;
    private String txtunme;
    private Date txtptoday;
    private Todod selectTodod = new Todod();
    private TodohDao todohDao = new TodohDao();
    private List<Todoh> todohlist = new ArrayList<Todoh>();
    private UsrDao usrDao = new UsrDao();
    private List<Usr> usrlist = new ArrayList<Usr>();

    public Todod getTodod() {
        return todod;
    }

    public void setTodod(Todod todod) {
        this.todod = todod;
    }

    public String getTxtucode() {
        return txtucode;
    }

    public void setTxtucode(String txtucode) {
        this.txtucode = txtucode;
    }

    public String getTxtunme() {
        return txtunme;
    }

    public void setTxtunme(String txtunme) {
        this.txtunme = txtunme;
    }

    public Date getTxtptoday() {
        return txtptoday;
    }

    public void setTxtptoday(Date txtptoday) {
        this.txtptoday = txtptoday;
    }

    public Todod getSelectTodod() {
        return selectTodod;
    }

    public void setSelectTodod(Todod selectTodod) {
        this.selectTodod = selectTodod;
    }

    public TodohDao getTodohDao() {
        return todohDao;
    }

    public void setTodohDao(TodohDao todohDao) {
        this.todohDao = todohDao;
    }
    
    

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Todod> tododList;

    public List<Todod> getTododList() {
        return tododList;
    }

    public void setTododList(List<Todod> tododList) {
        this.tododList = tododList;
    }

    //*********** methods ***************
    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");

    @PostConstruct
    public void init() {
        this.txtptoday = autoComplete.getpToday();
        this.txtucode = autoComplete.getUserId();
        this.txtunme = autoComplete.getUserName();
        Get_SelectGrid();

    }

    public List<Todod> crud(SQLServerDataTable sourceDataTable, Para.ToDoD para, boolean showMsgYN) {
        List<Todod> tododList = new ArrayList<Todod>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod todod = new Todod();

                            todod.setSno(rowNum + 1);
                            todod.setMtid(rs.getString("MTID"));
                            todod.setDtid(rs.getString("DTID"));
                            todod.setAdate(rs.getDate("ADATE"));
                            todod.setPtid(rs.getString("PTID"));
                            todod.setCmpy(rs.getString("CMPY"));
                            todod.setCmpdept(rs.getString("CMPDEPT"));
                            todod.setDesp(rs.getString("DESP"));
                            todod.setJtypecd(rs.getString("JTYPECD"));
                            todod.setSdate(rs.getDate("SDATE"));
                            todod.setEminute(rs.getInt("EMINUTES"));
                            todod.setTfdate(rs.getDate("TFDATE"));
                            todod.setAfdate(rs.getDate("AFDATE"));
                            todod.setRemark(rs.getString("REMARK"));
                            todod.setUid(rs.getString("UID"));
                            todod.setStatus(rs.getString("STATUS"));
                            todod.setAttfnme(rs.getString("ATTFNME"));
                            todod.setMgmremk(rs.getString("MGMREMK"));
                            todod.setApporej(rs.getString("APPOREJ"));
                            todod.setCuid(rs.getString("CUID"));
                            todod.setApporejdt(rs.getDate("APPOREJDT"));
                            todod.setStsupdt(rs.getDate("STSUPDT"));
                            todod.setAchead(rs.getString("ACHEAD"));
                            todod.setBudamto(rs.getBigDecimal("BUDAMTO"));
                            todod.setBudamtc(rs.getBigDecimal("BUDAMTC"));

                            return todod;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    }

    public void Get_SelectGrid() {
        try {

            this.tododList = getGrid(autoComplete.getUserId());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Todod> getGrid(String UID) throws SQLException {
        List<Todod> tododList = new ArrayList<Todod>();
        try {
            todod.setUid(UID);
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod todod = new Todod();

                            todod.setSno(rowNum + 1);
                            todod.setMtid(rs.getString("MTID"));
                            todod.setDtid(rs.getString("DTID"));
                            todod.setAdate(rs.getDate("ADATE"));
                            todod.setPtid(rs.getString("PTID"));
                            todod.setCmpy(rs.getString("CMPY"));
                            todod.setCmpdept(rs.getString("CMPDEPT"));
                            todod.setDesp(rs.getString("DESP"));
                            todod.setJtypecd(rs.getString("JTYPECD"));
                            todod.setSdate(rs.getDate("SDATE"));
                            todod.setEminute(rs.getInt("EMINUTE"));
                            todod.setTfdate(rs.getDate("TFDATE"));
                            todod.setAfdate(rs.getDate("AFDATE"));
                            todod.setRemark(rs.getString("REMARK"));
                            todod.setUid(rs.getString("UID"));
                            todod.setStatus(rs.getString("STATUS"));
                            todod.setAttfnme(rs.getString("ATTFNME"));
                            todod.setMgmremk(rs.getString("MGMREMK"));
                            todod.setApporej(rs.getString("APPOREJ"));
                            todod.setCuid(rs.getString("CUID"));
                            todod.setApporejdt(rs.getDate("APPOREJDT"));
                            todod.setStsupdt(rs.getDate("STSUPDT"));
                            todod.setAchead(rs.getString("ACHEAD"));
                            todod.setBudamto(rs.getBigDecimal("BUDAMTO"));
                            todod.setBudamtc(rs.getBigDecimal("BUDAMTC"));

                            return todod;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", todod.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.SelectARGrid.toString());
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;

    }
    
    public void selectapr() throws SQLException {

        Todod t = this.selectTodod;
        todod.setSno(t.getSno());        
//        todohDao.Select_Name(t.getMtid());
//        todohlist = todohDao.getTodohList();
//        if (!todohlist.isEmpty()) {
//            String thnme = todohlist.get(0).getDesp();
//            todod.setMtid(thnme);
//        }
        todod.setMtid(t.getMtid());
        todod.setDtid(t.getDtid());
        todod.setDesp(t.getDesp());
        usrDao.Select_Name(t.getUid());
        usrlist = usrDao.getUsrList();
        if (!usrlist.isEmpty()) {
            String urnme = usrlist.get(0).getUnme();
            todod.setUid(urnme);
        }
        todod.setStatus(t.getStatus());
        todod.setApporej(t.getApporej());
        todod.setMgmremk(t.getMgmremk());
    }
    
    public void update_click() throws SQLServerException {        
         String ss=this.todod.getApporej();
        if (todod.getApporej() == ("A")) {
            if (todod.getStatus() != "Finish"){
                GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Change Status.");
                return;
            }
        
        } else {
            if (todod.getStatus() == "Finish"){
                GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Change Status.");
                return;
            }
        
        }
        
        upDateAppRej(todod.getMtid(), todod.getDtid(), todod.getApporej(),todod.getStatus(),todod.getMgmremk(),autoComplete.getpToday(), false);
        Get_SelectGrid();
        clear();
             
    }
    public void update() throws SQLServerException{
        String ss=this.todod.getApporej();
        if(ss==null){
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Change Status.");
                return;
        }
        else if(ss.equals("A")){
            upDateAppRej(todod.getMtid(), todod.getDtid(), todod.getApporej(),todod.getStatus(),todod.getMgmremk(),autoComplete.getpToday(), false); 
             Get_SelectGrid();
        }else{
            updateAppR(todod.getMtid(), todod.getDtid(), todod.getApporej(),todod.getStatus(),todod.getMgmremk(),autoComplete.getpToday(), false); 
             Get_SelectGrid();
        }
    }
    
    public List<Todod> upDateAppRej(String mtid,String dtid,String apprej,String status,String mgmrm,Date apprejD,boolean showMsgYN) {
       List<Todod> tododList = new ArrayList<Todod>();
       Todod detail = new Todod();
       detail.setMtid(mtid);
       detail.setDtid(dtid);
       detail.setApporej(apprej);
       detail.setStatus(status);
       detail.setMgmremk(mgmrm);
       detail.setStatus("Finish");
       detail.setApporejdt(apprejD);
       try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod todod = new Todod();
                            
                            return todod;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", detail.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.UpdateAppRej);
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    }
    
    public void clear() {

        todod.init();
    }
    public List<Todod> updateAppR(String mtid,String dtid,String apprej,String status,String mgmrm,Date apprejD,boolean showMsgYN) {
       List<Todod> tododList = new ArrayList<Todod>();
       Todod detail = new Todod();
       detail.setMtid(mtid);
       detail.setDtid(dtid);
       detail.setApporej(apprej);
       detail.setStatus(status);
       detail.setMgmremk(mgmrm);
       detail.setStatus("Cancel");
       detail.setApporejdt(apprejD);
       try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod todod = new Todod();
                            
                            return todod;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", detail.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.UpdateAppRej);
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    }
    

}

    


package com.ability.dao;

import com.ability.model.Apptn;
import com.ability.model.Dept;
import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Wdone;
import com.ability.model.Jtype;

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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "wdoneDao")
@ViewScoped
public class WdoneDao {    
    
    @ManagedProperty("#{wdone}")
    private Wdone wdone = new Wdone();
    private Todod todod = new Todod();
    private Date txtptoday;
    private String txtucode;
    private String txtunme;
    private List<Wdone> listWdone = new ArrayList<Wdone>();
    private Dept combodept = new Dept();
    private Apptn comboapp = new Apptn();
    private DeptDao deptDao = new DeptDao();
    private List<Dept> deptlist = new ArrayList<Dept>();
    private TodohDao todohDao = new TodohDao();
    private List<Todoh> todohlist = new ArrayList<Todoh>();
    private TododDao tododDao = new TododDao();
    private List<Todod> tododlist = new ArrayList<Todod>();
    private ApptnDao appDao = new ApptnDao();
    private List<Apptn> applist = new ArrayList<Apptn>();
    private JtypeDao jtypeDao = new JtypeDao();
    private List<Jtype> jtypelist = new ArrayList<Jtype>();
    private Wdone selectWdone = new Wdone();
    private Todoh comboth = new Todoh();
    private Todod combotd = new Todod();
    private Jtype comboJtype = new Jtype();
    private List<Todod> listTodod = new ArrayList<Todod>();
    private String totalhr;
    private Boolean newRecord;
    private Integer urgented;
    private boolean mainTaskBtn;
    private boolean detailTaskBtn;
    public TododDao todoDao = new TododDao();
    public TodohDao headerDao = new TodohDao();
    private boolean calendarBoolean=true;

    public boolean isCalendarBoolean() {
        return calendarBoolean;
    }

    public void setCalendarBoolean(boolean calendarBoolean) {
        this.calendarBoolean = calendarBoolean;
    }
    
    public TodohDao getHeaderDao() {
        return headerDao;
    }

    public void setHeaderDao(TodohDao headerDao) {
        this.headerDao = headerDao;
    }

    private Todod selectedTodoD = new Todod();

    public Wdone getWdone() {
        return wdone;
    }

    public void setWdone(Wdone wdone) {
        this.wdone = wdone;
    }

    public Date getTxtptoday() {
        return txtptoday;
    }

    public void setTxtptoday(Date txtptoday) {
        this.txtptoday = txtptoday;
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

    public List<Wdone> getListWdone() {
        return listWdone;
    }

    public void setListWdone(List<Wdone> listWdone) {
        this.listWdone = listWdone;
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

    public DeptDao getDeptDao() {
        return deptDao;
    }

    public void setDeptDao(DeptDao deptDao) {
        this.deptDao = deptDao;
    }

    public List<Dept> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(List<Dept> deptlist) {
        this.deptlist = deptlist;
    }

    public Wdone getSelectWdone() {
        return selectWdone;
    }

    public void setSelectWdone(Wdone selectWdone) {
        this.selectWdone = selectWdone;
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

    public Jtype getComboJtype() {
        return comboJtype;
    }

    public void setComboJtype(Jtype comboJtype) {
        this.comboJtype = comboJtype;
    }

    public List<Todod> getListTodod() {
        return listTodod;
    }

    public Todod getSelectedTodoD() {
        return selectedTodoD;
    }

    public void setSelectedTodoD(Todod selectedTodoD) {
        this.selectedTodoD = selectedTodoD;
    }

    public void setListTodod(List<Todod> listTodod) {
        this.listTodod = listTodod;
    }

    public String getTotalhr() {
        return totalhr;
    }

    public void setTotalhr(String totalhr) {
        this.totalhr = totalhr;
    }

    public Boolean getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(Boolean newRecord) {
        this.newRecord = newRecord;
    }

    public TodohDao getTodohDao() {
        return todohDao;
    }

    public void setTodohDao(TodohDao todohDao) {
        this.todohDao = todohDao;
    }

    public ApptnDao getAppDao() {
        return appDao;
    }

    public void setAppDao(ApptnDao appDao) {
        this.appDao = appDao;
    }

    public List<Apptn> getApplist() {
        return applist;
    }

    public void setApplist(List<Apptn> applist) {
        this.applist = applist;
    }

    public JtypeDao getJtypeDao() {
        return jtypeDao;
    }

    public void setJtypeDao(JtypeDao jtypeDao) {
        this.jtypeDao = jtypeDao;
    }

    public List<Jtype> getJtypelist() {
        return jtypelist;
    }

    public void setJtypelist(List<Jtype> jtypelist) {
        this.jtypelist = jtypelist;
    }

    public Integer getUrgented() {
        return urgented;
    }

    public void setUrgented(Integer urgented) {
        this.urgented = urgented;
    }

    public boolean isMainTaskBtn() {
        return mainTaskBtn;
    }

    public void setMainTaskBtn(boolean mainTaskBtn) {
        this.mainTaskBtn = mainTaskBtn;
    }

    public boolean isDetailTaskBtn() {
        return detailTaskBtn;
    }

    public void setDetailTaskBtn(boolean detailTaskBtn) {
        this.detailTaskBtn = detailTaskBtn;
    }

    public TododDao getTodoDao() {
        return todoDao;
    }

    public void setTodoDao(TododDao todoDao) {
        this.todoDao = todoDao;
    }

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Wdone> wdoneList = new ArrayList<Wdone>();

    public List<Wdone> getWdoneList() {
        return wdoneList;
    }

    public void setWdoneList(List<Wdone> wdoneList) {
        this.wdoneList = wdoneList;
    }

    private List<Todod> tododList;

    public List<Todod> getTododList() {
        return tododList;
    }

    public void setTododList(List<Todod> tododList) {
        this.tododList = tododList;
    }

    public Todod getTodod() {
        return todod;
    }

    public void setTodod(Todod todod) {
        this.todod = todod;
    }

    public List<Todoh> getTodohlist() {
        return todohlist;
    }

    public void setTodohlist(List<Todoh> todohlist) {
        this.todohlist = todohlist;
    }

    public TododDao getTododDao() {
        return tododDao;
    }

    public void setTododDao(TododDao tododDao) {
        this.tododDao = tododDao;
    }

    public List<Todod> getTododlist() {
        return tododlist;
    }

    public void setTododlist(List<Todod> tododlist) {
        this.tododlist = tododlist;
    }

    //*********** methods ***************
    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");

    @PostConstruct
    public void init() {

        this.txtptoday = autoComplete.getpToday();
        this.txtucode = autoComplete.getUserId();
        this.txtunme = autoComplete.getUserName();

        todod.setUid(txtucode);
        wdone.setUid(txtucode);
        wdone.setWdate(txtptoday);
        this.listTodod = crudtod(todod.getToDoDTableStructure(), Para.ToDoD.SelectGrid1, false);
        GetWGrid();
        newSno();

    }

    public List<Wdone> crud(Para.Wdone para, boolean showMsgYN) throws SQLServerException {

        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_Wdone")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Wdone>() {
                        @Override
                        public Wdone mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Wdone wdone = new Wdone();

                            wdone.setUid(rs.getString("UID"));
                            wdone.setWdate(rs.getDate("WDATE"));
                            wdone.setSno(rs.getString("SNO"));
                            // wdone.setMtid(rs.getString("MTID"));
                            // wdone.setDtid(rs.getString("DTID"));
                            wdone.setDesp(rs.getString("DESP"));
                            wdone.setDeptid(rs.getString("DEPTID"));
                            wdone.setAppid(rs.getString("APPID"));
                            wdone.setCmpy(rs.getString("CMPY"));
                            wdone.setCmpdept(rs.getString("CMPDEPT"));
                            wdone.setFtime(rs.getDate("FTIME"));
                            wdone.setTtime(rs.getDate("TTIME"));
                            wdone.setMinutes(rs.getLong("MINUTES"));
                            wdone.setJtypecd(rs.getString("JTYPECD"));
                            wdone.setPlur(rs.getString("PLUR"));
                            wdone.setRemark(rs.getString("REMARK"));
                            wdone.setStatus(rs.getString("STATUS"));
                            wdone.setMgmremk(rs.getString("MGMREMK"));

                            return wdone;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpWdone", wdone.getWDTableStructure());
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            listWdone = (List<Wdone>) mapCt.get("resultSet");
            String result = (String) mapCt.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return listWdone;
    }

    public void GetWGrid() {
        this.listWdone = crudl(wdone.getWDTableStructure(), Para.Wdone.SelectWGrid, false);

    }

    public void insert() throws SQLServerException {
        
        try {

            if (newRecord == true) {
                wdone.setUid(autoComplete.getUserId());
                wdone.setWdate(autoComplete.getpToday());
//                wdone.setMtid(comboth.getMtid());
//                wdone.setDtid(combotd.getDtid());
//                wdone.setDeptid(combodept.getDeptid());
//                wdone.setAppid(comboapp.getAppid());
                  wdone.setJtypecd(comboJtype.getJtypecd());
                listWdone = crud(Para.Wdone.Insert, true);
 //               todoDao.upDateStatus(wdone.getDtid(), wdone.getMtid(), wdone.getStatus(), false);
                this.listWdone = crudl(wdone.getWDTableStructure(), Para.Wdone.SelectWGrid, false);
                init();
                clear();
                newSno();
            } else {
                           
                wdone.setUid(autoComplete.getUserId());
                wdone.setWdate(autoComplete.getpToday());
                wdone.setMtid(selectWdone.getMtid());
                wdone.setDtid(selectWdone.getDtid());
                wdone.setDeptid(selectWdone.getDeptid());
                wdone.setAppid(selectWdone.getAppid());
                wdone.setJtypecd(comboJtype.getJtypecd());
                wdone.setSno(wdone.getSno());
                listWdone = crud(Para.Wdone.Update, true);
                todoDao.upDateStatus(wdone.getDtid(), wdone.getMtid(), wdone.getStatus(), false);
                this.listWdone = crudl(wdone.getWDTableStructure(), Para.Wdone.SelectWGrid, false);
                init();
                clear();
                newSno();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Wdone> crudl(SQLServerDataTable sourceDataTable, Para.Wdone para, boolean showMsgYN) {
        List<Wdone> wdList = new ArrayList<Wdone>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_Wdone")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Wdone>() {
                        @Override
                        public Wdone mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Wdone wd = new Wdone();

                            wd.setUid(rs.getString("UID"));
                            wd.setWdate(rs.getDate("WDATE"));
                            wd.setSno(rs.getString("SNO"));
                            wd.setMtid(rs.getString("MTID"));
                            wd.setDtid(rs.getString("DTID"));
                            wd.setDesp(rs.getString("DESP"));
                            wd.setDeptid(rs.getString("DEPTID"));
                            wd.setAppid(rs.getString("APPID"));
                            wd.setCmpy(rs.getString("CMPY"));
                            wd.setCmpdept(rs.getString("CMPDEPT"));
                            wd.setFtime(rs.getTime("FTIME"));
                            wd.setTtime(rs.getTime("TTIME"));
                            wd.setJtypecd(rs.getString("JTYPECD"));
                            wd.setPlur(rs.getString("PLUR"));
                            wd.setMinutes(rs.getLong("MINUTES"));
                            wd.setRemark(rs.getString("Remark"));
                            wd.setStatus(rs.getString("STATUS"));
                            wd.setMgmremk(rs.getString("MGMREMK"));
                            wd.setDeptnme(rs.getString("DEPTNME"));
                            wd.setApplton(rs.getString("APPLTON"));

                            return wd;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpWdone", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            wdList = (List<Wdone>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return wdList;
    }

    public void newSno() {
        try {
            newRecord = true;
            clear();
            wdone.setUid(txtucode);
            wdone.setWdate(txtptoday);
            wdone.setSno(get_NewSno(txtucode, txtptoday, false).get(0).getSno());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Wdone> get_NewSno(String UID, Date PToday, boolean showMessage) throws SQLException {
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_Wdone")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Wdone>() {
                        @Override
                        public Wdone mapRow(ResultSet rs, int rowNum) throws SQLException {

                            wdone.setSno(rs.getString("SNO"));
                            return wdone;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpWdone", wdone.getWDTableStructure());
            pr.addValue("TranType", Para.Wdone.Get_NewSno);
            pr.addValue("result", "");

            Map mapjt = jdbcCall.execute(pr);
            wdoneList = (List<Wdone>) mapjt.get("resultSet");
            String result = (String) mapjt.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return wdoneList;
    }

    public List<Todod> getgrid(String UID) throws SQLException {
        try {
            todod.setUid(UID);
            SQLServerDataTable dt = todod.getToDoDTableStructure();

            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {

                            Todod todod = new Todod();

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
                            todod.setThnme(rs.getString("thnme"));

                            return todod;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoD", dt);
            pr.addValue("TranType", Para.ToDoD.SelectGrid1.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            return (List<Todod>) mapCt.get("resultSet");
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
    }

    public List<Todod> crudtod(SQLServerDataTable sourceDataTable, Para.ToDoD para, boolean showMsgYN) {
        List<Todod> tododList = new ArrayList<Todod>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod todod = new Todod();

                            todod.setMtid(rs.getString("MTID"));
                            todod.setDtid(rs.getString("DTID"));
                            todod.setDeptId(rs.getString("DEPTID"));
                            todod.setDeptnme(rs.getString("DEPTNME"));
                            todod.setAppid(rs.getString("APPID"));
                            todod.setApplton(rs.getString("APPLTON"));
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
                            todod.setThnme(rs.getString("thnme"));

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

    public void deleteGrid() {
        listWdone.remove(selectWdone);
    }   
    
    public void delete() {
        wdone = this.selectWdone;
        wdone.setUid(txtucode);
        wdone.setWdate(txtptoday);
        wdone.setSno(wdone.getSno());
        this.wdoneList = crudl(wdone.getWDTableStructure(), Para.Wdone.Delete, true);
        this.listWdone = crudl(wdone.getWDTableStructure(), Para.Wdone.SelectWGrid, false);
        clear();
        newSno();
    }

    public void select() {

        Wdone w = this.selectWdone;
        wdone.setSno(w.getSno());
        comboth.setDesp(w.getMtid());
        todohDao.Select_Name(w.getMtid());
        todohlist = todohDao.getTodohList();
        if (!todohlist.isEmpty()) {
            String thnme = todohlist.get(0).getDesp();
            comboth.setDesp(thnme);
            wdone.setMainTaskName(comboth.getDesp());
        }
        combotd.setDesp(w.getDtid());
        tododDao.Select_Name(w.getDtid(),w.getMtid());
        
        tododlist = tododDao.getTododList();
        if (!tododlist.isEmpty()) {
            String tdnme = tododlist.get(0).getDesp();
            wdone.setDetailName(tdnme);
        }
//        combodept.setDeptnme(w.getDeptid());
//        deptDao.Select_Name(w.getDeptid());
//        deptlist = deptDao.getDeptList();
//        if (!deptlist.isEmpty()) {
//            String dpnme = deptlist.get(0).getDeptnme();
//            combodept.setDeptnme(dpnme);
//            wdone.setDeptnme(combodept.getDeptnme());            
//        }
//        comboapp.setApplton(w.getAppid());
//        appDao.Select_Name(w.getAppid(), combodept.getDeptid());
//        applist = appDao.getAppList();
//        if (!applist.isEmpty()) {
//            String apnme = applist.get(0).getApplton();
//            wdone.setApplton(apnme);
//        }
        wdone.setDeptnme(w.getDeptnme());
        wdone.setApplton(w.getApplton());
        wdone.setDesp(w.getDesp());
        wdone.setCmpy(w.getCmpy());
        wdone.setCmpdept(w.getCmpdept());
        wdone.setFtime(w.getFtime());
        wdone.setTtime(w.getTtime());
        wdone.setMinutes(w.getMinutes());
        comboJtype.setJtypenme(w.getJtypecd());
        jtypeDao.Select_Name(w.getJtypecd());
        jtypelist = jtypeDao.getJtypeList();
        if (!jtypelist.isEmpty()) {
            String jtnme = jtypelist.get(0).getJtypenme();
            comboJtype.setJtypenme(jtnme);
        }
        wdone.setPlur(w.getPlur());
        wdone.setStatus(w.getStatus());
        wdone.setRemark(w.getRemark());
        newRecord = false;
    }

    public void selectMtid(SelectEvent event) {
        Object o = event.getObject();
        Todoh th = (Todoh) o;
        comboth = th;

    }

    public void selectDtid(SelectEvent event) {
        Object o = event.getObject();
        Todod td = (Todod) o;
        combotd = td;

    }

    public void selectDept(SelectEvent event) {
        Object o = event.getObject();
        Dept dp = (Dept) o;
        combodept = dp;

    }

    public void selectApptn(SelectEvent event) {
        Object o = event.getObject();
        Apptn app = (Apptn) o;
        comboapp = app;

    }

    public void selectJtype(SelectEvent event) {
        Object o = event.getObject();
        Jtype Jt = (Jtype) o;
        comboJtype = Jt;

    }

    public void clear() {
        wdone.init();
        comboth = new Todoh();
        combotd = new Todod();
        combodept = new Dept();
        comboapp = new Apptn();
        comboJtype=new Jtype();
        wdone.setSno(null);
//        this.setComboth(null);
//        this.setCombotd(null);
//        this.setCombodept(null);
//        this.setComboapp(null);
//        this.setTotalhr(null);
//        this.setComboJtype(null);

    }

    public void selectedValue(ValueChangeEvent evt) {
        Integer poru = (Integer) evt.getNewValue();
        setUrgented(poru);
        if (urgented == 2) {
            this.mainTaskBtn = true;
            this.detailTaskBtn = true;
        }
    }

    public void timeLotFocus() {
        Date fromTime = this.wdone.getFtime();
        Date toTime = this.wdone.getTtime();
        //Long milises=toTime.getTime() - fromTime.getTime();
        Long hour = (toTime.getTime() - fromTime.getTime()) / 60000;
       
        wdone.setMinutes(hour);

    }

    public void selectD() throws SQLServerException {
        Todod detail = this.selectedTodoD;
//        Todoh header = new Todoh();
//        header.setMtid(detail.getMtid());
//        todohlist = headerDao.selectByName(header.getToDoHTableStructure(), Para.ToDoH.Select_Name, false);
//        if (!todohlist.isEmpty()) {
//            String dpnme = todohlist.get(0).getDesp();
//
//            comboth.setDesp(dpnme);
//        }
//        tododlist = todoDao.selectByMtid(detail.getDtid(), detail.getMtid());
//        if (!tododlist.isEmpty()) {
//            String dpnme = tododlist.get(0).getDesp();
//            combotd.setDesp(dpnme);
//        }
        wdone.setMtid(detail.getMtid());
        wdone.setDtid(detail.getDtid());
        wdone.setDeptid(detail.getDeptId());
        wdone.setAppid(detail.getAppid());
        wdone.setJtypecd(detail.getJtypecd());
        wdone.setMainTaskName(detail.getThnme());
        wdone.setDetailName(detail.getDesp());
        wdone.setDeptnme(detail.getDeptnme());
        wdone.setApplton(detail.getApplton());
        
    }

}

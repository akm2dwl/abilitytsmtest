package com.ability.dao;

import com.ability.model.Apptn;
import com.ability.model.Todoh;
import com.ability.model.Usr;
import com.ability.model.Dept;

import com.ability.util.AutoComplete;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import javax.faces.bean.ManagedBean;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.ability.converter.ApptnConverter;
import com.ability.util.Util;
import static com.ability.util.Util.DateFormat;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

@ManagedBean(name="todohDao")
@ViewScoped
public class TodohDao {

    @ManagedProperty("#{todoh}")
    private Todoh todoh;

    public Todoh getTodoh() {
        return todoh;
    }

    public void setTodoh(Todoh todoh) {
        this.todoh = todoh;
    }

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Todoh> todohList= new ArrayList<Todoh>();
    private Usr comboLUsr = new Usr();
    private Usr comboAUsr = new Usr();
    private Todoh selectedTH;
    private Dept combodept = new Dept();
    private Apptn comboapp = new Apptn();
    private Date txtptoday;
    private DeptDao deptDao = new DeptDao();
    private List<Dept> deptlist = new ArrayList<Dept>();
    private boolean newRecord;
    private String mtid;
    private ApptnConverter appConverter=new ApptnConverter(); 
    List<Todoh> thList = new ArrayList<Todoh>();
    private ApptnDao appDao = new ApptnDao();
    private UsrDao usrDao = new UsrDao();
    List<Usr> usrlist = new ArrayList<Usr>();
    List<Usr> aUsrList=new ArrayList<Usr>();
    private Apptn apptn = new Apptn();
    
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

    public Apptn getComboapp() {
        return comboapp;
    }

    public void setComboapp(Apptn comboapp) {
        this.comboapp = comboapp;
    }

    public Dept getCombodept() {
        return combodept;
    }

    public void setCombodept(Dept combodept) {
        this.combodept = combodept;
    }

    public Todoh getSelectedTH() {
        return selectedTH;
    }

    public void setSelectedTH(Todoh selectedTH) {
        this.selectedTH = selectedTH;
    }

    public Usr getComboLUsr() {
        return comboLUsr;
    }

    public void setComboLUsr(Usr comboLUsr) {
        this.comboLUsr = comboLUsr;
    }

    public Usr getComboAUsr() {
        return comboAUsr;
    }

    public void setComboAUsr(Usr comboAUsr) {
        this.comboAUsr = comboAUsr;
    }

    public List<Todoh> getTodohList() {
        return todohList;
    }

    public void setTodohList(List<Todoh> todohList) {
        this.todohList = todohList;
    }

    public Date getTxtptoday() {
        return txtptoday;
    }

    public void setTxtptoday(Date txtptoday) {
        this.txtptoday = txtptoday;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public List<Todoh> getThList() {
        return thList;
    }

    public void setThList(List<Todoh> thList) {
        this.thList = thList;
    }

    public String getMtid() {
        return mtid;
    }

    public void setMtid(String mtid) {
        this.mtid = mtid;
    }

    public ApptnDao getAppDao() {
        return appDao;
    }

    public void setAppDao(ApptnDao appDao) {
        this.appDao = appDao;
    }

    private List<Apptn> applist = new ArrayList<Apptn>();

    public List<Apptn> getApplist() {
        return applist;
    }

    public void setApplist(List<Apptn> applist) {
        this.applist = applist;
    }

    public UsrDao getUsrDao() {
        return usrDao;
    }

    public void setUsrDao(UsrDao usrDao) {
        this.usrDao = usrDao;
    }

    public List<Usr> getUsrlist() {
        return usrlist;
    }

    public void setUsrlist(List<Usr> usrlist) {
        this.usrlist = usrlist;
    }

    public List<Usr> getaUsrList() {
        return aUsrList;
    }

    public void setaUsrList(List<Usr> aUsrList) {
        this.aUsrList = aUsrList;
    }

    public Apptn getApptn() {
        return apptn;
    }

    public void setApptn(Apptn apptn) {
        this.apptn = apptn;
    }
    

    //*********** methods ***************
    public List<Todoh> crud(SQLServerDataTable sourceDataTable, Para.ToDoH para, boolean showMsgYN) {

        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh todoh = new Todoh();
                            todoh.setSno(rowNum + 1);
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
                            todoh.setDeptnme(rs.getString("DepartmentName"));                       
                            todoh.setLunme(rs.getString("lunme"));
                            todoh.setAunme(rs.getString("aunme"));
                            todoh.setApplton(rs.getString("AppName"));
                            return todoh;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            todohList = (List<Todoh>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
            if (showMsgYN) {
                //GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            //GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return todohList;
    }

    public void selectLUsr(SelectEvent event) {
        Object o = event.getObject();
        Usr ur = (Usr) o;
        comboLUsr = ur;

    }

    public void selectAUsr(SelectEvent event) {
        Object o = event.getObject();
        Usr ur = (Usr) o;
        comboAUsr = ur;

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

    public void insert() throws SQLException {
        try {
            if (newRecord == true) {
                todoh.setMtid(this.getMtid());
                todoh.setLuid(comboLUsr.getUid());
                todoh.setAuid(comboAUsr.getUid());
                todoh.setDeptid(combodept.getDeptid());
                todoh.setAppid(comboapp.getAppid());
                todoh.setEdate(autoComplete.getpToday());
                this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.Insert, true);
                clear();
                this.Get_SelectGrid();
            } else {
                todoh.setMtid(this.getMtid());             
                todoh.setDeptid(combodept.getDeptid());
                todoh.setAppid(comboapp.getAppid());
                todoh.setLuid(comboLUsr.getUid());
                todoh.setAuid(comboAUsr.getUid());              
                todoh.setEdate(autoComplete.getpToday());
                this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.Update, true);
                
                clear();
                this.Get_SelectGrid();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update() throws SQLException {
        try {
            todoh.setMtid(this.getMtid());
            todoh.setLuid(comboLUsr.getUid());
            todoh.setAuid(comboAUsr.getUid());
            todoh.setDeptid(combodept.getDeptid());
            todoh.setAppid(comboapp.getAppid());
            todoh.setEdate(autoComplete.getpToday());
            this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.Update, true);
            clear();
            this.Get_SelectGrid();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");

    @PostConstruct
    public void init() {
        this.txtptoday = autoComplete.getpToday();
        try{
            this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.SelectByDate, false);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void Get_SelectGrid() {
        try {
            this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.SelectByDate, false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void newTohcode() {
        try {
             newRecord = true;
            clear();
            this.setMtid(get_NewTohCode(false).get(0).getMtid());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Todoh> get_NewTohCode(boolean showMessage) throws SQLException {
      //  List<Todoh> newList=new ArrayList<Todoh>();
        try {
                
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh th = new Todoh();

                            th.setMtid(rs.getString("MTID"));
                            return th;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", todoh.getToDoHTableStructure());
            pr.addValue("TranType", Para.ToDoH.Get_NewMainTaskID);
            pr.addValue("result", "");

            Map mapjt = jdbcCall.execute(pr);
            todohList = (List<Todoh>) mapjt.get("resultSet");
            String result = (String) mapjt.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return todohList;
    }

    public void select() throws SQLException {
       
       todoh = this.selectedTH;
       this.setMtid(todoh.getMtid());
       deptlist = deptDao.selectbyid(todoh.getDeptid());
        if (!deptlist.isEmpty()) {
            String dpnme = deptlist.get(0).getDeptnme();
            combodept.setDeptnme(dpnme);
        }
        this.combodept.setDeptid(todoh.getDeptid());
//         ctList = cboPermtCity.getCityCombo(cboPermtCountry.getCid());
//            autoComplete.setCtList(ctList);
//            cboPermtCity = (Ct) ctConverter.getAsObjectByName(null, null, permtconparts[4].trim());
                
        applist = apptn.getAppCombo(combodept.getDeptid());
        autoComplete.setApptnList(applist);
        comboapp=new Apptn();
       // comboapp=(Apptn)appConverter.getAsObject(null,null,todoh.getApplton().trim()) ;
        if (!applist.isEmpty()) {
            String apnme = (appDao.Select_Name(todoh.getAppid(),combodept.getDeptid())).get(0).getApplton();//applist.get(0).getApplton()
            comboapp.setApplton(apnme);
           // comboapp.setAppid(applist.get(0).getAppid());
        }      
        usrlist = usrDao.getUsrN(todoh.getLuid(),Para.USR.Select_UNAME,false);
        if (!usrlist.isEmpty()) {
            String urnme = usrlist.get(0).getUnme();
            comboLUsr.setUnme(urnme);
        }
        aUsrList = usrDao.getUsrN(todoh.getAuid(),Para.USR.Select_UNAME,false);
        if (!aUsrList.isEmpty()) {
            String urnme = aUsrList.get(0).getUnme();
            comboAUsr.setUnme(urnme);
        }       
        newRecord=false;
      
    }

    public void delete() {
        try {
            todoh = this.selectedTH;
            todoh.setMtid(todoh.getMtid());
            this.todohList = crud(todoh.getToDoHTableStructure(), Para.ToDoH.Delete, false);
            this.Get_SelectGrid();
            clear();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clear() {

        todoh.init();
        this.setMtid(null);

      comboAUsr=new Usr();
        //this.setComboAUsr(null);
      comboLUsr=new Usr();
        //this.setComboLUsr(null);
      combodept=new Dept();
        //this.setCombodept(null);
      comboapp=new Apptn();
        //this.setComboapp(null);
        
    }

    public List<Todoh> Select_Name(String MTID) {
        try {

            Todoh th = new Todoh();
            th.setMtid(MTID);

            SQLServerDataTable thTable = th.getToDoHTableStructure();
            this.todohList = selectByName(thTable, Para.ToDoH.Select_Name, false);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return todohList;
    }

    public List<Todoh> selectByName(SQLServerDataTable sourceDataTable, Para.ToDoH para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh t = new Todoh();

                            t.setDesp(rs.getString("DESP"));

                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoH", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            todohList = (List<Todoh>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return todohList;
    }
}

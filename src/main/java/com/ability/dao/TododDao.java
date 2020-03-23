package com.ability.dao;

import com.ability.model.Apptn;
import com.ability.model.Dept;
import com.ability.model.Jtype;
import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Usr;

import com.ability.util.AutoComplete;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
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
public class TododDao {

    @ManagedProperty("#{todod}")
    private Todod todod;
    private Todoh todoh;
    private Date txtptoday;
    private Jtype comboJtype = new Jtype();
    private Usr comboLUsr = new Usr();
    private Usr comboAUsr = new Usr();
    private Todoh comboth = new Todoh();
    private List<Todod> listTodod = new ArrayList<Todod>();
    private List<Todoh> listTodoh = new ArrayList<Todoh>();
    private JtypeDao jtypeDao = new JtypeDao();
    private List<Jtype> jtypelist = new ArrayList<Jtype>();
    private UsrDao usrDao = new UsrDao();
    private List<Usr> usrlist = new ArrayList<Usr>();
    private Todod selectTodod = new Todod();
    private Todod tod = new Todod();
    private Todoh selectedTD;
    private TodohDao todohDao = new TodohDao();
    private List<Todoh> todohlist = new ArrayList<Todoh>();
    private Boolean newRecord;
    private Boolean selectNew;
    private String txtucode;
    private String txtunme;

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
    
    

    public Todod getTodod() {
        return todod;
    }

    public void setTodod(Todod todod) {
        this.todod = todod;
    }

    public Date getTxtptoday() {
        return txtptoday;
    }

    public void setTxtptoday(Date txtptoday) {
        this.txtptoday = txtptoday;
    }

    public Jtype getComboJtype() {
        return comboJtype;
    }

    public void setComboJtype(Jtype comboJtype) {
        this.comboJtype = comboJtype;
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

    public List<Todod> getListTodod() {
        return listTodod;
    }

    public void setListTodod(List<Todod> listTodod) {
        this.listTodod = listTodod;
    }

    public List<Todoh> getListTodoh() {
        return listTodoh;
    }

    public void setListTodoh(List<Todoh> listTodoh) {
        this.listTodoh = listTodoh;
    }

    public Todod getTod() {
        return tod;
    }

    public void setTod(Todod tod) {
        this.tod = tod;
    }

    public Todod getSelectTodod() {
        return selectTodod;
    }

    public void setSelectTodod(Todod selectTodod) {
        this.selectTodod = selectTodod;
    }

    public Todoh getSelectedTD() {
        return selectedTD;
    }

    public void setSelectedTD(Todoh selectedTD) {
        this.selectedTD = selectedTD;
    }

    public TodohDao getTodohDao() {
        return todohDao;
    }

    public void setTodohDao(TodohDao todohDao) {
        this.todohDao = todohDao;
    }

    public List<Todoh> getTodohlist() {
        return todohlist;
    }

    public void setTodohlist(List<Todoh> todohlist) {
        this.todohlist = todohlist;
    }

    public Todoh getComboth() {
        return comboth;
    }

    public void setComboth(Todoh comboth) {
        this.comboth = comboth;
    }

    public Boolean getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(Boolean newRecord) {
        this.newRecord = newRecord;
    }

    public Todoh getTodoh() {
        return todoh;
    }

    public void setTodoh(Todoh todoh) {
        this.todoh = todoh;
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

    public Boolean getSelectNew() {
        return selectNew;
    }

    public void setSelectNew(Boolean selectNew) {
        this.selectNew = selectNew;
    }
    

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Todod> tododList;
    private List<Todoh> todohList;

    public List<Todod> getTododList() {
        return tododList;
    }

    public void setTododList(List<Todod> tododList) {
        this.tododList = tododList;
    }

    public List<Todoh> getTodohList() {
        return todohList;
    }

    public void setTodohList(List<Todoh> todohList) {
        this.todohList = todohList;
    }

    //*********** methods ***************
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

    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");

    @PostConstruct
    public void init() {
        setSelectNew(true);
        this.txtptoday = autoComplete.getpToday();
        this.txtucode = autoComplete.getUid();
        this.txtunme = autoComplete.getUname();
        //newDTID();
        
    }

    public void mainLostFocus() {
        setSelectNew(false);
        try {
            this.todohList=getDepApp(comboth.getMtid());
            todod.setDtid(get_NewDt(false).get(0).getDtid());
            todod.setDeptnme(todohList.get(0).getDeptnme());
            todod.setApplton(todohList.get(0).getApplton());
            this.tododList = getGrid(comboth.getMtid(),false);           
            
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void Get_SelectGrid() {
        try {
            this.tododList = getGrid(comboth.getMtid(),false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Todod> getGrid(String MtID,boolean showMsg) throws SQLException {
        List<Todod> tododList = new ArrayList<Todod>();
        Todod todo=new Todod();
        todo.setMtid(MtID);
        try {
//            todod.setMtid(MtID);
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
                            todod.setUname(rs.getString("UNME"));
                            todod.setAdate(rs.getDate("ADATE"));
                            todod.setDeptnme(rs.getString("DEPTNME"));
                            todod.setApplton(rs.getString("APPLTON"));
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
                    "tbTpToDoD", todo.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.SelectbyMTID.toString());
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsg) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;

    }
 
    public List<Todoh> getMainCombo(String MtID) throws SQLException {
        try {
            todoh.setMtid(MtID);
            SQLServerDataTable dt = todoh.getToDoHTableStructure();

            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh th = new Todoh();

                            th.setMtid(rs.getString("MTID"));
                            th.setCmpy(rs.getString("CMPY"));
                            th.setCmpdept(rs.getString("CMPDEPT"));
                            return th;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", dt);
            pr.addValue("TranType", Para.ToDoH.Select_LostFocus.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            return (List<Todoh>) mapCt.get("resultSet");
        } catch (SQLServerException ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
    }

    public void selectJtpye(SelectEvent event) {
        Object o = event.getObject();
        Jtype jt = (Jtype) o;
        comboJtype = jt;

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

    public void selectMtid(SelectEvent event) {
        Object o = event.getObject();
        Todoh th = (Todoh) o;
        comboth = th;

    }

    public void delete() {
        todod=this.selectTodod;
        todod.setMtid(todod.getMtid());
        todod.setDtid(todod.getDtid());
        this.tododList=crud(todod.getToDoDTableStructure(),Para.ToDoD.Delete,true);
        this.Get_SelectGrid();
        clear();
    }

    public void select() throws SQLException {

        todod = this.selectTodod;
        jtypelist=jtypeDao.Select_Name(todod.getJtypecd());
         if (!jtypelist.isEmpty()) {
            String dpnme = jtypelist.get(0).getJtypenme();
            comboJtype.setJtypenme(dpnme);
        }
        usrlist = usrDao.getUsrN(todod.getUid(),Para.USR.Select_UNAME,false);
        if (!usrlist.isEmpty()) {
            String urnme = usrlist.get(0).getUnme();
            comboAUsr.setUnme(urnme);
        }
//        todod.setSno(t.getSno());
//        todod.setDtid(t.getDtid());
//        comboth.setDesp(t.getMtid());
//        todohDao.Select_Name(t.getMtid());
//        todohlist = todohDao.getTodohList();
//        if (!todohlist.isEmpty()) {
//            String thnme = todohlist.get(0).getDesp();
//            comboth.setDesp(thnme);
//        }
//        todod.setDtid(t.getDtid());
//        todod.setCmpy(t.getCmpy());
//        todod.setCmpdept(t.getCmpdept());
//        todod.setDesp(t.getDesp());
//        todod.setPtid(t.getPtid());
//        comboJtype.setJtypenme(t.getJtypecd());
//        jtypeDao.Select_Name(t.getJtypecd());
//        jtypelist = jtypeDao.getJtypeList();
//        if (!jtypelist.isEmpty()) {
//            String jtnme = jtypelist.get(0).getJtypenme();
//            comboJtype.setJtypenme(jtnme);
//        }
//        todod.setAdate(t.getAdate());
//        todod.setSdate(t.getSdate());
//        todod.setTfdate(t.getTfdate());
//        todod.setEminute(t.getEminute());
//        comboAUsr.setUnme(t.getUid());
//        usrDao.Select_Name(t.getUid());
//        usrlist = usrDao.getUsrList();
//        if (!usrlist.isEmpty()) {
//            String urnme = usrlist.get(0).getUnme();
//            comboAUsr.setUnme(urnme);
//        }
//        todod.setAchead(t.getAchead());
//        todod.setBudamto(t.getBudamto());
//        todod.setBudamtc(t.getBudamtc());
//        todod.setRemark(t.getRemark());
        newRecord = false;
    }

    public void newDTID() {
        try {
            newRecord = true;
            clear();
            //todod.setMtid(comboth.getMtid());
            todod.setDtid(get_NewDt(false).get(0).getDtid());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Todod> get_NewDt(boolean showMessage) throws SQLException {
        todod.setMtid(comboth.getMtid());
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod td = new Todod();
                            td.setDtid(rs.getString("DTID"));
                            return td;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoD", todod.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.Get_NewDT);
            pr.addValue("result", "");

            Map mapjt = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapjt.get("resultSet");
            String result = (String) mapjt.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    }

    public void submit_click() throws SQLServerException {
        try {

            if (newRecord == true) {
                if(comboth==null ){
                     
                    GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill Main Task ");
                    return;
                }
                else if(comboJtype==null){
                    GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill  job type.");
                    return;
                }
                else if(comboAUsr==null){
                    GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill  Assign User");
                    return;
                }
//                if(comboth.getMtid() == null || comboth.getMtid().trim().equals("") || comboth.getMtid().trim().isEmpty()) {
//                    GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Name.");
//                    return;
//                 }
//                 if(comboth.getMtid() == null || comboth.getMtid().trim().equals("") || comboth.getMtid().trim().isEmpty()) {
//                    GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Name.");
//                    return;
//                 }
                todod.setMtid(comboth.getMtid());
                todod.setJtypecd(comboJtype.getJtypecd());
                todod.setUid(comboAUsr.getUid());
                crud(todod.getToDoDTableStructure(), Para.ToDoD.Insert, false);
                Get_SelectGrid();
                AllClear();

            } else {

                todod.setMtid(comboth.getMtid());
                todod.setDtid(todod.getDtid());
                todod.setJtypecd(comboJtype.getJtypecd());
                todod.setUid(comboAUsr.getUid());
                tododList = crud(todod.getToDoDTableStructure(), Para.ToDoD.Update, false);
                Get_SelectGrid();
                AllClear();
                
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Todod> Select_Name(String DTID,String MTID) {
        Todod td = new Todod();
        td.setDtid(DTID);
        td.setMtid(MTID);
        SQLServerDataTable tdTable = td.getToDoDTableStructure();
        this.tododList = selectByName(tdTable, Para.ToDoD.Select_Name, false);
        return tododList;
    }

    public List<Todod> selectByName(SQLServerDataTable sourceDataTable, Para.ToDoD para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod t = new Todod();

                            t.setDesp(rs.getString("DESP"));

                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN==true) {
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
       // this.setComboth(null);
        this.setSelectNew(true);
        this.setComboAUsr(null);
        this.setComboJtype(null);
        //comboth=new Todoh();
        comboAUsr=new Usr();
        comboJtype=new Jtype();        
    }
    public void AllClear(){
        todod.init();
        todod.setDeptnme("");
        todod.setApplton("");
        this.setSelectNew(true);
        this.setComboth(null);
        this.setComboAUsr(null);
        this.setComboJtype(null);
        comboth=new Todoh();
        comboAUsr=new Usr();
        comboJtype=new Jtype(); 
    }

   public List<Todod> upDateStatus(String dtid,String mainTaskId,String status,boolean showMsgYN) {
       List<Todod> tododList = new ArrayList<Todod>();
       Todod detail = new Todod();
       detail.setDtid(dtid);
       detail.setMtid(mainTaskId);
       detail.setStatus(status);
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
            pr.addValue("TranType",Para.ToDoD.Update_st);
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN==true) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    } 
  public List<Todod> upDateHeaderStatus(String dtid,String mainTaskId,boolean showMsgYN) {
       List<Todod> tododList = new ArrayList<Todod>();
       Todod detail = new Todod();
       detail.setDtid(dtid);
       detail.setMtid(mainTaskId);
      
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
            pr.addValue("TranType",Para.ToDoD.sec_fin);
            pr.addValue("result", "");

            Map mapTodod = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapTodod.get("resultSet");
            String result = (String) mapTodod.get("result");
            if (showMsgYN==true) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    } 
   public List<Todod> selectByMtid(String dtid,String Mtid) {
       Todod detail=new Todod();
       detail.setDtid(dtid);
       detail.setMtid(Mtid);
        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod t = new Todod();
                            t.setDtid(rs.getString("DTID"));
                            t.setDesp(rs.getString("DESP"));

                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", detail.getToDoDTableStructure());
            pr.addValue("TranType", Para.ToDoD.SelectbyMTDTID.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            tododList = (List<Todod>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
           
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return tododList;
    }
   
   
   
   public List<Todoh> getDepApp(String mainTask){
       List<Todoh> todoList=new ArrayList<Todoh>();
       Todoh detail=new Todoh();      
       detail.setMtid(mainTask);
        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh t = new Todoh();
                            t.setDeptnme(rs.getString("DepartmentName"));
                            t.setApplton(rs.getString("AppName"));
                           return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoH", detail.getToDoHTableStructure());
            pr.addValue("TranType", Para.ToDoH.SelectDA.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            todoList = (List<Todoh>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
           
        } catch (Exception ex) {
            
            throw new RuntimeException(ex.toString());
        }
        
       return todoList;
   }

    
}

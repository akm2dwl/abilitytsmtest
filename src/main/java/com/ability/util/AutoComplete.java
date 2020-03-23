package com.ability.util;

import com.ability.dao.Db;
import com.ability.dao.UsrDao;
import com.ability.model.Apptn;
import com.ability.model.Dept;
import com.ability.model.Jtype;
import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Usr;
import java.io.IOException;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@ManagedBean
@SessionScoped
public class AutoComplete {

    private String userName;
    private String userId;
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private Usr usr = new Usr();
    private Usr comboUsr = new Usr();
    private UsrDao usrDao = new UsrDao();
    private Dept dept = new Dept();
    private Dept comboDept = new Dept();
    private List<Dept> deptList = new ArrayList<Dept>();
    private Dept cboDept;

    private Apptn apptn = new Apptn();
    private Apptn comboApptn = new Apptn();
    private List<Apptn> apptnList = new ArrayList<Apptn>();
    private Apptn cboApptn;

    private Jtype jtype = new Jtype();
    private Jtype comboJt = new Jtype();
    private List<Jtype> jtList = new ArrayList<Jtype>();

    private Todoh todoh = new Todoh();
    private Todoh comboth = new Todoh();
    private List<Todoh> thList = new ArrayList<Todoh>();
    private Todoh cboToh;

    private Todod todod = new Todod();
    private Todod combotd = new Todod();
    private List<Todod> tdList = new ArrayList<Todod>();

    public Apptn getCboApptn() {
        return cboApptn;
    }

    public void setCboApptn(Apptn cboApptn) {
        this.cboApptn = cboApptn;
    }

    public Apptn getApptn() {
        return apptn;
    }

    public void setApptn(Apptn apptn) {
        this.apptn = apptn;
    }

    public Apptn getComboApptn() {
        return comboApptn;
    }

    public void setComboApptn(Apptn comboApptn) {
        this.comboApptn = comboApptn;
    }

    public List<Apptn> getApptnList() {
        return apptnList;
    }

    public void setApptnList(List<Apptn> apptnList) {
        this.apptnList = apptnList;
    }

    public Dept getComboDept() {
        return comboDept;
    }

    public void setComboDept(Dept comboDept) {
        this.comboDept = comboDept;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Usr getComboUsr() {
        return comboUsr;
    }

    public void setComboUsr(Usr comboUsr) {
        this.comboUsr = comboUsr;
    }

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }

    private java.util.Date pToday;

    public Date getpToday() {
        return pToday;
    }

    public void setpToday(Date pToday) {
        this.pToday = pToday;
    }

    public List<Dept> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Dept> deptList) {
        this.deptList = deptList;
    }

    public Dept getCboDept() {
        return cboDept;
    }

    public void setCboDept(Dept cboDept) {
        this.cboDept = cboDept;
    }

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uname;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Jtype getJtype() {
        return jtype;
    }

    public void setJtype(Jtype jtype) {
        this.jtype = jtype;
    }

    public Jtype getComboJt() {
        return comboJt;
    }

    public void setComboJt(Jtype comboJt) {
        this.comboJt = comboJt;
    }

    public List<Jtype> getJtList() {
        return jtList;
    }

    public void setJtList(List<Jtype> jtList) {
        this.jtList = jtList;
    }

    public Todoh getTodoh() {
        return todoh;
    }

    public void setTodoh(Todoh todoh) {
        this.todoh = todoh;
    }

    public Todoh getComboth() {
        return comboth;
    }

    public void setComboth(Todoh comboth) {
        this.comboth = comboth;
    }

    public List<Todoh> getThList() {
        return thList;
    }

    public void setThList(List<Todoh> thList) {
        this.thList = thList;
    }

    public Todod getTodod() {
        return todod;
    }

    public void setTodod(Todod todod) {
        this.todod = todod;
    }

    public Todod getCombotd() {
        return combotd;
    }

    public Todoh getCboToh() {
        return cboToh;
    }

    public void setCboToh(Todoh cboToh) {
        this.cboToh = cboToh;
    }

    public void setCombotd(Todod combotd) {
        this.combotd = combotd;
    }

    public List<Todod> getTdList() {
        return tdList;
    }

    public void setTdList(List<Todod> tdList) {
        this.tdList = tdList;
    }

    //******************DataSource**********************//
    DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //******************Constructor**********************
    public AutoComplete() {

    }

    //******************init**********************
    @PostConstruct
    public void init() {
//        uid = "003";
//        uname = "T3";
        ds = Db.getSQLDataSource();
        this.pToday = Util.sqlDate(new Date());
    }

    //************User Combo*************************//    
    public List<Usr> filterUserCombo(String query) throws SQLException {
        List<Usr> urs = usr.select();
        if (urs != null) {
            List<Usr> filteredCombo = new ArrayList<Usr>();
            for (int i = 0; i < urs.size(); i++) {
                Usr combo = urs.get(i);
                if (combo.getUnme().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    //************Jytpe Combo*************************//    
    public List<Jtype> filterJtypeCombo(String query) throws SQLException {
        List<Jtype> jt = jtype.select();
        if (jt != null) {
            List<Jtype> filteredCombo = new ArrayList<Jtype>();
            for (int i = 0; i < jt.size(); i++) {
                Jtype combo = jt.get(i);
                if (combo.getJtypenme().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    //************Todoheader Combo*************************//    
    public List<Todoh> filterTodohCombo(String query) throws SQLException {
        List<Todoh> th = todoh.select();
        if (th != null) {
            List<Todoh> filteredCombo = new ArrayList<Todoh>();
            for (int i = 0; i < th.size(); i++) {
                Todoh combo = th.get(i);
                if (combo.getDesp().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    //************Department Combo*************************//    
    public List<Dept> filterDepartmentCombo(String query) throws SQLException {

        List<Dept> crs = dept.select();

        if (crs != null) {
            List<Dept> filteredCombo = new ArrayList<Dept>();

            for (int i = 0; i < crs.size(); i++) {
                Dept combo = crs.get(i);
                if (combo.getDeptnme().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    //************Application Combo*************************//    
    public List<Apptn> filterAppCombo(String query) throws SQLException {

        apptnList = apptn.getAppCombo(cboDept.getDeptid());

        if (apptnList != null) {
            List<Apptn> filteredCombo = new ArrayList<Apptn>();

            for (int i = 0; i < apptnList.size(); i++) {
                Apptn combo = apptnList.get(i);
                if (combo.getApplton().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    //************ToDoDetail Combo*************************//    
    public List<Todod> filterTododCombo(String query) throws SQLException {

        tdList = todod.getTodCombo(comboth.getMtid());

        if (tdList != null) {
            List<Todod> filteredCombo = new ArrayList<Todod>();

            for (int i = 0; i < tdList.size(); i++) {
                Todod combo = tdList.get(i);
                if (combo.getDesp().toLowerCase().startsWith(query)) {
                    filteredCombo.add(combo);
                }
            }
            return filteredCombo;
        } else {
            return null;
        }
    }

    public void Login() {
        try {
            //            if (!validateUser()) {
//                GrowlView.saveMessage("Invalid user!");
//                return;
//            }
            pToday = new Date();
            //List<Usr> userExit=usrDao.LoginAccess(usr, uid)
            FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(AutoComplete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loginTest() {
        List<Usr> userList = LoginAccess(this.getUserId(), this.getPwd());
        if (userList.size() > 0) {
            try {

                ////           }
                pToday = new Date();
                this.userName=userList.get(0).getUnme();
                FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AutoComplete.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));

        }
    }
    public void logout() throws IOException{
         FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }

    public List<Usr> LoginAccess(String usrId, String password) {
        Usr user = new Usr();
        user.setUid(usrId);
        user.setPswd(password);
        List<Usr> userList = new ArrayList<Usr>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Usr>() {
                        @Override
                        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usr usr = new Usr();

                            usr.setUnme(rs.getString("UNME"));
                            usr.setUid(rs.getString("UID"));

                            return usr;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpUSR", user.getUSRTableStructure());
            pr.addValue("TranType", Para.USR.login.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            userList = (List<Usr>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return userList;
    }

}

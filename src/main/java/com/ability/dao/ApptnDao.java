package com.ability.dao;

import com.ability.model.Apptn;
import com.ability.model.Dept;
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
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ApptnDao {

    @ManagedProperty("#{apptn}")
    private Apptn apptn;
    private boolean newRecord;
    private Dept combodept = new Dept();
    private List<Apptn> appList;
    private Apptn selectedApptn;

    public Apptn getApptn() {
        return apptn;
    }

    public void setApptn(Apptn apptn) {
        this.apptn = apptn;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public Dept getCombodept() {
        return combodept;
    }

    public void setCombodept(Dept combodept) {
        this.combodept = combodept;
    }

    public List<Apptn> getAppList() {
        return appList;
    }

    public void setAppList(List<Apptn> appList) {
        this.appList = appList;
    }

    public Apptn getSelectedApptn() {
        return selectedApptn;
    }

    public void setSelectedApptn(Apptn selectedApptn) {
        this.selectedApptn = selectedApptn;
    }

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Apptn> apList = new ArrayList<Apptn>();

    public List<Apptn> getApList() {
        return apList;
    }

    public void setApList(List<Apptn> apList) {
        this.apList = apList;
    }

    //*********** methods ***************
    public List<Apptn> crud(SQLServerDataTable sourceDataTable, Para.APPTN para, boolean showMsgYN) {

        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Apptn>() {
                        @Override
                        public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Apptn apptn = new Apptn();
                            
                            apptn.setSno(rowNum+1);
                            apptn.setAppid(rs.getString("APPID"));
                            apptn.setDeptid(rs.getString("DEPTID"));
                            apptn.setApplton(rs.getString("APPLTON"));

                            return apptn;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpAPPTN", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            apList = (List<Apptn>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return apList;
    }

    public List<Apptn> crudd(Para.APPTN para, boolean showMsgYN) throws SQLServerException {
        SQLServerDataTable appTable = apptn.getAPPTNTableStructure();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Apptn>() {
                        @Override
                        public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Apptn apptn = new Apptn();

                            apptn.setAppid(rs.getString("APPID"));
                            apptn.setDeptid(rs.getString("DEPTID"));
                            apptn.setApplton(rs.getString("APPLTON"));

                            return apptn;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpAPPTN", appTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            appList = (List<Apptn>) mapCt.get("resultSet");
            String result = (String) mapCt.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return appList;
    }

    public void newApcode() {
        try {
            newRecord = true;
            clear();
            apptn.setDeptid(combodept.getDeptid());
            this.setCombodept(this.getCombodept());
            apptn.setAppid(get_NewApCode(false).get(0).getAppid());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Apptn> get_NewApCode(boolean showMessage) throws SQLException {
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Apptn>() {
                        @Override
                        public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Apptn ap = new Apptn();
                            ap.setAppid((rs.getString("APPID")));
                            return ap;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpAPPTN", apptn.getAPPTNTableStructure());
            pr.addValue("TranType", Para.APPTN.Get_NewAppID);
            pr.addValue("result", "");

            Map mapap = jdbcCall.execute(pr);
            appList = (List<Apptn>) mapap.get("resultSet");
            String result = (String) mapap.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return appList;
    }

    public void insert() throws SQLException {
        try {
            if (newRecord == true) {
                this.appList = crud(apptn.getAPPTNTableStructure(), Para.APPTN.Insert, false);
                this.Get_SelectGrid();
                clear();
            } else {

                apptn.setAppid(apptn.getAppid());
                this.appList = crud(apptn.getAPPTNTableStructure(), Para.APPTN.Update, false);
                this.Get_SelectGrid();
                clear();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void Get_SelectGrid() {
        try {
            apptn.setDeptid(combodept.getDeptid());
            this.appList = crud(apptn.getAPPTNTableStructure(), Para.APPTN.Select_Grid, false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void select() {

        apptn = this.selectedApptn;
        newRecord = false;
    }

    public void delete() {
        try {
            apptn = this.selectedApptn;
            //apptn.setDeptid(combodept.getDeptid());
            apptn.setDeptid(apptn.getDeptid());
            apptn.setAppid(apptn.getAppid());
            this.appList = crudd(Para.APPTN.Delete, true);
            this.Get_SelectGrid();
            clear();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clear() {

        apptn.init();
    }

    public void exit() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("bankdashboard.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ApptnDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectDept(SelectEvent event) {
        Object o = event.getObject();
        combodept = (Dept) o;

        if (this.combodept.toString().trim() == null || this.combodept.toString().trim().isEmpty() || this.combodept.toString().trim().equals("")) {
            GrowlView.showMsg("Department is Required");
            return;

        }
        this.Get_SelectGrid();

    }

    public List<Apptn> Select_Name(String APPID,String deptid) {
        try {

            Apptn ap = new Apptn();

            ap.setAppid(APPID);
            ap.setDeptid(deptid);
            SQLServerDataTable apTable = ap.getAPPTNTableStructure();
            this.appList = selectByName(apTable, Para.APPTN.Select_Name, false);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return appList;
    }

    public List<Apptn> selectByName(SQLServerDataTable sourceDataTable, Para.APPTN para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_APPTN")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Apptn>() {
                        @Override
                        public Apptn mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Apptn ap = new Apptn();

                            ap.setApplton(rs.getString("APPLTON"));

                            return ap;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpAPPTN", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            appList = (List<Apptn>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return appList;
    }

}

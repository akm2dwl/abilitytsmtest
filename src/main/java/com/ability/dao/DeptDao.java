package com.ability.dao;

import com.ability.model.Dept;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name="deptDao")
@ViewScoped
public class DeptDao {

    @ManagedProperty("#{dept}")
    private Dept dept;
     public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    private boolean newRecord;
    private Dept selecteddp;
    private String deptid;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

   
    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public Dept getSelecteddp() {
        return selecteddp;
    }

    public void setSelecteddp(Dept selecteddp) {
        this.selecteddp = selecteddp;
    }
    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Dept> deptList;

    public List<Dept> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Dept> deptList) {
        this.deptList = deptList;
    }

    List<Dept> dtList = new ArrayList<Dept>();

    public List<Dept> getDtList() {
        return dtList;
    }

    public void setDtList(List<Dept> dtList) {
        this.dtList = dtList;
    }

    //*********** methods ***************
    public List<Dept> crud(SQLServerDataTable sourceDataTable, Para.DEPT para, boolean showMsgYN) {
        List<Dept> deptList = new ArrayList<Dept>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept dept = new Dept();
                            dept.setSno(rowNum + 1);
                            dept.setDeptid(rs.getString("DEPTID"));
                            dept.setDeptnme(rs.getString("DEPTNME"));
                            dept.setCmpy(rs.getString("CMPY"));
                            dept.setCmpdept(rs.getString("CMPDEPT"));

                            return dept;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpDEPT", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            deptList = (List<Dept>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return deptList;
    }

    public void newDeptId() {
        try {
            newRecord = true;
            clear();
            dept.setDeptid(get_NewDpID(false).get(0).getDeptid()); //why getdeptid ? need ?
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Dept> get_NewDpID(boolean showMessage) throws SQLException {
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept dp = new Dept();
                            dp.setDeptid(rs.getString("DEPTID"));
                            return dp;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpDept", dept.getDEPTTableStructure());
            pr.addValue("TranType", Para.DEPT.Get_NewDept);
            pr.addValue("result", "");

            Map mapjt = jdbcCall.execute(pr);
            dtList = (List<Dept>) mapjt.get("resultSet");
            String result = (String) mapjt.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return dtList;

    }

    public void insert() throws SQLException {
        try {
            if (newRecord == true) {
                this.deptList = crud(dept.getDEPTTableStructure(), Para.DEPT.Insert, true);
                clear();
                this.Get_SelectGrid();
            } else {

                dept.setDeptid(dept.getDeptid());

                this.deptList = crud(dept.getDEPTTableStructure(), Para.DEPT.Update, true);
                clear();
                this.Get_SelectGrid();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void select() {

        //dept = this.selecteddp;
        dept.setCmpy(this.selecteddp.getCmpy());
        dept.setCmpdept(this.selecteddp.getCmpdept());
        dept.setDeptid(this.selecteddp.getDeptid());
        dept.setDeptnme(this.selecteddp.getDeptnme());
        newRecord = false;
    }

    public void delete() {
        try {
            dept = this.selecteddp;
            dept.setDeptid(dept.getDeptid());
            this.deptList = crud(dept.getDEPTTableStructure(), Para.DEPT.Delete, true);
            this.Get_SelectGrid();
            clear();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PostConstruct
    public void init() {
        try {
            this.deptList = crud(dept.getDEPTTableStructure(), Para.DEPT.Select, false);
        } catch (SQLServerException ex) {
            Logger.getLogger(DeptDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Get_SelectGrid() {
        try {
            this.deptList = crud(dept.getDEPTTableStructure(), Para.DEPT.Select, false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clear() {

        dept.init();
    }

    public List<Dept> selectbyid(String DEPTID) throws SQLException {
        try {
            Dept dep = new Dept();
            dep.setDeptid(DEPTID);
            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept d = new Dept();
                            d.setDeptid(rs.getString("DEPTID"));
                            d.setDeptnme(rs.getString("DEPTNME"));
                            d.setCmpy(rs.getString("CMPY"));
                            d.setCmpdept(rs.getString("CMPDEPT"));
                            return d;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpDEPT", dep.getDEPTTableStructure());
            pr.addValue("TranType", Para.DEPT.Selects.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            return (List<Dept>) mapCt.get("resultSet");
        } catch (SQLServerException ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
    }

    public List<Dept> Select_Name(String DEPTID) {
        try {

            Dept dp = new Dept();

            dp.setDeptid(DEPTID);

            SQLServerDataTable dpTable = dp.getDEPTTableStructure();
            this.deptList = selectByName(dpTable, Para.DEPT.Select_Name, false);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return deptList;
    }

    public List<Dept> selectByName(SQLServerDataTable sourceDataTable, Para.DEPT para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_DEPT")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Dept>() {
                        @Override
                        public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Dept d = new Dept();

                            d.setDeptnme(rs.getString("DEPTNME"));

                            return d;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpDEPT", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            deptList = (List<Dept>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return deptList;
    }

}

package com.ability.dao;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped

public class JtypeDao {

    @ManagedProperty("#{jtype}")
    private Jtype jtype = new Jtype();

    public Jtype getJtype() {
        return jtype;
    }

    public void setJtype(Jtype jtype) {
        this.jtype = jtype;
    }
    
    private String jtypecd;
    private boolean newRecord;
    private Jtype selectedJt;
    private Jtype jty = new Jtype();

    public Jtype getSelectedJt() {
        return selectedJt;
    }

    public void setSelectedJt(Jtype selectedJt) {
        this.selectedJt = selectedJt;
    }

    public String getJtypecd() {
        return jtypecd;
    }

    public void setJtypecd(String jtypecd) {
        this.jtypecd = jtypecd;
    }

   

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public Jtype getJty() {
        return jty;
    }

    public void setJty(Jtype jty) {
        this.jty = jty;
    }

    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;

    //*********** model related code ***************
    private List<Jtype> jtypeList;

    public List<Jtype> getJtypeList() {
        return jtypeList;
    }

    public void setJtypeList(List<Jtype> jtypeList) {
        this.jtypeList = jtypeList;
    }
    
    List<Jtype> jtList=new ArrayList<Jtype>();

    public List<Jtype> getJtList() {
        return jtList;
    }

    public void setJtList(List<Jtype> jtList) {
        this.jtList = jtList;
    }
    
    

    //*********** methods ***************
    public List<Jtype> crud(SQLServerDataTable sourceDataTable, Para.JTYPE para, boolean showMsgYN) {
        
        try {
            ds = Db.getSQLDataSource();
         
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype jtype = new Jtype();
                            jtype.setSno(rowNum+1);
                            jtype.setJtypecd(rs.getString("JTYPECD"));
                            jtype.setJtypenme(rs.getString("JTYPENME"));
                            jtype.setCmpy(rs.getString("CMPY"));
                            jtype.setCmpdept(rs.getString("CMPDEPT"));
                       
                            return jtype;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpJTYPE", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            jtypeList = (List<Jtype>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }

        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return jtypeList;
    }

    public void newJtcode() {
        try {
            newRecord = true;
            clear();
            this.setJtypecd(get_NewJtCode(false).get(0).getJtypecd());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Jtype> get_NewJtCode(boolean showMessage) throws SQLException {
        try {
            
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype jr = new Jtype();
                            
                            jr.setJtypecd(rs.getString("JTYPECD"));
                            return jr;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpJTYPE", jtype.getJTYPETableStructure());
            pr.addValue("TranType", Para.JTYPE.Get_NewJtype);
            pr.addValue("result", "");

            Map mapjt = jdbcCall.execute(pr);
            jtList = (List<Jtype>) mapjt.get("resultSet");
            String result = (String) mapjt.get("result");
            if (showMessage) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
        return jtList;
    }

    public void insert() throws SQLException {
        try {
            if (newRecord == true) {
               
                this.jtypeList = crud(jtype.getJTYPETableStructure(), Para.JTYPE.Insert, false);
                clear();
                this.Get_SelectGrid();
            } else {

                jtype.setJtypecd(jtype.getJtypecd());
                this.jtypeList = crud(jtype.getJTYPETableStructure(), Para.JTYPE.Update, false);
                clear();
                this.Get_SelectGrid();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @PostConstruct
    public void init() {
        try {
            this.jtypeList = crud(jtype.getJTYPETableStructure(), Para.JTYPE.Select, false);
//            jty.setSno(1);
//            jty.setSno(jtypeList.size() + 1);
//            jtypeList.add(jty);
        } catch (SQLServerException ex) {
            Logger.getLogger(JtypeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Get_SelectGrid() {
        try {
            this.jtypeList = crud(jtype.getJTYPETableStructure(), Para.JTYPE.Select, false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
   

    public List<Jtype> getUsrGrid(String JCD) throws SQLException {
        try {
            jtype.setJtypecd(JCD);
            SQLServerDataTable dt = jtype.getJTYPETableStructure();

            DataSource ds = Db.getSQLDataSource();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype jt = new Jtype();

                            jt.setCmpdept(rs.getString("CMPDEPT"));
                            jt.setCmpy(rs.getString("CMPY"));
                            jt.setJtypecd(rs.getString("JTYPECD"));
                            jt.setJtypenme(rs.getString("JTYPENME"));

                            return jt;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpJTYPE", dt);
            pr.addValue("TranType", Para.USR.Selects.toString());
            pr.addValue("result", "");

            Map mapCt = jdbcCall.execute(pr);
            return (List<Jtype>) mapCt.get("resultSet");
        } catch (SQLServerException ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
    }

    public void select() {

        //Jtype j = this.selectedJt;
        jtype.setCmpy(this.selectedJt.getCmpy());
        jtype.setCmpdept(this.selectedJt.getCmpdept());
        this.setJtypecd(this.selectedJt.getJtypecd());
        jtype.setJtypenme(this.selectedJt.getJtypenme());        
        newRecord = false;
    }

    public void delete() {
        try {
                jtype=this.selectedJt;
                jtype.setJtypecd(jtype.getJtypecd());
                this.jtypeList=crud(jtype.getJTYPETableStructure(), Para.JTYPE.Delete, true);
                Get_SelectGrid();
                clear();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clear() {
        jtype.init();
    }

    public List<Jtype> Select_Name(String JTYPECD) {
        try {

            Jtype jt = new Jtype();

            jt.setJtypecd(JTYPECD);

            SQLServerDataTable jtTable = jt.getJTYPETableStructure();
            this.jtypeList = selectByName(jtTable, Para.JTYPE.Select_Name, false);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return jtypeList;
    }

    public List<Jtype> selectByName(SQLServerDataTable sourceDataTable, Para.JTYPE para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_JTYPE")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Jtype>() {
                        @Override
                        public Jtype mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Jtype j = new Jtype();

                            j.setJtypenme(rs.getString("JTYPENME"));

                            return j;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpJTYPE", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            jtypeList = (List<Jtype>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return jtypeList;
    }
}

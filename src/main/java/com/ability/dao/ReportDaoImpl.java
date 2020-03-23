/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.dao;

import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Wdone;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 *
 * @author AbilityJAVA
 */
public class ReportDaoImpl {
    DataSource ds;
    SimpleJdbcCall jdbcCall;
     public List<Todoh> getHeaderList(Para.ToDoH para,SQLServerDataTable dataTable) {
        List<Todoh> todohList = new ArrayList<Todoh>();
        try {
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoH")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todoh>() {
                        @Override
                        public Todoh mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todoh todoh = new Todoh();

                            todoh.setMtid(rs.getString("MTID"));
                            todoh.setEdate(rs.getDate("EDATE"));
                            todoh.setDesp(rs.getString("DESP"));
                            todoh.setCmpy(rs.getString("CMPY"));
                            todoh.setDeptid(rs.getString("DEPTID"));
                            todoh.setDeptnme(rs.getString("DepartmentName"));
                            todoh.setApplton(rs.getString("AppName"));
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

                            return todoh;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpToDoH", dataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            todohList = (List<Todoh>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return todohList;
    }
      public List<Todod> selectDetail(SQLServerDataTable tableStructure) {
       List<Todod> todoList=new ArrayList<Todod>();
        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_ToDoD")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Todod>() {
                        @Override
                        public Todod mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Todod t = new Todod();
                            t.setDtid(rs.getString("DTID"));
                            t.setMtid(rs.getString("MTID"));
                            t.setDesp(rs.getString("DESP"));
                            t.setAfdate(rs.getDate("ADATE"));
                            t.setTfdate(rs.getDate("TFDATE"));
                            t.setStatus(rs.getString("STATUS"));
                            t.setEminute(rs.getInt("EMINUTE"));
                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpToDoD", tableStructure);
            pr.addValue("TranType", Para.ToDoD.Select_By_Mid.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            todoList = (List<Todod>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return todoList;
    }
    public List<Wdone> getWdoneListDetail(SQLServerDataTable tableStructure){
        List<Wdone> wdoneList=new ArrayList<Wdone>();
        
       try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_Wdone")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Wdone>() {
                        @Override
                        public Wdone mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Wdone t = new Wdone();
                            t.setUid(rs.getString("UID"));
                            t.setWdate(rs.getDate("WDATE"));
                            t.setDetailName(rs.getString("detail"));
                            t.setSno(rs.getString("SNO"));
                            t.setMtid(rs.getString("MTID"));
                            t.setDtid(rs.getString("DTID"));
                            t.setDesp(rs.getString("DESP"));
                            t.setDeptid(rs.getString("DEPTID"));
                            t.setAppid(rs.getString("APPID"));
                            t.setCmpy(rs.getString("CMPY"));
                            t.setCmpdept(rs.getString("CMPDEPT"));
                            t.setFtime(rs.getDate("FTIME"));
                            t.setTtime(rs.getDate("TTIME"));
                            t.setMinutes(rs.getLong("MINUTES"));
                            t.setJtypecd(rs.getString("JTYPECD"));
                            t.setPlur(rs.getString("PLUR"));
                            t.setRemark(rs.getString("REMARK"));
                            t.setStatus(rs.getString("STATUS"));
                            t.setMgmremk(rs.getString("MGMREMK"));
                            t.setuName(rs.getString("UNME"));
                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpWdone", tableStructure);
            pr.addValue("TranType", Para.Wdone.selectby.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            wdoneList = (List<Wdone>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return wdoneList;
    }
    public ReportDaoImpl(){
        
    } 
    public void throwException(String message) throws Exception {
        throw new Exception(message);
    }
}

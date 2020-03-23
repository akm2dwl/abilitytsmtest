/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.dao;

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
public class WorkDoneDao {
    DataSource ds;
    SimpleJdbcCall jdbcCall; 
    public List<Wdone> getWorkDoneReport(SQLServerDataTable dataTable){
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
                            t.setDeptnme(rs.getString("DEPTNME"));
                            t.setApplton(rs.getString("APPLTON"));
                            t.setFromTime(rs.getString("FromTime"));
                            t.setToTime(rs.getString("ToTime"));
                            return t;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpWdone", dataTable);
            pr.addValue("TranType", Para.Wdone.select_byDate.toString());
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
     public WorkDoneDao(){
        
    } 
           
}

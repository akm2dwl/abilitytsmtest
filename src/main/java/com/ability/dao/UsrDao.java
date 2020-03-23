package com.ability.dao;

import com.ability.model.Usr;
import com.ability.util.AutoComplete;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import javax.faces.bean.ManagedBean;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

@ManagedBean
@ViewScoped
public class UsrDao {
    
    @ManagedProperty("#{usr}")
    private Usr usr;
   public boolean selectBtn;
    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }
    
    //*********** database related code ***************
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;
    
    
      //*********** model related code ***************
    private List<Usr> usrList;
    private boolean newRecord;
    private String uid;
    private String userId;
    private String cpwd;
    private String opswd;
    private String tpw;
    private String pswd;
    private String uname;
    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
   

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getTpw() {
        return tpw;
    }

    public void setTpw(String tpw) {
        this.tpw = tpw;
    }
    
    private Usr selectedUr;

    public String getCpwd() {
        return cpwd;
    }

    public void setCpwd(String cpwd) {
        this.cpwd = cpwd;
    }  

    public String getOpswd() {
        return opswd;
    }

    public void setOpswd(String opswd) {
        this.opswd = opswd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public Usr getSelectedUr() {
        //this.selectBtn=true;
        return selectedUr;
    }

    public void setSelectedUr(Usr selectedUr) {
        this.selectedUr = selectedUr;
    }   
    

    public List<Usr> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<Usr> usrList) {
        this.usrList = usrList;
    }
    
    private List<Usr> urlist= new ArrayList<Usr>();

    public List<Usr> getUrlist() {
        return urlist;
    }

    public void setUrlist(List<Usr> urlist) {
        this.urlist = urlist;
    }

    public boolean isSelectBtn() {
        return selectBtn;
    }

    public void setSelectBtn(boolean selectBtn) {
        this.selectBtn = selectBtn;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    //*********** methods ***************
    public List<Usr> crud(SQLServerDataTable sourceDataTable,Para.USR para,boolean showMsgYN)  {
        List<Usr> usrList = new ArrayList<Usr>();
        try{
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                .returningResultSet("resultSet",
                new ParameterizedBeanPropertyRowMapper<Usr>() {
                    @Override
                    public Usr mapRow(ResultSet rs, int rowNum)throws SQLException {
                        Usr usr = new Usr();
                       
                            usr.setUid(rs.getString("UID"));
                            usr.setUnme(rs.getString("UNME"));
                            usr.setCmpy(rs.getString("CMPY"));
                            usr.setCmpdept(rs.getString("CMPDEPT"));
                            usr.setPswd(rs.getString("PSWD"));
                            usr.setRk(rs.getString("RK"));
                            usr.setMphno(rs.getString("MPHNO"));
                            usr.setEmail(rs.getString("EMAIL")); 
                            usr.setPswdcdt(rs.getDate("PSWDCDT"));
                            
                        return usr;
                    }
                });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                "tbTpUSR", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapJtype = jdbcCall.execute(pr);
            usrList = (List<Usr>) mapJtype.get("resultSet");
            String result = (String) mapJtype.get("result");
            if(showMsgYN)
                GrowlView.saveMessage(result);

        }
        catch(Exception ex){
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return usrList;
    }
    
    
    public void newUsrcode()  { 
        try {
                newRecord=true;
                clear();
                usr.setUid(get_NewUsrCode(false).get(0).getUid());
                
            } catch (Exception ex) {
               throw new RuntimeException(ex);
            }
    }
    
    public List<Usr> get_NewUsrCode(boolean showMessage) throws SQLException {
        try{
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                    .returningResultSet("resultSet", new ParameterizedBeanPropertyRowMapper<Usr>() {
                        @Override
                        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usr ur = new Usr();
                            ur.setUid(rs.getString("UID"));
                        return ur;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpUSR", usr.getUSRTableStructure());
            pr.addValue("TranType", Para.USR.Get_NewUser);
            pr.addValue("result", "");

            Map mapUsr = jdbcCall.execute(pr);
            urlist = (List<Usr>) mapUsr.get("resultSet");
            String result = (String) mapUsr.get("result");
            if (showMessage) {
               GrowlView.saveMessage(result); 
            }
        }catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
        return urlist;
    }
    
    public void insert() throws SQLException {
       if(this.usr.getUnme() == null || this.usr.getUnme().trim().equals("") || this.usr.getUnme().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Name.");
            return;
        }
        else if(this.usr.getCmpy() == null || this.usr.getCmpy().trim().equals("") || this.usr.getCmpy().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill Company Name.");
            return;
        }
       else if(this.usr.getCmpdept() == null || this.usr.getCmpdept().trim().equals("") || this.usr.getCmpdept().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill Company Department.");
            return;
        }
        else if(this.usr.getRk() == null || this.usr.getRk().trim().equals("") || this.usr.getRk().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Rank.");
            return;
        }
       else if(this.usr.getEmail() == null || this.usr.getEmail().trim().equals("") || this.usr.getEmail().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Email.");
            return;
        }
       else if(this.usr.getMphno() == null || this.usr.getMphno().trim().equals("") || this.usr.getMphno().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Phone.");
            return;
        }
        
         else if(this.usr.getPswd() == null || this.usr.getPswd().trim().equals("") || this.usr.getPswd().trim().isEmpty()) {
            GrowlView.showUIMessage(FacesMessage.SEVERITY_ERROR, "Fill User Password.");
            return;
        }
         
       
     else if(!this.cpwd.equals(this.usr.getPswd())){
         GrowlView.saveMessage("Password and Confirm Password are not match.");
                return;
      } 
        try {                
                this.usrList = crud(usr.getUSRTableStructure(), Para.USR.Insert,false);
                clear(); 
                this.Get_SelectGrid();
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        } 
    }
    
    @PostConstruct
    public void init(){
        this.userId=autoComplete.getUserId();
        this.uname=autoComplete.getUserName();
        try {
            this.usrList=crud(usr.getUSRTableStructure(),Para.USR.Select,false);
        } catch (SQLServerException ex) {
            Logger.getLogger(UsrDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Get_SelectGrid() {
        try {           
                this.usrList=crud(usr.getUSRTableStructure(),Para.USR.Select,false);
        } catch (Exception ex) {
                throw new RuntimeException(ex);
        }
    }  
    
    public List<Usr> getUsrGrid(String UCD) throws SQLException {
        try {
            
                usr.setUid(UCD);
                SQLServerDataTable dt = usr.getUSRTableStructure();

                DataSource ds = Db.getSQLDataSource();
                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                        .returningResultSet("resultSet",
                                new ParameterizedBeanPropertyRowMapper<Usr>() {
                            @Override
                            public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                                Usr ur = new Usr();
                                ur.setUid(rs.getString("UID"));
                                ur.setUnme(rs.getString("UNME"));
                                ur.setCmpy(rs.getString("CMPY"));
                                ur.setCmpdept(rs.getString("CMPDEPT"));
                                ur.setRk(rs.getString("RK"));
                                ur.setMphno(rs.getString("MPHNO"));
                                ur.setEmail(rs.getString("EMAIL"));
                                return ur;
                            }
                        });

                MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpUSR", dt);
                pr.addValue("TranType", Para.USR.Selects.toString());
                pr.addValue("result", "");

                Map mapCt = jdbcCall.execute(pr);
                return (List<Usr>) mapCt.get("resultSet");
        } catch (SQLServerException ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
    }
    
    public void update() throws SQLException {
        try {                
                usr.setUid(usr.getUid());
                this.usrList = crud(usr.getUSRTableStructure(), Para.USR.Update,false);
                 clear();
                 this.Get_SelectGrid();
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
    }
    
    public void updatePW() throws SQLException {
        try {                
                usr.setUid(this.userId);
                this.usrList = crud(usr.getUSRTableStructure(), Para.USR.UpdatePW,false);
                 clearcp();
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
    }
    
    public void delete() {
        try {
                usr = this.selectedUr;
                usr.setUid(usr.getUid());
                this.usrList=crud(usr.getUSRTableStructure(),Para.USR.Delete,false);
                this.Get_SelectGrid();
                clear();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void select() {
        this.selectBtn=true;
        usr = this.selectedUr;
        newRecord=false;
    }
    
    public void cpasswordLostFocus() {        
       if(!this.cpwd.equals(usr.getPswd())){
           try{               
                GrowlView.saveMessage("Password and Confirm Password are not match.");
                return;
           }
           catch (Exception e) {
                throw new RuntimeException(e);
            }      
        }
    }
          
     public void pswdLostFocus() { 
        this.setPswd(pswd);
       
    }
        
    public void opswdLostFocus() { 
         this.usrList = getUsrN(this.getUserId().toString(),Para.USR.Select_UNAME,false);
         this.setTpw(this.getUsrList().get(0).getPswd());

       if(!this.opswd.equals(this.getTpw())){
        try{               
                GrowlView.saveMessage("Old Password is Wrong.");
                return;
        
            }
       catch (Exception e) {
                throw new RuntimeException(e);
            }
       
      }
    }  
    
    public void uidLostFocus() {
        if(this.getUserId().toString().trim() == null || this.getUserId().toString().trim().isEmpty() || this.getUserId().trim().equals("")){
   
            try {
                GrowlView.saveMessage("Fill User Code.");
                clear();
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        //usr.setUid(this.getUid().toString().trim());
        this.usrList = getUsrN(this.getUserId().toString(),Para.USR.Select_UNAME,false);
       
        
        if (this.getUsrList().isEmpty()) {
            try {
                newRecord = true;                    
                clear();         
                GrowlView.saveMessage("Data Empty.");                    
                return;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } 
        
        usr.setUnme(this.getUsrList().get(0).getUnme()); 
        //usr.setPswd(this.getUsrList().get(0).getPswd());
        this.setTpw(this.getUsrList().get(0).getPswd());
    } 
    
    public List<Usr> getUsrN(String UID,Para.USR para,boolean showMsgYN)  {
        try{
            Usr u=new Usr();
            u.setUid(UID);
            
            try{
                ds = Db.getSQLDataSource();
                jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                        .returningResultSet("resultSet",
                                new ParameterizedBeanPropertyRowMapper<Usr>() {
                                    @Override
                                    public Usr mapRow(ResultSet rs, int rowNum)throws SQLException {
              
                                        Usr ur = new Usr();
                                        ur.setUnme(rs.getString("UNME"));
                                        ur.setPswd(rs.getString("PSWD"));
                                        return ur;
                                    }
                                });
                
                MapSqlParameterSource pr = new MapSqlParameterSource().addValue("tbTpUSR",  u.getUSRTableStructure());
                pr.addValue("TranType", para.toString());
                pr.addValue("result", "");
                
                Map mapCusd = jdbcCall.execute(pr);
                usrList = (List<Usr>) mapCusd.get("resultSet"); // more than 1 record
                String result = (String) mapCusd.get("result");
                if(showMsgYN)
                    GrowlView.saveMessage(result);
                
            }
            catch(Exception ex){
                GrowlView.saveMessage(ex.toString());
                throw new RuntimeException(ex.toString());
            }
            return usrList;
        }
        catch(Exception ex){
            Logger.getLogger(UsrDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    
      
    public void clear(){
        
        usr.init();        
        this.setCpwd(null);
     }
    
    public void clearcp(){
        
        usr.init();
        this.setUid(null);
        
        this.setCpwd(null);
     }
    
    public List<Usr> Select_Name(String UID) {
        try {

            Usr ur = new Usr();

            ur.setUid(UID);

            SQLServerDataTable urTable = ur.getUSRTableStructure();
            this.usrList = selectByName(urTable, Para.USR.Select_Name, false);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usrList;
    }

    public List<Usr> selectByName(SQLServerDataTable sourceDataTable, Para.USR para, boolean showMsgYN) {

        try {
            DataSource ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                    .returningResultSet("resultSet",
                            new ParameterizedBeanPropertyRowMapper<Usr>() {
                        @Override
                        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usr ur = new Usr();
                            
                            ur.setUnme(rs.getString("UNME"));

                            return ur;
                        }
                    });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                    "tbTpUSR", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapCr = jdbcCall.execute(pr);
            usrList = (List<Usr>) mapCr.get("resultSet");
            String result = (String) mapCr.get("result");
            if (showMsgYN) {
                GrowlView.saveMessage(result);
            }
        } catch (Exception ex) {
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return usrList;
    }
    
}

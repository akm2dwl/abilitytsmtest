/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.dao;

import com.ability.model.Usr;
import com.ability.util.AutoComplete;
import com.ability.util.GrowlView;
import com.ability.util.Para;
import com.ability.util.SessionUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author AbilityJAVA
 */
@ManagedBean(name = "loginDao")
@SessionScoped
public class LoginDao implements Serializable{
    private static final long serialVersionUID = 1094801825228386363L;
	
	private String pwd;
	private String userId;
	private String userName;
    
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    private DataSource ds;
    private SimpleJdbcCall jdbcCall = null;
    public void validateUsernamePassword() {       
		List<Usr> userList=LoginAccess(this.getUserId(),this.getPwd());
		if (userList.size()>0) {
			AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                        autoComplete.setUid(userId);
                        autoComplete.setUname(pwd);
                        autoComplete.Login();
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			
		}
	}
	
	public void logout() {
		HttpSession session = SessionUtil.getSession();
		session.invalidate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

       public List<Usr> LoginAccess(String usrId,String password){       
        Usr user=new Usr();
        user.setUid(usrId);
        user.setPswd(password);
        List<Usr> userList=new ArrayList<Usr>();
        try{
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_USR")
                .returningResultSet("resultSet",
                new ParameterizedBeanPropertyRowMapper<Usr>() {
                    @Override
                    public Usr mapRow(ResultSet rs, int rowNum)throws SQLException {
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
           
        }
        catch(Exception ex){
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return userList;
    }
        
}

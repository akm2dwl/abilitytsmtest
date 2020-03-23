/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.Controller;

import com.ability.dao.reprtStaff;
import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Usr;
import com.ability.model.Wdone;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author AbilityJAVA
 */
@ManagedBean(name = "rptStaff")
@ViewScoped
public class reportByStaffController {

    @ManagedProperty("#{todod}")
    private Todod todod;
    @ManagedProperty("#{wdone}")
    private Wdone wdone;
    @ManagedProperty("#{usr}")
    private Usr usr;
    private Todod selectTodod;
    public reprtStaff rf = new reprtStaff();

    public Todod getTodod() {
        return todod;
    }

    public void setTodod(Todod todod) {
        this.todod = todod;
    }

    public Wdone getWdone() {
        return wdone;
    }

    public void setWdone(Wdone wdone) {
        this.wdone = wdone;
    }

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }
    private Integer finished;
    private List<Wdone> wdoneList = new ArrayList<Wdone>();
    private String ddid;
    private List<Todod> tododList = new ArrayList<Todod>();
    private List<Usr> usrList = new ArrayList<Usr>();
    private Usr comboUsr;

    public reprtStaff getRf() {
        return rf;
    }

    public void setRf(reprtStaff rf) {
        this.rf = rf;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public List<Wdone> getWdoneList() {
        return wdoneList;
    }

    public void setWdoneList(List<Wdone> wdoneList) {
        this.wdoneList = wdoneList;
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public List<Todod> getTododList() {
        return tododList;
    }

    public void setTododList(List<Todod> tododList) {
        this.tododList = tododList;
    }

    public List<Usr> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<Usr> usrList) {
        this.usrList = usrList;
    }

    public Usr getComboUsr() {
        return comboUsr;
    }

    public void setComboUsr(Usr comboUsr) {
        this.comboUsr = comboUsr;
    }

    public Todod getSelectTodod() {
        return selectTodod;
    }

    public void setSelectTodod(Todod selectTodod) {
        this.selectTodod = selectTodod;
    }
    

    public void selectUsr(SelectEvent event) {
        Object o = event.getObject();
        Usr ur = (Usr) o;
        comboUsr = ur;
    }

    public void selectFinished(ValueChangeEvent evt) throws SQLServerException {
        Integer yesNo = (Integer) evt.getNewValue();
        setFinished(yesNo);
        todod.setUid(comboUsr.getUid());
        this.tododList = rf.selectDetailReport(todod.getToDoDTableStructure());

    }

    public void selectWorkDetail() {
        wdone.setMtid(this.selectTodod.getMtid());
        wdone.setDtid(this.selectTodod.getDtid());
        wdone.setUid(this.selectTodod.getUid());
        this.wdoneList = rf.getWdoneListDetailReport(wdone.getWDTableStructure());
    }

    public void search() {
        Integer ss = finished;
        todod.setUid(comboUsr.getUid());
        this.tododList = rf.selectDetailReport(todod.getToDoDTableStructure());
    }
   
}

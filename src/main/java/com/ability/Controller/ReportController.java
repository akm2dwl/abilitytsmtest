/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.Controller;

import com.ability.dao.ReportDaoImpl;
import com.ability.model.Todod;
import com.ability.model.Todoh;
import com.ability.model.Usr;
import com.ability.model.Wdone;
import com.ability.util.Para;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author AbilityJAVA
 */
@ManagedBean(name = "reportCon")
@ViewScoped
public class ReportController {

    @ManagedProperty("#{todoh}")
    private Todoh toh;
    @ManagedProperty("#{todod}")
    private Todod todod;
    @ManagedProperty("#{wdone}")
    private Wdone wdone;
    @ManagedProperty("#{usr}")
    private Usr usr;
    private Todod selectTodod;
    public String mainTaskId;
    public String mainDesp;
    public String detailTaskId;
    public String detailDesp;
    public ReportDaoImpl report = new ReportDaoImpl();

    public Todoh getToh() {
        return toh;
    }

    public void setToh(Todoh toh) {
        this.toh = toh;
    }

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

    public Todod getSelectTodod() {
        return selectTodod;
    }

    public void setSelectTodod(Todod selectTodod) {
        this.selectTodod = selectTodod;
    }

    private Integer finished;
    private List<Todoh> toDohList = new ArrayList<Todoh>();
    private List<Wdone> wdoneList = new ArrayList<Wdone>();
    private String mtid;
    private String ddid;
    private List<Todod> tododList = new ArrayList<Todod>();
    private List<Usr> usrList = new ArrayList<Usr>();
    private Usr comboUsr;

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public List<Todoh> getToDohList() {
        return toDohList;
    }

    public void setToDohList(List<Todoh> toDohList) {
        this.toDohList = toDohList;
    }

    public List<Wdone> getWdoneList() {
        return wdoneList;
    }

    public void setWdoneList(List<Wdone> wdoneList) {
        this.wdoneList = wdoneList;
    }

    public String getMtid() {
        return mtid;
    }

    public void setMtid(String mtid) {
        this.mtid = mtid;
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

    public ReportDaoImpl getReport() {
        return report;
    }

    public void setReport(ReportDaoImpl report) {
        this.report = report;
    }

    public String getMainTaskId() {
        return mainTaskId;
    }

    public void setMainTaskId(String mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    public String getMainDesp() {
        return mainDesp;
    }

    public void setMainDesp(String mainDesp) {
        this.mainDesp = mainDesp;
    }

    public String getDetailTaskId() {
        return detailTaskId;
    }

    public void setDetailTaskId(String detailTaskId) {
        this.detailTaskId = detailTaskId;
    }

    public String getDetailDesp() {
        return detailDesp;
    }

    public void setDetailDesp(String detailDesp) {
        this.detailDesp = detailDesp;
    }

    public void selectFinished(ValueChangeEvent evt) throws SQLServerException {
        Integer yesNo = (Integer) evt.getNewValue();
        setFinished(yesNo);
        if (finished == 1 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.Select, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (finished == 2 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectWithDate, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (finished == 3 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectADate, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (finished == 1 && comboUsr.getUid() != null) {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectAllUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (finished == 2 && comboUsr.getUid() != null) {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectfinishedUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectNfinishedUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        }

    }

    public void selectDetail() {
        todod.setMtid(mtid);
        this.tododList = report.selectDetail(todod.getToDoDTableStructure());
    }

    public void selectWorkDetail() {
        wdone.setMtid(this.selectTodod.getMtid());
        wdone.setDtid(this.selectTodod.getDtid());
        this.wdoneList = report.getWdoneListDetail(wdone.getWDTableStructure());
        this.detailTaskId = wdoneList.get(0).getDtid();
        this.detailDesp = wdoneList.get(0).getDetailName();
    }

    public void selectUsr(SelectEvent event) {
        Object o = event.getObject();
        Usr ur = (Usr) o;
        comboUsr = ur;
    }
    public void search() throws SQLServerException{
        Integer yesNo = finished;
        //setFinished(yesNo);
         if (yesNo == 1 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.Select, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (yesNo == 2 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectWithDate, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (yesNo == 3 && comboUsr == null) {
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectADate, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (yesNo == 1 && comboUsr.getUid() != null) {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectAllUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else if (yesNo == 2 && comboUsr.getUid() != null) {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectfinishedUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        } else {
            toh.setLuid(this.comboUsr.getUid());
            this.toDohList = report.getHeaderList(Para.ToDoH.SelectNfinishedUsr, toh.getToDoHTableStructure());
            this.mainTaskId = toDohList.get(0).getMtid();
            this.mainDesp = toDohList.get(0).getDesp();
        }
    }
}

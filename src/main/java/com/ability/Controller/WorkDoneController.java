/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.Controller;

import com.ability.dao.WdoneDao;
import com.ability.dao.WorkDoneDao;
import com.ability.model.Usr;
import com.ability.model.Wdone;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author AbilityJAVA
 */
@ManagedBean(name = "wdoneCon")
@ViewScoped
public class WorkDoneController {
  @ManagedProperty("#{wdone}")
  private Wdone wdone; 
  @ManagedProperty("#{usr}")
  private Usr usr;
  private List<Usr> usrList = new ArrayList<Usr>();
  private Usr comboUsr;
  public WorkDoneDao report=new WorkDoneDao();
  private Integer finished;
  private List<Wdone> wdoneList=new ArrayList<Wdone>();

    public Wdone getWdone() {
        return wdone;
    }

    public void setWdone(Wdone wdone) {
        this.wdone = wdone;
    }

    public WorkDoneDao getReport() {
        return report;
    }

    public void setReport(WorkDoneDao report) {
        this.report = report;
    }

  

    public List<Wdone> getWdoneList() {
        return wdoneList;
    }

    public void setWdoneList(List<Wdone> wdoneList) {
        this.wdoneList = wdoneList;
    }

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
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
    
    public void getWdoneGrid(){
        wdone.setUid(comboUsr.getUid());
        this.wdoneList=report.getWorkDoneReport(wdone.getWDTableStructure());
    }
    public void selectUsr(SelectEvent event) {
        Object o = event.getObject();
        Usr ur = (Usr) o;
        comboUsr = ur;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }
    
}

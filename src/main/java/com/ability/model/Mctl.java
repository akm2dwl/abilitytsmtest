
package com.ability.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Mctl
{
    private Integer id;
    private Integer mnuid;
    private Integer sno;
    private String mainmnu;
    private Integer rlid;
    private String frmpnme;
    private String frmlnme;
    private String frmtle;     

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMnuid() {
        return mnuid;
    }

    public void setMnuid(Integer mnuid) {
        this.mnuid = mnuid;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getMainmnu() {
        return mainmnu;
    }

    public void setMainmnu(String mainmnu) {
        this.mainmnu = mainmnu;
    }
   
    public Integer getRlid() {
        return rlid;
    }

    public void setRlid(Integer rlid) {
        this.rlid = rlid;
    }

    public String getFrmpnme() {
        return frmpnme;
    }

    public void setFrmpnme(String frmpnme) {
        this.frmpnme = frmpnme;
    }

    public String getFrmlnme() {
        return frmlnme;
    }

    public void setFrmlnme(String frmlnme) {
        this.frmlnme = frmlnme;
    }

    public String getFrmtle() {
        return frmtle;
    }

    public void setFrmtle(String frmtle) {
        this.frmtle = frmtle;
    }
}  
    

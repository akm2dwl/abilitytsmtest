package com.ability.dao;

import com.ability.util.Util;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Layout {
    private Integer i;

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }
    
    
    
    @PostConstruct
    public void init()  {
        HttpSession session=Util.getSession();
        session.setAttribute("bkid", "400");

    }
    
   
    
}

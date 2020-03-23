/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.converter;

import com.ability.dao.DeptDao;
import com.ability.model.Dept;
import com.ability.util.AutoComplete;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.sql.SQLException;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("deptConverter")
public class DepartmentConverter implements Converter {
   
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                Dept dept=new Dept();
                List<Dept> cs=dept.select();
              
                for (int i = 0; i < cs.size(); i++) {
                    Dept dept1=new Dept();
                    dept1 = cs.get(i);
                    if(dept1.getDeptnme().equalsIgnoreCase(value)) {
                        autoComplete.setCboDept(dept1);
                        return dept1;
                    }
                }
                return null;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Department."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Dept) object).getDeptnme());
        }
        else {
            return null;
        }
    } 
     
}

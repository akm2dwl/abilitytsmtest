/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.converter;

import com.ability.dao.JtypeDao;
import com.ability.model.Jtype;
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

@FacesConverter("jtConverter")
public class JtypeConverter implements Converter{
   @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                Jtype cr1 = new Jtype();
                List<Jtype> crs =  cr1.select();
                for (int i = 0; i < crs.size(); i++) {
                    Jtype jt = crs.get(i);
                    if(jt.getJtypenme().equals(value)) {
                        autoComplete.setComboJt(jt);
                        return jt;
                    }
                }
                return null;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Job Type."));
            } catch (SQLException ex) {
                Logger.getLogger(JtypeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            return null;
        }
        return null;
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
         if(object != null) {
            return String.valueOf(((Jtype) object).getJtypenme());
        }
        else {
            return null;
        }
    }  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.converter;

import com.ability.dao.UsrDao;
import com.ability.model.Usr;
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

@FacesConverter("usrConverter")
public class UsrConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                Usr cr1 = new Usr();
                List<Usr> crs =  cr1.select();
                for (int i = 0; i < crs.size(); i++) {
                    Usr usr = crs.get(i);
                    if(usr.getUnme().equals(value)) {
                        autoComplete.setComboUsr(usr);
                        return usr;
                    }
                }
                return null;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid User."));
            } catch (SQLException ex) {
                Logger.getLogger(UsrDao.class.getName()).log(Level.SEVERE, null, ex);
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
            return String.valueOf(((Usr) object).getUnme());
        }
        else {
            return null;
        }
    } 
    
}

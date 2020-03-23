/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.converter;

import com.ability.dao.TododDao;
import com.ability.model.Todod;
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

@FacesConverter("todConverter")
public class TododConverter implements Converter {
    
        @Override
        public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
            if(value != null && value.trim().length() > 0) {
                try {
                    AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                    List<Todod> csi=autoComplete.getTdList();

                    for (int i = 0; i < csi.size(); i++) {
                        Todod td = csi.get(i);
                        if(td.getDesp().equalsIgnoreCase(value)) {
                            autoComplete.setCombotd(td);
                            return td;
                        }
                    }
                    return null;
                } catch(NumberFormatException ex) {
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid To Do Detail."));
                }
            }
            else {
                return null;
            }
        }

        @Override
        public String getAsString(FacesContext fc, UIComponent uic, Object object) {
            if(object != null) {
                return String.valueOf(((Todod) object).getDesp());
            }
            else {
                return null;
            }
        }
}

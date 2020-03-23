/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ability.converter;

import com.ability.model.Apptn;
import com.ability.util.AutoComplete;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("appConverter")
public class ApptnConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                AutoComplete autoComplete = (AutoComplete) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("autoComplete");
                List<Apptn> csi=autoComplete.getApptnList();

                for (int i = 0; i < csi.size(); i++) {
                    Apptn app = csi.get(i);
                    if(app.getApplton().equalsIgnoreCase(value)) {
                        autoComplete.setComboApptn(app);
                        return app;
                    }
                }
                return null;
            } catch(NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Application."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Apptn) object).getApplton());
        }
        else {
            return null;
        }
    } 
}

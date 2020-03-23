
package com.ability.util;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@ManagedBean
public class GrowlView {
     
    private String message;
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
     
     public static void saveMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Message",  msg) );
    
    }
    
    public static void showUIMessage(Severity severity, String message) {
		FacesMessage facesMessage = new FacesMessage(severity, message, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
    
    public static void showMsg(String result){
        if(result.equalsIgnoreCase("ok")) GrowlView.showUIMessage(FacesMessage.SEVERITY_INFO, result);
        else GrowlView.showUIMessage(FacesMessage.SEVERITY_WARN, result);
    }
}
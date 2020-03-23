
package com.ability.rpt;

import com.ability.dao.Db;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@ManagedBean
@RequestScoped
public class RptGen {
    private Date date;
    private String bc;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }
    
    public void generateReport(ActionEvent actionEvent){
        try {
            Connection connection = Db.getConnection();
            
            ServletOutputStream servletOutputStream;
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)
                    facesContext.getExternalContext().getResponse();
            
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream reportStream = classLoader.getResourceAsStream("/BC_CC.jasper");
            
            Map parameters = new HashMap();
            parameters.put("BK", "MEB");
            parameters.put("BR", "HQ");
            parameters.put("BKID", 4);
            parameters.put("BRID", 7);
            if(this.date!=null)
                parameters.put("D04",new SimpleDateFormat("yyyy-MM-dd").format(date));
            else
                parameters.put("D04",null);
            parameters.put("BC", bc);
            
            servletOutputStream = response.getOutputStream();
            facesContext.responseComplete();
            response.setContentType("application/pdf");
            JasperRunManager.runReportToPdfStream(reportStream,
                    servletOutputStream, parameters, connection);
            
            servletOutputStream.flush();
        } catch (SQLException ex) {
            Logger.getLogger(RptGen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(RptGen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RptGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

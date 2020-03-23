
package com.ability.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javax.faces.bean.ManagedBean;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.ability.util.GrowlView;
import com.ability.model.Mctl;
import com.ability.util.Para;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

@ManagedBean(name = "mctlDao",eager = true)
@RequestScoped
public class MctlDao {
    //******************fields*****************************
   
    private Integer id;
    private Integer mnuid;
    private Integer sno;
    private String mainmnu;
    private Integer rlid;
    private String frmpnme;
    private String frmlnme;
    private String frmtle;

    //******************getter/setter*****************************
    

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
    
    //******************constructor*****************************
    public MctlDao() {
        
        id=0;
        mnuid=0;
        sno=0;
        mainmnu="";
        rlid=0;
        frmpnme="";
        frmlnme="";
        frmtle="";
    }

    //******************building menu*****************************
    @PostConstruct
    public void init(){
        Select_MENU();
        DefaultSubMenu subMenu =new DefaultSubMenu();
        DefaultMenuItem menuItem =new DefaultMenuItem();
        for (Mctl mctl : menuList) {
            if(mctl.getSno().toString().equalsIgnoreCase("0")){
                subMenu = new DefaultSubMenu(mctl.getMainmnu());
                subMenu.setIcon("fa fa-sitemap");
                subMenu.setId(mctl.getMainmnu());
                if(subMenu.getId()!=null){
                    model.addElement(subMenu);
                }    
            }
            else if(mctl.getSno()>0){
                menuItem = new DefaultMenuItem(mctl.getMainmnu());
                menuItem.setId(mctl.getMainmnu());
                menuItem.setValue(mctl.getMainmnu());
                menuItem.setIcon("fa fa-home");
                menuItem.setOutcome(mctl.getFrmpnme());
                subMenu.addElement(menuItem);
            }
        }
    }

    //******************menu getter/setter*****************************
    private MenuModel model = new DefaultMenuModel();
    private List<Mctl> menuList;
    
    public MenuModel getModel() {
        return model;
    } 

    public List<Mctl> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Mctl> menuList) {
        this.menuList = menuList;
    }

//***********************data***************************************
    private DataSource ds=null;
    private SimpleJdbcCall jdbcCall=null;
   
    private SQLServerDataTable getDataTable() throws SQLServerException{
    SQLServerDataTable tmp = new SQLServerDataTable(); 

        tmp.addColumnMetadata("ID", java.sql.Types.INTEGER);
        tmp.addColumnMetadata("mnuid", java.sql.Types.INTEGER);
        tmp.addColumnMetadata("sno", java.sql.Types.INTEGER);
        tmp.addColumnMetadata("MAINMNU", java.sql.Types.NVARCHAR);
        tmp.addColumnMetadata("RLID", java.sql.Types.SMALLINT);
        tmp.addColumnMetadata("FRMPNME", java.sql.Types.NVARCHAR);
        tmp.addColumnMetadata("FRMLNME", java.sql.Types.NVARCHAR);
        tmp.addColumnMetadata("FRMTLE", java.sql.Types.NVARCHAR);

        tmp.addRow(this.id,this.mnuid,this.sno,this.mainmnu,
            this.rlid,this.frmpnme,this.frmlnme,this.frmtle);
        return tmp;
    }
    
    //*************************methods*************************************
    public void Insert(){
        try{
            //set data here
            //setBkid(1);
            SQLServerDataTable tmp=getDataTable();
            
            crud(tmp,Para.MCTL.Insert);
        }
        catch(SQLServerException ex){
        }
    }
    
//    public void Update(){
//          
//       try{
//           java.util.Date javaDate = new java.util.Date();
//            long javaTime = javaDate.getTime();
//            java.sql.Date sqlDate = new java.sql.Date(javaTime);
//  
//            SQLServerDataTable tmp=getDataTable();
//             tmp.addRow(
//                BKID SMALLINT
//                MAINMNU NVARCHAR
//                SMENU NVARCHAR
//                PID SMALLINT
//                RLID SMALLINT
//                FRMPNME NVARCHAR
//                FRMLNME NVARCHAR
//                FRMTLE NVARCHAR
//            );
//            
//            crud(tmp,Para.MCTL.Update);
//        }
//        catch(SQLServerException ex){
//        }
//    }

   
    public void Delete(){
        try{
            //set data here  
            SQLServerDataTable tmp=getDataTable();
            
            crud(tmp,Para.MCTL.Delete);
        }
        catch(SQLServerException ex){
        }        
    }
    
    public void Select(){
        try{
            //set data here  
            SQLServerDataTable tmp=getDataTable();
            
            crud(tmp,Para.MCTL.Select);
        }
        catch(SQLServerException ex){
        }                
    }
    private void Select_MENU(){
        try{
            //set data here  
            SQLServerDataTable mctlTable = getDataTable();
  
            this.menuList = crud( mctlTable, Para.MCTL.Select_MENU);
        }
        catch(SQLServerException ex){
        }      
    }
 
   //  public void Select_ID(){
//        try{
//            java.util.Date javaDate = new java.util.Date();
//            long javaTime = javaDate.getTime();
//            java.sql.Date sqlDate = new java.sql.Date(javaTime);
//  
//            SQLServerDataTable tmp=getDataTable();
//            tmp.addRow(
//              BKID SMALLINT
//              MAINMNU NVARCHAR
//              SMENU NVARCHAR
//              PID SMALLINT
//              RLID SMALLINT
//              FRMPNME NVARCHAR
//              FRMLNME NVARCHAR
//              FRMTLE NVARCHAR
//            );

//            
//            crud(tmp,Para.MCTL.Select_ID);
//        }
//        catch(SQLServerException ex){
//        }                
//    }
//    public void Select_CD(){
//        try{
//            java.util.Date javaDate = new java.util.Date();
//            long javaTime = javaDate.getTime();
//            java.sql.Date sqlDate = new java.sql.Date(javaTime);
//  
//            SQLServerDataTable tmp=getDataTable();
//            tmp.addRow(
//              BKID SMALLINT
//              MAINMNU NVARCHAR
//              SMENU NVARCHAR
//              PID SMALLINT
//              RLID SMALLINT
//              FRMPNME NVARCHAR
//              FRMLNME NVARCHAR
//              FRMTLE NVARCHAR
//            );
//            
//            crud(tmp,Para.MCTL.Select_CD);
//        }
//        catch(SQLServerException ex){
//        }                
//    }

    public List<Mctl> crud(SQLServerDataTable sourceDataTable,Para.MCTL para)  {
        List<Mctl> mctlList = new ArrayList<Mctl>();
        try{
            ds = Db.getSQLDataSource();
            jdbcCall = new SimpleJdbcCall(ds).withProcedureName("usp_MCTL")
                .returningResultSet("resultSet",
                new ParameterizedBeanPropertyRowMapper<Mctl>() {
                    @Override
                    public Mctl mapRow(ResultSet rs, int rowNum)throws SQLException {
                        Mctl mctl = new Mctl();
                        mctl.setId(rs.getInt("MNUID"));
                        mctl.setMnuid(rs.getInt("ID"));
                        mctl.setSno(rs.getInt("SNO"));
                        mctl.setMainmnu(rs.getString("MAINMNU"));
                        mctl.setFrmpnme(rs.getString("FRMPNME"));
                        return mctl;
                    }
                });

            MapSqlParameterSource pr = new MapSqlParameterSource().addValue(
                "tbTpMCTL", sourceDataTable);
            pr.addValue("TranType", para.toString());
            pr.addValue("result", "");

            Map mapMctl = jdbcCall.execute(pr);
            mctlList = (List<Mctl>) mapMctl.get("resultSet");
            String result = (String) mapMctl.get("result");
            if(!result.equalsIgnoreCase("ok"))
                GrowlView.saveMessage(result);
        }
        catch(Exception ex){
            GrowlView.saveMessage(ex.toString());
            throw new RuntimeException(ex.toString());
        }
        return mctlList;
    }
}



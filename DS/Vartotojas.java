package DS;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Vartotojas implements Serializable{
    private int id;
    private String login, pass, eMail;
    private boolean aktyvus = true;
    private ArrayList<Projektas> projektai = new ArrayList();
    
    private transient IntegerProperty idColumn;
    public void setIdColumn(int value) { idColumnProperty().set(value); }
    public int getIdColumn() { return id; }
    public IntegerProperty idColumnProperty() { 
        if (idColumn == null) {
            idColumn = new SimpleIntegerProperty(this, "idColumn");
            setIdColumn(id);
        }
        return idColumn; 
    }
    
    private transient BooleanProperty activeColumn;
    public void setActiveColumn(boolean value) { activeColumnProperty().set(value);}
    public boolean getActiveColumn() { return aktyvus; }
    public BooleanProperty activeColumnProperty() { 
        if (activeColumn == null) {
            activeColumn = new SimpleBooleanProperty(this, "activeColumn");
            setActiveColumn(aktyvus);
        }
        return activeColumn; 
    }
    
    public Vartotojas(int id, String login, String pass, String eMail, boolean Active) {
        this.login = login;
        this.pass = pass;
        this.id = id;
        this.eMail = eMail;
        this.aktyvus = Active;
        setIdColumn(id);
        setActiveColumn(aktyvus);
    }

    public ArrayList<Projektas> getProjektai() {
        return projektai;
    }
    
    public void pridetiProjekta(Projektas p){
        projektai.add(p);
    }
    
    @Override
    public String toString() {
        return "Vartotojas [" + "Id = " + id + "; Login = " + login + "; Pass = " + pass + "; eMail = " + eMail + "; Aktyvus = " + aktyvus + ']';
    }

    public int getId() {
        return id;
    }
        
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public boolean isAktyvus() {
        return aktyvus;
    }

    public void setAktyvus(boolean aktyvus) {
        this.aktyvus = aktyvus;
        setActiveColumn(aktyvus);
    }
    
    public abstract String getAtpazinimoDuomenys();
}

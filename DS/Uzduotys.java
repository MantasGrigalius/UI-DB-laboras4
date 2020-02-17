package DS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Uzduotys implements Serializable{

    private int id;
    private String pavadinimas;
    private Date sukurimoData, pabaigimoData;
    private Vartotojas sukurtaVartotojo, pabaigtaVartotojo;
    private boolean pabaigta = false;
    private ArrayList<Uzduotys> subUzduotys = new ArrayList();
    private Projektas projektas;

    public Uzduotys(int id, String pavadinimas, Date sukurimoData, Date pabaigimoData, boolean pabaigta, Vartotojas sukurtaVartotojo, Vartotojas pabaigtaVartotojo, Projektas projektas) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.sukurimoData = sukurimoData;
        this.pabaigimoData = pabaigimoData;
        this.sukurtaVartotojo = sukurtaVartotojo;
        this.pabaigtaVartotojo = pabaigtaVartotojo;
        this.pabaigta = pabaigta;
        this.projektas = projektas;
        
        if(sukurtaVartotojo.getClass().equals(Zmogus.class)){
            setCreatorName(((Zmogus)sukurtaVartotojo).getVardas()+" "+((Zmogus)sukurtaVartotojo).getPavarde());
        }
        else{
            setCreatorName(((Imone)sukurtaVartotojo).getPavadinimas());
        }
        setIdColumn(id);
        setName(pavadinimas);
        setAktyvumoStulpelis(pabaigta);
        setSubStulpelis(subUzduotys.size());
        
    }
    
    private transient StringProperty name;
    public void setName(String value) { nameProperty().set(value); }
    public String getName() { return pavadinimas; }
    public StringProperty nameProperty() { 
        if (name == null) {
            name = new SimpleStringProperty(this, "name");
            setName(pavadinimas);
        }
        return name; 
    }
    
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
    
    private transient StringProperty creatorName;
    public void setCreatorName(String value) { creatorNameProperty().set(value); }
    public String getCreatorName() {
        if(sukurtaVartotojo.getClass().equals(Zmogus.class)){
            return ((Zmogus)sukurtaVartotojo).getVardas()+" "+((Zmogus)sukurtaVartotojo).getPavarde();
        }
        else{
            return ((Imone)sukurtaVartotojo).getPavadinimas();
        }
    }
    public StringProperty creatorNameProperty() { 
        if (creatorName == null) {
            creatorName = new SimpleStringProperty(this, "creatorName");
            if(sukurtaVartotojo.getClass().equals(Zmogus.class)){
            setCreatorName(((Zmogus)sukurtaVartotojo).getVardas()+" "+((Zmogus)sukurtaVartotojo).getPavarde());
        }
        else{
            setCreatorName(((Imone)sukurtaVartotojo).getPavadinimas());
        }
        }
        return creatorName; 
    }

    private transient BooleanProperty aktyvumoStulpelis;
    public void setAktyvumoStulpelis(boolean value) { aktyvumoStulpelisProperty().set(value);}
    public boolean getAktyvumoStulpelis() { return pabaigta; }
    public BooleanProperty aktyvumoStulpelisProperty() { 
        if (aktyvumoStulpelis == null) {
            aktyvumoStulpelis = new SimpleBooleanProperty(this, "aktyvumoStulpelis");
            setAktyvumoStulpelis(pabaigta);
        }
        return aktyvumoStulpelis; 
    }

    private transient IntegerProperty subStulpelis;
    public void setSubStulpelis(int value) { subStulpelisProperty().set(value); }
    public int getSubStulpelis() { return subUzduotys.size(); }
    public IntegerProperty subStulpelisProperty() { 
        if (subStulpelis == null) {
            subStulpelis = new SimpleIntegerProperty(this, "subStulpelis");
            setSubStulpelis(subUzduotys.size());
        }
        return subStulpelis; 
    }

    @Override
    public String toString() {
        String subUzduotys = "[";
        for (Uzduotys u : this.subUzduotys) {
            subUzduotys += u.toString();
            subUzduotys += ", ";
        }
        if(subUzduotys.length()>1){
        subUzduotys = subUzduotys.substring(0, subUzduotys.length() - 2);
        }
        subUzduotys += "]";
        return "Uzduotis [" + "Id = " + id + "; Pavadinimas = " + pavadinimas + "; Sukurimo Data = " + sukurimoData + "; Pabaigimo Data = " + pabaigimoData + "; Sukurta Vartotojo = " + sukurtaVartotojo + "; Pabaigta Vartotojo = " + pabaigtaVartotojo + "; Pabaigta = " + pabaigta + "; Projektas = " + projektas + "; Sub Uzduoys = " + subUzduotys + ']';
    }

    public void setPabaigimoData(Date pabaigimoData) {
        this.pabaigimoData = pabaigimoData;
    }

    public void setPabaigtaVartotojo(Vartotojas pabaigtaVartotojo) {
        this.pabaigtaVartotojo = pabaigtaVartotojo;
    }

    public void setPabaigta(boolean pabaigta) {
        this.pabaigta = pabaigta;
        setAktyvumoStulpelis(pabaigta);
    }

    public void setProjektas(Projektas projektas) {
        this.projektas = projektas;
    }

    public ArrayList<Uzduotys> gautiVisasUzduotis() {
        ArrayList<Uzduotys> visos = new ArrayList();
        for (Uzduotys u : subUzduotys) {
            visos.addAll(u.gautiVisasUzduotis());
        }
        return visos;
    }

    public void pridetiUzduoti(Uzduotys u) {
        subUzduotys.add(u);
        setSubStulpelis(subUzduotys.size());
    }

    public int getId() {
        return id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public Date getSukurimoData() {
        return sukurimoData;
    }

    public Date getPabaigimoData() {
        return pabaigimoData;
    }

    public Vartotojas getSukurtaVartotojo() {
        return sukurtaVartotojo;
    }

    public Vartotojas getPabaigtaVartotojo() {
        return pabaigtaVartotojo;
    }

    public boolean isPabaigta() {
        return pabaigta;
    }

    public ArrayList<Uzduotys> getSubUzduotys() {
        return subUzduotys;
    }

    public Projektas getProjektas() {
        return projektas;
    }

}

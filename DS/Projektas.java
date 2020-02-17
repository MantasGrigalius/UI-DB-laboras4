package DS;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projektas implements Serializable{

    private int id;
    private String pavadinimas;
    private ArrayList<Vartotojas> dalyviai = new ArrayList();
    private ArrayList<Uzduotys> uzduotys = new ArrayList();

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
    
    public Projektas(int id, String pavadinimas) {
        this.pavadinimas = pavadinimas;
        //dalyviai.add(kurejas);
        this.id = id;
        setIdColumn(id);
        setName(pavadinimas);
    }

    @Override
    public String toString() {
        return "Projektas [" + "Id = " + id + "; Pavadinimas = " + pavadinimas + ']';
    }

    public int getId() {
        return id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
        setName(pavadinimas);
    }

    public ArrayList<Vartotojas> getDalyviai() {
        return dalyviai;
    }

    public ArrayList<Uzduotys> getUzduotys() {
        return uzduotys;
    }

    public void pridetiVartotoja(Vartotojas v) {
        dalyviai.add(v);
    }

    public void pridetiUzduotys(Uzduotys u) {
        uzduotys.add(u);
    }

    public ArrayList<Uzduotys> gautiVisasUzduotis() {
        ArrayList<Uzduotys> visos = new ArrayList();
        visos.addAll(this.uzduotys);
        for (Uzduotys u : uzduotys) {
            visos.addAll(u.gautiVisasUzduotis());
        }
        return visos;
    }
}

package DS;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Imone extends Vartotojas implements Serializable {

    private String pavadinimas;
    
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

    public Imone(int id, String login, String pass, String eMail, boolean Active, String pavadinimas) {
        super(id, login, pass, eMail, Active);
        this.pavadinimas = pavadinimas;
        setName(pavadinimas);
    }

    @Override
    public String toString() {
        return "Imone [" + "Id = " + super.getId() + "; Pavadinimas = " + pavadinimas + "; Aktyvus = "+ super.isAktyvus()+ ']';
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
        setName(pavadinimas);
    }

    @Override
    public String getAtpazinimoDuomenys(){
        return getPavadinimas();
    }
}

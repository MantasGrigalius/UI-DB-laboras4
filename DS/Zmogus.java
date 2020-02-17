package DS;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Zmogus extends Vartotojas implements Serializable {

    private String vardas, pavarde;

    private transient StringProperty firstName;
    public void setFirstName(String value) { firstNameProperty().set(value); }
    public String getFirstName() { return vardas; }
    public StringProperty firstNameProperty() { 
        if (firstName == null) {
            firstName = new SimpleStringProperty(this, "firstName");
            setFirstName(vardas);
        }
        return firstName; 
    }
    
    private transient StringProperty surname;
    public void setSurname(String value) { surnameProperty().set(value); }
    public String getSurname() { return pavarde; }
    public StringProperty surnameProperty() { 
        if (surname == null) {
            surname = new SimpleStringProperty(this, "surname");
            setSurname(pavarde);
        }
        return surname; 
    }
    
    public Zmogus(int id, String login, String pass, String eMail, boolean Active, String vardas, String pavarde) {
        super(id, login, pass, eMail, Active);
        this.vardas = vardas;
        setFirstName(vardas);
        setSurname(pavarde);
        this.pavarde = pavarde;
    }

    @Override
    public String toString() {
        return "Zmogus [" + "Id = " + super.getId() + "; Vardas = " + vardas + "; Pavarde = " + pavarde + "; Aktyvus = "+ super.isAktyvus()+']';
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
        setFirstName(vardas);
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
        setSurname(pavarde);
    }

    @Override
    public String getAtpazinimoDuomenys(){
        return getVardas() + " " + getPavarde();
    }
}

package DS;

public class Komentaras {
    
    private final int id;
    private final String tekstas;
    private final Vartotojas autorius;
    private final Uzduotys uzduotis;

    public Komentaras(int id, String tekstas, Vartotojas autorius, Uzduotys uzduotis) {
        this.id = id;
        this.tekstas = tekstas;
        this.autorius = autorius;
        this.uzduotis = uzduotis;
    }

    
    
    public int getId() {
        return id;
    }

    public String getTekstas() {
        return tekstas;
    }

    public Vartotojas getAutorius() {
        return autorius;
    }

    public Uzduotys getUzduotis() {
        return uzduotis;
    }

    
    
}

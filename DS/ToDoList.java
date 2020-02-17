package DS;

import java.sql.Statement;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ToDoList implements Serializable {

    private Vartotojas prisijunges = null;
    private int likeBandymai = 3;

    private Connection conn = null;

    public void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost/ld4";
        String USER = "root";
        String PASS = "";
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void disconnect() throws Exception {
        conn.close();
    }

    public boolean registruotiZmogu(String login, String pass, String vardas, String pavarde) {
        if (gautiVartotojaPrisijungimas(login) == null) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO zmogus(`Username`, `Password`, `eMail`, `Active`, `Vardas`, `Pavarde`) VALUES (?,?,?,?,?,?)");
                ps.setString(1, login);
                ps.setString(2, pass);
                ps.setString(3, null);
                ps.setBoolean(4, true);
                ps.setString(5, vardas);
                ps.setString(6, pavarde);
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Nepavyko uzregistruoti naujo zmogaus!");
            }
        }
        return false;
    }

    public boolean registruotiImone(String login, String pass, String pavadinimas) {
        if (gautiVartotojaPrisijungimas(login) == null) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO imone(`Username`, `Password`, `eMail`, `Active`, `Pavadinimas`) VALUES (?,?,?,?,?)");
                ps.setString(1, login);
                ps.setString(2, pass);
                ps.setString(3, null);
                ps.setBoolean(4, true);
                ps.setString(5, pavadinimas);
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("Nepavyko uzregistruoti naujos imones!");
            }
        }
        return false;
    }

    public Vartotojas prisijungimas(String login, String pass) throws Exceptionai {
        likeBandymai--;
        for (Vartotojas v : gautiVartotojus()) {
            if (v.getLogin().equals(login) && v.getPass().equals(pass) && v.isAktyvus()) {
                prisijunges = v;
                likeBandymai = 3;
                return v;
            }
        }
        throw new Exceptionai("Neteisingi prisijungimo duomenys!", likeBandymai);
    }

    public void atsijungti(int id) {
        prisijunges = null;
    }

    public boolean pridetiProjekta(String pavadinimas) {
        if (prisijunges != null && pavadinimas.length() > 3) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO projektas(`Pavadinimas`) VALUES (?)");
                ps.setString(1, pavadinimas);
                ps.executeUpdate();
                ps.close();

                return true;
            } catch (SQLException ex) {
                System.out.println("Nepavyko sukurti naujo projekto!");
            }
        }
        return false;
    }

    public void pridetiZmoguProjektan(int projektoId, int vartotojoId) {
        if (prisijunges != null) {
            Projektas dabartinis = gautiProjektoID(projektoId);
            Vartotojas vartotojas = gautiZmoguId(vartotojoId);
            if (dabartinis != null && vartotojas != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO zmogusprojektas VALUES (?, ?)");
                    ps.setInt(1, vartotojoId);
                    ps.setInt(2, projektoId);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Nepavyko prideti zmogaus i projekta!");
                }
            }
        }
    }
    
    public void pridetiImoneProjektan(int projektoId, int vartotojoId) {
        if (prisijunges != null) {
            Projektas dabartinis = gautiProjektoID(projektoId);
            Vartotojas vartotojas = gautiImoneId(vartotojoId);
            if (dabartinis != null && vartotojas != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO imoneprojektas VALUES (?, ?)");
                    ps.setInt(1, vartotojoId);
                    ps.setInt(2, projektoId);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Nepavyko prideti imones i projekta!");
                }
            }
        }
    }

    public void trintiProjekta(int id) {
        if (prisijunges != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM zmogusprojektas WHERE `ProjektoId` = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement("DELETE FROM imoneprojektas WHERE `ProjektoId` = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement("SELECT `Id` FROM uzduotis WHERE `Projektas` = ?");
                ps.setInt(1, id);
                ResultSet rez = ps.executeQuery();
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    uzbaigtiUzduoti(Id);
                }
                rez.close();
                ps.close();

                ps = conn.prepareStatement("DELETE FROM projektas WHERE `Id` = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Nepavyko panaikinti projekto!");
            }
        }
    }

    public void pridetiUzduoti(int projektoId, String pavadinimas) {
        if (prisijunges != null) {
            if (gautiProjektoID(projektoId) != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO uzduotis(`Pavadinimas`, `SukurimoData`, `SukurtaVartotojo`, `Projektas`) VALUES (?, ?, ?, ?)");
                    ps.setString(1, pavadinimas);
                    ps.setDate(2, new java.sql.Date(new Date().getTime()));
                    ps.setInt(3, prisijunges.getId());
                    ps.setInt(4, projektoId);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Nepavyko sukurti naujos užduoties!");
                }
            }
        }
    }

    public void pridetiSubUzduoti(int uzduotiesId, String pavadinimas) {
        if (prisijunges != null) {
            if (gautiUzduotiId(uzduotiesId) != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO uzduotis(`Pavadinimas`, `SukurimoData`, `SukurtaVartotojo`,`TevineUzduotis`) VALUES (?, ?, ?, ?)");
                    ps.setString(1, pavadinimas);
                    ps.setDate(2, new java.sql.Date(new Date().getTime()));
                    ps.setInt(3, prisijunges.getId());
                    ps.setInt(4, uzduotiesId);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Nepavyko sukurti naujos sub užduoties!");
                }
            }
        }
    }

    public void redaguotiZmogausInfo(int id, String vardas, String pavarde) {
        if (prisijunges != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE zmogus SET `Vardas` = ?, `Pavarde` = ? WHERE `Id` = ?");
                ps.setString(1, vardas);
                ps.setString(2, pavarde);
                ps.setInt(3, id);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Nepavyko atnaujinti žmogaus!");
            }
        }
    }

    public void redaguotiImonesInfo(int id, String pavadinimas) {
        if (prisijunges != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE imone SET `Pavadinimas` = ? WHERE `Id` = ?");
                ps.setString(1, pavadinimas);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Nepavyko atnaujinti įmonės!");
            }
        }
    }

    public void redaguotiProjektoInfo(int id, String pavadinimas) {
        if (prisijunges != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE projektas SET `Pavadinimas` = ? WHERE `Id` = ?");
                ps.setString(1, pavadinimas);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Nepavyko atnaujinti projekto!");
            }
        }
    }

    public ArrayList<Vartotojas> gautiVartotojus() {
        ArrayList<Vartotojas> visi = new ArrayList();
        try {
            Statement ps = conn.createStatement();
            ResultSet rez = ps.executeQuery("SELECT * FROM `zmogus`");
            while (rez.next()) {
                int Id = rez.getInt(1);
                String Username = rez.getString(2);
                String Password = rez.getString(3);
                String eMail = rez.getString(4);
                boolean Active = rez.getBoolean(5);
                String Vardas = rez.getString(6);
                String Pavarde = rez.getString(7);

                Vartotojas v = new Zmogus(Id, Username, Password, eMail, Active, Vardas, Pavarde);
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `zmogusprojektas` WHERE `ZmogausId` = ?)");
                ps2.setInt(1, v.getId());
                ResultSet rez2 = ps2.executeQuery();
                while (rez2.next()) {
                    int ProjektoId = rez2.getInt(1);
                    String Pavadinimas = rez2.getString(2);

                    Projektas p = new Projektas(ProjektoId, Pavadinimas);
                    v.pridetiProjekta(p);
                }
                rez2.close();
                ps2.close();
                
                visi.add(v);
            }
            rez.close();
            ps.close();

            ps = conn.createStatement();
            rez = ps.executeQuery("SELECT * FROM imone");
            while (rez.next()) {
                int Id = rez.getInt(1);
                String Username = rez.getString(2);
                String Password = rez.getString(3);
                String eMail = rez.getString(4);
                boolean Active = rez.getBoolean(5);
                String Pavadinimas = rez.getString(6);

                Vartotojas v = new Imone(Id, Username, Password, eMail, Active, Pavadinimas);
                
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `imoneprojektas` WHERE `ImonesId` = ?)");
                ps2.setInt(1, v.getId());
                ResultSet rez2 = ps2.executeQuery();
                while (rez2.next()) {
                    int ProjektoId = rez2.getInt(1);
                    String ProjektoPavadinimas = rez2.getString(2);

                    Projektas p = new Projektas(ProjektoId, ProjektoPavadinimas);
                    v.pridetiProjekta(p);
                }
                rez2.close();
                ps2.close();
                
                visi.add(v);
            }
            rez.close();
            ps.close();
            return visi;
        } catch (SQLException ex) {
            System.out.println("Nepavyko gauti vartotoju!");
        }
        return new ArrayList();
    }

    public ArrayList<Projektas> gautiProjektus() {
        if (prisijunges != null) {
            ArrayList<Projektas> visi = new ArrayList();
            try {
                Statement ps = conn.createStatement();
                ResultSet rez = ps.executeQuery("SELECT * FROM `projektas`");
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Pavadinimas = rez.getString(2);

                    Projektas p = new Projektas(Id, Pavadinimas);
                    
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `zmogus` WHERE `Id` IN (SELECT `ZmogausId` FROM `zmogusprojektas` WHERE `ProjektoId` = ?)");
                    ps2.setInt(1, p.getId());
                    ResultSet rez2 = ps2.executeQuery();
                    while (rez2.next()) {
                        int ZmogausId = rez2.getInt(1);
                        String Username = rez2.getString(2);
                        String Password = rez2.getString(3);
                        String eMail = rez2.getString(4);
                        boolean Active = rez2.getBoolean(5);
                        String Vardas = rez2.getString(6);
                        String Pavarde = rez2.getString(7);

                        Vartotojas v = new Zmogus(ZmogausId, Username, Password, eMail, Active, Vardas, Pavarde);
                        p.pridetiVartotoja(v);
                    }
                    rez2.close();
                    ps2.close();
                    
                    PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM `imone` WHERE `Id` IN (SELECT `ImonesId` FROM `imoneprojektas` WHERE `ProjektoId` = ?)");
                    ps3.setInt(1, p.getId());
                    ResultSet rez3 = ps3.executeQuery();
                    while (rez3.next()) {
                        int ImonesId = rez3.getInt(1);
                        String Username = rez3.getString(2);
                        String Password = rez3.getString(3);
                        String eMail = rez3.getString(4);
                        boolean Active = rez3.getBoolean(5);
                        String ImonesPavadinimas = rez3.getString(6);

                        Vartotojas v = new Imone(ImonesId, Username, Password, eMail, Active, ImonesPavadinimas);
                        p.pridetiVartotoja(v);
                    }
                    rez3.close();
                    ps3.close();
                    
                    visi.add(p);
                }
                rez.close();
                ps.close();
                return visi;
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti projektų!");
            }
        }
        return new ArrayList();
    }

    public ArrayList<Uzduotys> gautiUzduotis() {
        if (prisijunges != null) {
            ArrayList<Uzduotys> visi = new ArrayList();
            try {
                Statement ps = conn.createStatement();
                ResultSet rez = ps.executeQuery("SELECT * FROM `uzduotis`");
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Pavadinimas = rez.getString(2);
                    Date SukurimoData = rez.getDate(3);
                    Date PabaigimoData = rez.getDate(4);
                    int SukurtaVartotojoId = rez.getInt(5);
                    int PabaigtaVartotojoId = rez.getInt(6);
                    boolean Pabaigta = rez.getBoolean(7);
                    int TevineUzduotisId = rez.getInt(8);
                    int ProjektasId = rez.getInt(9);
                    
                    Vartotojas SukurtaVartotojo = gautiZmoguId(SukurtaVartotojoId);
                    Vartotojas PabaigtaVartotojo = gautiZmoguId(PabaigtaVartotojoId);
                    Projektas Projektas = gautiProjektoID(ProjektasId);

                    Uzduotys u = new Uzduotys(Id, Pavadinimas, SukurimoData, PabaigimoData, Pabaigta, SukurtaVartotojo, PabaigtaVartotojo, Projektas);
                    
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `uzduotis` WHERE `TevineUzduotis` = ?");
                    ps2.setInt(1, Id);
                    ResultSet rez2 = ps2.executeQuery();
                    while (rez2.next()) {
                        int Id2 = rez2.getInt(1);
                        String Pavadinimas2 = rez2.getString(2);
                        Date SukurimoData2 = rez2.getDate(3);
                        Date PabaigimoData2 = rez2.getDate(4);
                        int SukurtaVartotojoId2 = rez2.getInt(5);
                        int PabaigtaVartotojoId2 = rez2.getInt(6);
                        boolean Pabaigta2 = rez2.getBoolean(7);
                        int TevineUzduotisId2 = rez2.getInt(8);
                        int ProjektasId2 = rez2.getInt(9);
                        
                        Vartotojas SukurtaVartotojo2 = gautiZmoguId(SukurtaVartotojoId2);
                        Vartotojas PabaigtaVartotojo2 = gautiZmoguId(PabaigtaVartotojoId2);
                        Projektas Projektas2 = gautiProjektoID(ProjektasId2);
                        
                        Uzduotys u2 = new Uzduotys(Id2, Pavadinimas2, SukurimoData2, PabaigimoData2, Pabaigta2, SukurtaVartotojo2, PabaigtaVartotojo2, Projektas2);
                        u.pridetiUzduoti(u2);
                    }
                    rez2.close();
                    ps2.close();
                    visi.add(u);
                }
                rez.close();
                ps.close();
                return visi;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Nepavyko gauti užduočių!");
            }
        }
        return new ArrayList();
    }

    public Uzduotys gautiUzduotiId(int uzduotiesId) {
        if (prisijunges != null) {
            for (Uzduotys u : gautiUzduotis()) {
                if (u.getId() == uzduotiesId) {
                    return u;
                }
            }
        }
        return null;
    }

    public boolean uzsaldytiZmogu(int id) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE zmogus SET `Active` = ? WHERE `Id` = ?");
                ps.setBoolean(1, false);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("Nepavyko užšaldyti žmogaus!");
            }
        }
        return false;
    }
    
    public boolean uzsaldytiImone(int id) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE imone SET `Active` = ? WHERE `Id` = ?");
                ps.setBoolean(1, false);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("Nepavyko užšaldyti įmonės!");
            }
        }
        return false;
    }

    public Zmogus gautiZmoguId(int id) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM zmogus WHERE `Id` = ?");
                ps.setInt(1, id);
                ResultSet rez = ps.executeQuery();
                
                Zmogus z = null;
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Username = rez.getString(2);
                    String Password = rez.getString(3);
                    String eMail = rez.getString(4);
                    boolean Active = rez.getBoolean(5);
                    String Vardas = rez.getString(6);
                    String Pavarde = rez.getString(7);

                    z = new Zmogus(Id, Username, Password, eMail, Active, Vardas, Pavarde);
                    
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `zmogusprojektas` WHERE `ZmogausId` = ?)");
                    ps2.setInt(1, z.getId());
                    ResultSet rez2 = ps2.executeQuery();
                    while (rez2.next()) {
                        int ProjektoId = rez2.getInt(1);
                        String Pavadinimas = rez2.getString(2);

                        Projektas p = new Projektas(ProjektoId, Pavadinimas);
                        z.pridetiProjekta(p);
                    }
                    rez2.close();
                    ps2.close();
                    
                    break;
                }
                rez.close();
                ps.close();
                return z;
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti žmogaus!");
            }
        }
        return null;
    }
    
    public Imone gautiImoneId(int id) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM imone WHERE `Id` = ?");
                ps.setInt(1, id);
                ResultSet rez = ps.executeQuery();
                
                Imone i = null;
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Username = rez.getString(2);
                    String Password = rez.getString(3);
                    String eMail = rez.getString(4);
                    boolean Active = rez.getBoolean(5);
                    String Pavadinimas = rez.getString(6);

                    i = new Imone(Id, Username, Password, eMail, Active, Pavadinimas);
                    
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `imoneprojektas` WHERE `ImonesId` = ?)");
                    ps2.setInt(1, i.getId());
                    ResultSet rez2 = ps2.executeQuery();
                    while (rez2.next()) {
                        int ProjektoId = rez2.getInt(1);
                        String ProjektoPavadinimas = rez2.getString(2);

                        Projektas p = new Projektas(ProjektoId, ProjektoPavadinimas);
                        i.pridetiProjekta(p);
                    }
                    rez2.close();
                    ps2.close();

                    break;
                }
                rez.close();
                ps.close();
                return i;
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti įmonės!");
            }
        }
        return null;
    }

    public Projektas gautiProjektoID(int id) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` = ?");
                ps.setInt(1, id);
                ResultSet rez = ps.executeQuery();

                Projektas p = null;
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Pavadinimas = rez.getString(2);

                    p = new Projektas(Id, Pavadinimas);
                    
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `zmogus` WHERE `Id` IN (SELECT `ZmogausId` FROM `zmogusprojektas` WHERE `ProjektoId` = ?)");
                    ps2.setInt(1, p.getId());
                    ResultSet rez2 = ps2.executeQuery();
                    while (rez2.next()) {
                        int ZmogausId = rez2.getInt(1);
                        String Username = rez2.getString(2);
                        String Password = rez2.getString(3);
                        String eMail = rez2.getString(4);
                        boolean Active = rez2.getBoolean(5);
                        String Vardas = rez2.getString(6);
                        String Pavarde = rez2.getString(7);

                        Vartotojas v = new Zmogus(ZmogausId, Username, Password, eMail, Active, Vardas, Pavarde);
                        p.pridetiVartotoja(v);
                    }
                    rez2.close();
                    ps2.close();
                    
                    PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM `imone` WHERE `Id` IN (SELECT `ImonesId` FROM `imoneprojektas` WHERE `ProjektoId` = ?)");
                    ps3.setInt(1, p.getId());
                    ResultSet rez3 = ps3.executeQuery();
                    while (rez3.next()) {
                        int ImonesId = rez3.getInt(1);
                        String Username = rez3.getString(2);
                        String Password = rez3.getString(3);
                        String eMail = rez3.getString(4);
                        boolean Active = rez3.getBoolean(5);
                        String ImonesPavadinimas = rez3.getString(6);

                        Vartotojas v = new Imone(ImonesId, Username, Password, eMail, Active, ImonesPavadinimas);
                        p.pridetiVartotoja(v);
                    }
                    rez3.close();
                    ps3.close();
                    
                    break;
                }
                rez.close();
                ps.close();
                return p;
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti projekto!");
            }
        }
        return null;
    }

    public Vartotojas gautiVartotojaPrisijungimas(String login) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                Statement ps = conn.createStatement();
                ResultSet rez = ps.executeQuery("SELECT * FROM zmogus");
                while (rez.next()) {
                    String Username = rez.getString(2);
                    if (Username.equals(login)) {
                        int Id = rez.getInt(1);
                        String Password = rez.getString(3);
                        String eMail = rez.getString(4);
                        boolean Active = rez.getBoolean(5);
                        String Vardas = rez.getString(6);
                        String Pavarde = rez.getString(7);
                        
                        Zmogus z = new Zmogus(Id, Username, Password, eMail, Active, Vardas, Pavarde);
                        
                        PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `zmogusprojektas` WHERE `ZmogausId` = ?)");
                        ps2.setInt(1, z.getId());
                        ResultSet rez2 = ps2.executeQuery();
                        while (rez2.next()) {
                            int ProjektoId = rez2.getInt(1);
                            String Pavadinimas = rez2.getString(2);

                            Projektas p = new Projektas(ProjektoId, Pavadinimas);
                            z.pridetiProjekta(p);
                        }
                        rez2.close();
                        ps2.close();
                        
                        return z;
                    }
                }
                rez.close();
                ps.close();
                
                ps = conn.createStatement();
                rez = ps.executeQuery("SELECT * FROM imone");
                while (rez.next()) {
                    String Username = rez.getString(2);
                    if (Username.equals(login)) {
                        int Id = rez.getInt(1);
                        String Password = rez.getString(3);
                        String eMail = rez.getString(4);
                        boolean Active = rez.getBoolean(5);
                        String Pavadinimas = rez.getString(6);
                        
                        Imone i = new Imone(Id, Username, Password, eMail, Active, Pavadinimas);
                        
                        PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM `projektas` WHERE `Id` IN (SELECT `ProjektoId` FROM `imoneprojektas` WHERE `ImonesId` = ?)");
                        ps2.setInt(1, i.getId());
                        ResultSet rez2 = ps2.executeQuery();
                        while (rez2.next()) {
                            int ProjektoId = rez.getInt(1);
                            String ProjektoPavadinimas = rez.getString(2);

                            Projektas p = new Projektas(ProjektoId, ProjektoPavadinimas);
                            i.pridetiProjekta(p);
                        }
                        rez2.close();
                        ps2.close();
                        
                        return i;
                    }
                }
                rez.close();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti vartotojo pagal slapyvardį!");
            }
        }
        return null;
    }

    public boolean uzbaigtiUzduoti(int uzduotiesId) {
        if (prisijunges != null && prisijunges.isAktyvus()) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE uzduotis SET `Pabaigta` = ?, `PabaigimoData` = ?, `PabaigtaVartotojo` = ? WHERE `Id` = ?");
                ps.setBoolean(1, true);
                ps.setDate(2, new java.sql.Date(new Date().getTime()));
                ps.setInt(3, prisijunges.getId());
                ps.setInt(4, uzduotiesId);
                ps.executeUpdate();
                ps.close();
                
                ps = conn.prepareStatement("UPDATE uzduotis SET `Pabaigta` = ?, `PabaigimoData` = ?, `PabaigtaVartotojo` = ? WHERE `Id` = ?");
                ps.setBoolean(1, true);
                ps.setDate(2, new java.sql.Date(new Date().getTime()));
                ps.setInt(3, prisijunges.getId());
                ps.setInt(4, uzduotiesId);
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Nepavyko užbaigti užduoties!");
            }
        }
        return false;
    }

    public Vartotojas gautiPrisijungusiVartotoja() {
        if (prisijunges.getClass().equals(Zmogus.class)){
            prisijunges = gautiZmoguId(prisijunges.getId());
        }
        else if (prisijunges.getClass().equals(Imone.class)){
            prisijunges = gautiImoneId(prisijunges.getId());
        }
        return prisijunges;
    }
    
    public ArrayList<Komentaras> gautiUzduotiesKomentarus(int uzduotiesId){
        
        if (prisijunges != null) {
            ArrayList<Komentaras> visi = new ArrayList();
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM `komentaras` WHERE `Uzduotis` = ?");
                ps.setInt(1, uzduotiesId);
                ResultSet rez = ps.executeQuery();
                while (rez.next()) {
                    int Id = rez.getInt(1);
                    String Tekstas = rez.getString(2);
                    
                    Komentaras k = new Komentaras(Id, Tekstas, null, null);
                    
                    
                    visi.add(k);
                }
                rez.close();
                ps.close();
                return visi;
            } catch (SQLException ex) {
                System.out.println("Nepavyko gauti komentarų!");
            }
        }
        return new ArrayList();
        
    }
    
    public boolean pridetiKomentara(String tekstas, int uzduotiesId){
        
        if (prisijunges != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO `komentaras` (`Tekstas`, `Autorius`, `Uzduotis`) VALUES (?, ?, ?)");
                ps.setString(1, tekstas);
                ps.setInt(2, prisijunges.getId());
                ps.setInt(3, uzduotiesId);
                ps.executeUpdate();
                ps.close();

                return true;
            } catch (SQLException ex) {
                System.out.println("Nepavyko sukurti naujo komentaro!"  + ex.getMessage());
            }
        }
        return false;
        
    }

}

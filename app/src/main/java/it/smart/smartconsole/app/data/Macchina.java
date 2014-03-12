package it.smart.smartconsole.app.data;

public class Macchina {

    private int id;
    private int idMacchina;
    private int oreFunzionamento;
    private int velocita;
    private int pezziProdotti;
    //private int manutenzione;
    private int codiceAllarme;
    private int energiaAssorbita;
    private int tempMotore;
    private int tempSlitta;

    public Macchina(int id, int idMacchina, int oreFunzionamento, int velocita,
                    int pezziProdotti, /*int manutenzione,*/ int codiceAllarme,
                    int energiaAssorbita, int tempMotore, int tempSlitta) {
        super();
        this.id = id;
        this.idMacchina = idMacchina;
        this.oreFunzionamento = oreFunzionamento;
        this.velocita = velocita;
        this.pezziProdotti = pezziProdotti;
        //this.manutenzione = manutenzione;
        this.codiceAllarme = codiceAllarme;
        this.energiaAssorbita = energiaAssorbita;
        this.tempMotore = tempMotore;
        this.tempSlitta = tempSlitta;
    }

    public int getId() {
        return id;
    }

    public int getIdMacchina() {
        return idMacchina;
    }

    public int getOreFunzionamento() {
        return oreFunzionamento;
    }

    public int getVelocita() {
        return velocita;
    }

    public int getPezziProdotti() {
        return pezziProdotti;
    }
    /*
        public int getManutenzione() {
            return manutenzione;
        }
    */
    public int getCodiceAllarme() {
        return codiceAllarme;
    }

    public int getEnergiaAssorbita() {
        return energiaAssorbita;
    }

    public int getTempMotore() {
        return tempMotore;
    }

    public int getTempSlitta() {
        return tempSlitta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMacchina(int idMacchina) {
        this.idMacchina = idMacchina;
    }

    public void setOreFunzionamento(int oreFunzionamento) {
        this.oreFunzionamento = oreFunzionamento;
    }

    public void setVelocita(int velocita) {
        this.velocita = velocita;
    }

    public void setPezziProdotti(int pezziProdotti) {
        this.pezziProdotti = pezziProdotti;
    }

    public void setCodiceAllarme(int codiceAllarme) {
        this.codiceAllarme = codiceAllarme;
    }

    public void setEnergiaAssorbita(int energiaAssorbita) {
        this.energiaAssorbita = energiaAssorbita;
    }

    public void setTempMotore(int tempMotore) {
        this.tempMotore = tempMotore;
    }

    public void setTempSlitta(int tempSlitta) {
        this.tempSlitta = tempSlitta;
    }

}

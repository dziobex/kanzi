public class Word {
    private int id;
    private String traditional;
    private String simplified;
    private String pinyin;
    private String meaning;
    private int level;

    public void SetId( int id ) { this.id = id; }
    public void SetTraditional( String trad ) { this.traditional = trad; }
    public void SetSimplified( String simp ) { this.simplified = simp; }
    public void SetPinyin( String pin ) { this.pinyin = pin; }
    public void SetMeaning( String mean ) { this.meaning = mean; }
    public void SetLevel( int level ) { this.level = level; }

    public int GetId() { return this.id; }
    public String GetTraditional() { return this.traditional; }
    public String GetSimplified() { return this.simplified; }
    public String GetPinyin() { return this.pinyin; }
    public String GetMeaning() { return this.meaning; }
    public int GetLevel() { return level; }

    public String GetData() {
        return String.format("ID: %s\nTRADITIONAL: %s\nSIMPLIFIED: %s\nPINYIN: %s\nMEANING: %s",
                this.id, this.traditional, this.simplified, this.pinyin, this.meaning);
    }
}

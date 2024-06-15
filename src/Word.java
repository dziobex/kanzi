public class Word {
    int id, level;
    String traditional, simplified, pinyin, meaning;

    public void setId( int id ) { this.id = id; }
    public void setTraditional( String trad ) { this.traditional = trad; }
    public void setSimplified( String simp ) { this.simplified = simp; }
    public void setPinyin( String pin ) { this.pinyin = pin; }
    public void setMeaning( String mean ) { this.meaning = mean; }
    public void setLevel( int level ) { this.level = level; }

    public int getId() { return this.id; }
    public String getTraditional() { return this.traditional; }
    public String getSimplified() { return this.simplified; }
    public String getPinyin() { return this.pinyin; }
    public String getMeaning() { return this.meaning; }
    public int getLevel() { return level; }

    public String getData() {
        return String.format("ID: %s\nTRADITIONAL: %s\nSIMPLIFIED: %s\nPINYIN: %s\nMEANING: %s",
                this.id, this.traditional, this.simplified, this.pinyin, this.meaning);
    }
}

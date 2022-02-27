package tree.table;

public abstract class Entry {

    private String name;
    private String kindOfEntry;

    public Entry(String name, String kindOfEntry){
        this.name = name;
        this.kindOfEntry = kindOfEntry;
    }

    public String getName() {
        return name;
    }

    public String getKindOfEntry() {
        return kindOfEntry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKindOfEntry(String kindOfEntry) {
        this.kindOfEntry = kindOfEntry;
    }
}

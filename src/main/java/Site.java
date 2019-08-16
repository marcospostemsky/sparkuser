public class Site implements Comparable<Site>{

    private String id;
    private String name;

    public Site() {
    }

    public Site(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "[" + id + "-" + name + "]";
    }

    @Override
    public int compareTo(Site o){
        return (this.name.compareTo(o.getName()));
    }
}
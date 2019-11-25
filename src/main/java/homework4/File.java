package homework4;

public class File {
    private Long id;
    private String name;
    private String format;
    private Long size;
    private Storage storage;

    public File(Long id, String name, String format, Long size, Storage storage) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public Long getSize() {
        return size;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (!id.equals(file.id)) return false;
        return name.equals(file.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

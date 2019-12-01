package jdbc.homework4;

public class Storage {
    private Long id;
    private String[] formatsSupported;
    private String storageCountry;
    private Long storageMaxSize;

    public Storage(Long id, String[] formatsSupported, String storageCountry, Long storageMaxSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
    }

    public Long getId() {
        return id;
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public Long getStorageMaxSize() {
        return storageMaxSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        return id.equals(storage.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

package pictureManager.domain;

public abstract class IdentifiableEntity  {

    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIdSet() {
        return this.id != null;
    }

}

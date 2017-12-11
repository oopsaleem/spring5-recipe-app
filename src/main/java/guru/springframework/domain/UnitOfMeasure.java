package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public UnitOfMeasure() {
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "UnitOfMeasure(id=" + this.getId() + ", description=" + this.getDescription() + ")";
    }
}

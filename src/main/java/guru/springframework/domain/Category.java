package guru.springframework.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

    public Category() {
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String toString() {
        return "Category(id=" + this.getId() + ", description=" + this.getDescription() + ", recipes=" + this.getRecipes() + ")";
    }
}
package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repo;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        repo.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }
}

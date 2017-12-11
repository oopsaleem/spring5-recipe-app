package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl service;

    @Mock
    RecipeRepository repo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new RecipeServiceImpl(repo);
    }
    
    @Test
    public void getRecipeByIdTest() throws Exception {
    	Recipe recipe = new Recipe();
    	recipe.setId(1L);
    	
    	Optional<Recipe> recipeOpt = Optional.of(recipe);
    	
    	when(repo.findById(anyLong())).thenReturn(recipeOpt);

    	Recipe returnedRecipe = service.findById(1L);
    	assertNotNull("Null recipe was retrieved !", returnedRecipe);
    	verify(repo, times(1)).findById(anyLong());
    	verify(repo, never()).findAll();
    }

    @Test
    public void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        when(service.getRecipes()).thenReturn(recipes);

        Set<Recipe> recipeSet = service.getRecipes();

        assertEquals(recipeSet.size(), 1);
        Mockito.verify(repo, Mockito.times(1)).findAll();
    }
    
}
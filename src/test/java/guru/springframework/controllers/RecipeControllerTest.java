package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    RecipeController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
    }

    @Test
    public void getAllRecipes() throws Exception {
        //Given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);
        @SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //When
        String viewName = controller.getAllRecipes(model);

        //Then
        assertEquals("recipes", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(3, setInController.size());
    }

    @Test
    public void testMockMVC() throws Exception {
        //Given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);

        //Given resolver
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".jsp");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new RecipeController(recipeService))
                .setViewResolvers(viewResolver)
                .build();

        //then
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists())
                .andExpect(model().attribute("recipes", hasSize(3)))
                .andExpect(view().name("recipes"));
    }
}
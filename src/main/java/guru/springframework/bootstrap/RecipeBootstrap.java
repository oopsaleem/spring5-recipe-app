package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository catRepo;
    private final RecipeRepository recipeRepo;
    private final UnitOfMeasureRepository uomRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepo.saveAll(getRecipes());
    }

    @Autowired
    public RecipeBootstrap(CategoryRepository catRepo, RecipeRepository recipeRepo, UnitOfMeasureRepository uomRepo) {
        this.catRepo = catRepo;
        this.recipeRepo = recipeRepo;
        this.uomRepo = uomRepo;
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUomOpt = uomRepo.findByDescription("Each");
        if(!eachUomOpt.isPresent()) throw new RuntimeException("Each UoM not found!");

        Optional<UnitOfMeasure> teaspoonUomOpt = uomRepo.findByDescription("Teaspoon");
        if(!teaspoonUomOpt.isPresent()) throw new RuntimeException("Teaspoon UoM not found!");

        UnitOfMeasure eachUoM = eachUomOpt.get();
        UnitOfMeasure teaspoonUoM = teaspoonUomOpt.get();

        Optional<Category> taiziCatOpt = catRepo.findByDescription("Taizi");
        if(!taiziCatOpt.isPresent()) throw new RuntimeException("Taizi category not found!");

        Optional<Category> haimiCatOpt = catRepo.findByDescription("Haimi");
        if(!haimiCatOpt.isPresent()) throw new RuntimeException("Haimi category not found!");

        Category taiziCat = taiziCatOpt.get();
        Category haimiCat = haimiCatOpt.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.getCategories().add(taiziCat);
        guacRecipe.getCategories().add(haimiCat);
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.\n"
                + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"
                + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
                + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
                + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
                + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
                + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n"
        );

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz50dZ3LBsw\n"
        );

        guacRecipe.setNotes(guacNotes);


        guacRecipe.addIngredients(new Ingredient("ripe avocados", new BigDecimal(2), eachUoM));
        guacRecipe.addIngredients(new Ingredient("Tbsp of fresh lime juice or lemon juice", new BigDecimal(1), teaspoonUoM));
        recipes.add(guacRecipe);

        return recipes;
    }
}

package com.denysiuk.andriy.culinaryassistant.model

import com.denysiuk.andriy.culinaryassistant.ui.Ingredient
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType

class Model @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://api.spoonacular.com")
        .build()
    private val api: RecipeRequest by lazy {
        retrofit.create(RecipeRequest::class.java)
    }
    suspend fun refresh(query: String): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            //val request = api.request(query)
            val request = CulinaryResponse(offset=0, number=10, results=arrayOf(Recipe(id=715415, title="Red Lentil Soup with Chicken and Turnips", image="https://img.spoonacular.com/recipes/715415-312x231.jpg", imageType="jpg"), Recipe(id=716406, title="Asparagus and Pea Soup: Real Convenience Food", image="https://img.spoonacular.com/recipes/716406-312x231.jpg", imageType="jpg"), Recipe(id=715446, title="Slow Cooker Beef Stew", image="https://img.spoonacular.com/recipes/715446-312x231.jpg", imageType="jpg"), Recipe(id=782601, title="Red Kidney Bean Jambalaya", image="https://img.spoonacular.com/recipes/782601-312x231.jpg", imageType="jpg"), Recipe(id=716426, title="Cauliflower, Brown Rice, and Vegetable Fried Rice", image="https://img.spoonacular.com/recipes/716426-312x231.jpg", imageType="jpg"), Recipe(id=716627, title="Easy Homemade Rice and Beans", image="https://img.spoonacular.com/recipes/716627-312x231.jpg", imageType="jpg"), Recipe(id=664147, title="Tuscan White Bean Soup with Olive Oil and Rosemary", image="https://img.spoonacular.com/recipes/664147-312x231.jpg", imageType="jpg"), Recipe(id=644387, title="Garlicky Kale", image="https://img.spoonacular.com/recipes/644387-312x231.jpg", imageType="jpg"), Recipe(id=640941, title="Crunchy Brussels Sprouts Side Dish", image="https://img.spoonacular.com/recipes/640941-312x231.jpg", imageType="jpg"), Recipe(id=660306, title="Slow Cooker: Pork and Garbanzo Beans", image="https://img.spoonacular.com/recipes/660306-312x231.jpg", imageType="jpg")).toList(), totalResults=3646) //TODO("Change this to actual request")
            try {
                Result.success(request.results)
            } catch(e: Exception){
                Result.failure<CulinaryResponse>(e)
            } as Result<List<Recipe>>
        }
    }
    suspend fun requestRecipe(id: String): Result<DetailedRecipe>{
        return withContext(Dispatchers.IO) {
//                val request = api.requestRecipe(id = Integer.valueOf(id))
                try {
//                    Result.success(request)
                    Result.success(DetailedRecipe(id=715415, title="Red Lentil Soup with Chicken and Turnips", image="https://img.spoonacular.com/recipes/715415-556x370.jpg", source="https://www.pinkwhen.com/red-lentil-soup-with-chicken-and-turnips/", readiness=55, servings=8, author="pinkwhen.com", types=listOf("lunch", "soup", "main course", "main dish", "dinner"), ingredients=listOf(Ingredient(name = "additional toppings : avocado",amount = 8.0,unit ="servings",state = "additional toppings : diced avocado,micro greens,chopped basil"), Ingredient(name="carrots", amount=3.0, unit="medium", state="carrots, peeled and diced"), Ingredient(name="celery stalks", amount=3.0, unit="", state="celery stalks, diced"), Ingredient(name="chicken breast", amount=2.0, unit="cups", state="fully-cooked chicken breast, shredded (may be omitted for a vegetarian version)"), Ingredient(name="flat leaf parsley", amount=0.5, unit="cup", state="flat leaf Italian parsley, chopped (plus extra for garnish)"), Ingredient(name="garlic", amount=6.0, unit="cloves", state="garlic, finely minced"), Ingredient(name="olive oil", amount=2.0, unit="tablespoons", state="olive oil"), Ingredient(name="canned tomatoes", amount=28.0, unit="ounce", state="can plum tomatoes, drained and rinsed, chopped"), Ingredient(name="lentils", amount=2.0, unit="cups", state="dried red lentils, rinsed"), Ingredient(name="salt and pepper", amount=8.0, unit="servings", state="salt and black pepper, to taste"), Ingredient(name="turnip", amount=1.0, unit="large", state="turnip, peeled and diced"), Ingredient(name="vegetable stock", amount=8.0, unit="cups", state="vegetable stock"), Ingredient(name="onion", amount=1.0, unit="medium", state="yellow onion, diced")), instructions="To a large dutch oven or soup pot, heat the olive oil over medium heat. Add the onion, carrots and celery and cook for 8-10 minutes or until tender, stirring occasionally. Add the garlic and cook for an additional 2 minutes, or until fragrant. Season conservatively with a pinch of salt and black pepper.To the pot, add the tomatoes, turnip and red lentils. Stir to combine. Stir in the vegetable stock and increase the heat on the stove to high. Bring the soup to a boil and then reduce to a simmer. Simmer for 20 minutes or until the turnips are tender and the lentils are cooked through. Add the chicken breast and parsley. Cook for an additional 5 minutes. Adjust seasoning to taste.Serve the soup immediately garnished with fresh parsley and any additional toppings. Enjoy!"))
                    //Result.success(DetailedRecipe(Integer.valueOf(id), "", "", "", -1, -1, "", listOf(), listOf(), "", ""))
                } catch (e: Exception) {
                    Result.failure(e)
                } as Result<DetailedRecipe>
            }
    }
}

@Serializable
data class Recipe(val id: Int, val title: String, val image: String, val imageType: String){}
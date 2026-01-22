package com.denysiuk.andriy.culinaryassistant.ui

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.denysiuk.andriy.culinaryassistant.CulinaryIntent
import com.denysiuk.andriy.culinaryassistant.R
import com.denysiuk.andriy.culinaryassistant.model.DetailedRecipe
import com.denysiuk.andriy.culinaryassistant.ui.viewmodels.RecipeViewModel
import com.denysiuk.andriy.culinaryassistant.ui.viewmodels.UiEffect
import java.text.NumberFormat

class RecipeActivity(val id: Int) {
}
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun loadRecipe(id: String, viewModel: RecipeViewModel = hiltViewModel(), goBack: () -> Unit) {
    val state: RecipeState by viewModel.state.collectAsState()
    viewModel.handle(CulinaryIntent.requestRecipe(id))
    val recipe: DetailedRecipe = state.recipe
    val context = LocalContext.current
    /*Text(text = id.toString())
}*/
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UiEffect.OpenWebPage -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    Image(painter = painterResource(R.drawable.ic_arrow_back_24), contentDescription = "back", modifier = Modifier.clickable(true) { goBack.invoke() }.widthIn(25.dp, 30.dp).fillMaxWidth(0.2f).aspectRatio(1.0F))
                },
                title = {
                    Row {
                        Text(recipe.title, modifier = Modifier.weight(75.0f)
                            .padding(0.dp, 0.dp, 10.dp, 0.dp))
                        Column(
                            horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()
                                .weight(25.0f)
                                .padding(10.dp)
                                .clickable(true){
                                    println("Clicked!")
                                    viewModel.handle(CulinaryIntent.openRecipeInBrowser(recipe.source))
                                }, verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_source_24),
                                contentDescription = "ImageSource",
                                alignment = Alignment.TopEnd,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                            var size by remember { mutableStateOf(15.sp) }
                            Text(text = recipe.author, maxLines = 1, fontSize = size, onTextLayout = {
                                if (it.multiParagraph.didExceedMaxLines) {
                                    size = (size.value * .9F).sp
                                }
                            })
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
        ) {
                Column() {
                    var ingredientsExpanded by remember { mutableStateOf(false) }
                    Column(modifier = Modifier.wrapContentHeight().animateContentSize()
                            .clickable(true) { ingredientsExpanded = !ingredientsExpanded }
                            .weight(30.0f, false)) { //Ingredients
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = stringResource(R.string.ingredients_text), fontSize = 20.sp)
                            Image(
                                painter = painterResource(R.drawable.ic_arrow_down_no_body_24dp),
                                contentDescription = "ArrowDown",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                            )
                        }
                        if (ingredientsExpanded) {
                            LazyColumn() {
                                items(recipe.ingredients) { ingredient ->
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = ingredient.name + " " + NumberFormat.getInstance()
                                                .format(ingredient.amount) + " " + ingredient.unit,
//                                    modifier = Modifier.height(10.dp),
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(0.dp, 0.dp)
                                        )
                                        //Text(text = ingredient.state, fontSize = 15.sp, maxLines = 1,) //modifier = Modifier.padding(2.dp, 0.dp))
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(0.dp, 2.dp)
                            .weight(20.0f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) { //Image, servings and readiness
                        Box(modifier = Modifier.wrapContentSize()) {
                            GlideImage(model = recipe.image, contentDescription = "ImageDish", alignment = Alignment.Center/*, contentScale = ContentScale.Crop*/)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CutCornerShape(2.dp)
                                )
                                .aspectRatio(1.0f)
                                .padding(4.dp, 4.dp)
                        ) {
                            Image(painter = painterResource(R.drawable.ic_dish_24px), contentDescription = "servings_icon", colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
                            Text(text = recipe.servings.toString())
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RectangleShape
                                )
                                .aspectRatio(1.0f)
                                .padding(4.dp, 4.dp)
                        ) {
                            Image(painter = painterResource(R.drawable.ic_clock_24px), contentDescription = "readiness_icon", colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
                            Text(text = recipe.readiness.toString(), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    Row(modifier = Modifier.weight(60.0f)) {
                        Column(
                            modifier = Modifier.fillMaxWidth()

                                .weight(65.0f)
                        ) { //Instructions
                            Text(text = "Instructions", fontSize = 20.sp)
                            Text(text = recipe.instructions, fontSize = 15.sp, modifier = Modifier.verticalScroll(rememberScrollState()))
                        }
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun previewRecipe(){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    Image(painter = painterResource(R.drawable.ic_arrow_back_24), contentDescription = "back")
                },
                title = {
                    Row {
                        Text("Title")
                        Column(
                            horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()
                                .weight(10.0f), verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_source_24),
                                contentDescription = "ImageSource",
                                alignment = Alignment.TopEnd,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                            Text(text = "Author", maxLines = 1)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface() {
            Column(Modifier.padding(innerPadding)) {
                var ingredientsExpanded by remember { mutableStateOf(true) }
                Column(modifier = Modifier.wrapContentHeight().animateContentSize()
                    .clickable(true) { ingredientsExpanded = !ingredientsExpanded }.weight(30.0f, false)) { //Ingredients
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(5.dp, 5.dp)
                    ) {
                        Text(text = stringResource(R.string.ingredients_text), fontSize = 20.sp)
                        Image(
                            painter = painterResource(R.drawable.ic_arrow_down_no_body_24dp),
                            contentDescription = "ArrowDown",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)

                        )
                    }
                    if (ingredientsExpanded) {
                        LazyColumn() {
                            items(
                                listOf(
                                    Ingredient(
                                        name = "additional toppings : avocado",
                                        amount = 8.0,
                                        unit = "servings",
                                        state = "additional toppings : diced avocado,micro greens,chopped basil"
                                    ),
                                    Ingredient(
                                        name = "carrots",
                                        amount = 3.0,
                                        unit = "medium",
                                        state = "carrots, peeled and diced"
                                    ),
                                    Ingredient(
                                        name = "celery stalks",
                                        amount = 3.0,
                                        unit = "",
                                        state = "celery stalks, diced"
                                    ),
                                    Ingredient(
                                        name = "chicken breast",
                                        amount = 2.0,
                                        unit = "cups",
                                        state = "fully-cooked chicken breast, shredded (may be omitted for a vegetarian version)"
                                    ),
                                    Ingredient(
                                        name = "flat leaf parsley",
                                        amount = 0.5,
                                        unit = "cup",
                                        state = "flat leaf Italian parsley, chopped (plus extra for garnish)"
                                    ),
                                    Ingredient(
                                        name = "garlic",
                                        amount = 6.0,
                                        unit = "cloves",
                                        state = "garlic, finely minced"
                                    ),
                                    Ingredient(
                                        name = "olive oil",
                                        amount = 2.0,
                                        unit = "tablespoons",
                                        state = "olive oil"
                                    ),
                                    Ingredient(
                                        name = "canned tomatoes",
                                        amount = 28.0,
                                        unit = "ounce",
                                        state = "can plum tomatoes, drained and rinsed, chopped"
                                    ),
                                    Ingredient(
                                        name = "lentils",
                                        amount = 2.0,
                                        unit = "cups",
                                        state = "dried red lentils, rinsed"
                                    ),
                                    Ingredient(
                                        name = "salt and pepper",
                                        amount = 8.0,
                                        unit = "servings",
                                        state = "salt and black pepper, to taste"
                                    ),
                                    Ingredient(
                                        name = "turnip",
                                        amount = 1.0,
                                        unit = "large",
                                        state = "turnip, peeled and diced"
                                    ),
                                    Ingredient(
                                        name = "vegetable stock",
                                        amount = 8.0,
                                        unit = "cups",
                                        state = "vegetable stock"
                                    ),
                                    Ingredient(
                                        name = "onion",
                                        amount = 1.0,
                                        unit = "medium",
                                        state = "yellow onion, diced"
                                    )
                                )
                            ) { ingredient ->
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = ingredient.name + " " + NumberFormat.getInstance()
                                            .format(ingredient.amount) + " " + ingredient.unit,
                                        fontSize = 15.sp,
                                    )
                                    //Text(text = ingredient.state, fontSize = 15.sp, maxLines = 1,) //modifier = Modifier.padding(2.dp, 0.dp))
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(20.0f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box() {
                        Image(
                            painter = painterResource(R.drawable.ic_test_24dp),
                            contentDescription = "ImageDish",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(50.dp)
                            .aspectRatio(1.0f, true)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(size = 2.dp)
                            )
                            .padding(4.dp, 4.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_dish_24px),
                            contentDescription = "servings_icon",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        )
                        Text(text = "2", fontWeight = FontWeight.ExtraBold)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(50.dp)
                            .aspectRatio(1.0f, true)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(4.dp, 4.dp),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_clock_24px),
                            contentDescription = "readiness_icon",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        )
                        Text(text = "45 min", fontWeight = FontWeight.ExtraBold)
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .weight(60.0f)
                ) {
                    Text(text = "Description", fontSize = 20.sp)
                    Text(text = "Instructions", fontSize = 20.sp)
                }
            }
        }
    }
}
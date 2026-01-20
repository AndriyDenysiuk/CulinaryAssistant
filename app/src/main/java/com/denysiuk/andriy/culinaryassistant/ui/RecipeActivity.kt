package com.denysiuk.andriy.culinaryassistant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.denysiuk.andriy.culinaryassistant.CulinaryIntent
import com.denysiuk.andriy.culinaryassistant.R
import com.denysiuk.andriy.culinaryassistant.model.DetailedRecipe
import com.denysiuk.andriy.culinaryassistant.ui.viewmodels.RecipeViewModel

class RecipeActivity(val id: Int) {
}
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun loadRecipe(id: String, viewModel: RecipeViewModel = hiltViewModel()) {
    val state: RecipeState by viewModel.state.collectAsState()
    viewModel.handle(CulinaryIntent.requestRecipe(id))
    val recipe: DetailedRecipe = state.recipe
    /*Text(text = id.toString())
}*/
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(recipe.title) }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
        ) {
            Column() {
                Row() {
                    Column(modifier = Modifier.weight(90.0f)) {
//                        Text(text = recipe.title, fontSize = 30.sp)
                        LazyRow(horizontalArrangement = Arrangement.SpaceBetween, contentPadding = PaddingValues(2.dp, 0.dp)) {
                            items(recipe.ingredients) { ingredient ->
                                Text(text = ingredient.name, fontSize = 15.sp)
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()
                            .weight(10.0f), verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_source_24),
                            contentDescription = "ImageSource",
                            alignment = Alignment.TopEnd,
                            colorFilter = ColorFilter.Companion.tint(Color.White)
                        )
                        Text(text = recipe.author)
                    }
                }
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .weight(65.0f)
                    ) {
//                    Text(text = "Description", fontSize = 20.sp)
//                    Text(text = recipe.description, fontSize = 15.sp)
                        Text(text = "Instructions", fontSize = 20.sp)
                        Text(text = recipe.instructions, fontSize = 15.sp)
                    }
                    Column(modifier = Modifier.weight(35.0f)) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            GlideImage(model = recipe.image, contentDescription = "ImageDish")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = CutCornerShape(2.dp)
                                )
                                    .padding(2.dp, 0.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.servings_text),
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                                Text(text = recipe.servings.toString())
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = CutCornerShape(2.dp)
                                )
                                    .padding(2.dp, 0.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.readiness_text),
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                                Text(text = recipe.readiness.toString())
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun previewRecipe(){
    Surface {
        Column(){
            Row(){
                Column {
                    Text(text = "Title", fontSize = 30.sp)
                    Text(text = "Ingredients", fontSize = 15.sp)
                }
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(R.drawable.ic_test_24dp),
                        contentDescription = "source",
                        alignment = Alignment.TopEnd
                    )
                    Text(text = "author")
                }
            }
            Row{
                Column(modifier = Modifier.fillMaxWidth()
                    .weight(65.0f)) {
                    Text(text = "Description", fontSize = 20.sp)
                    Text(text = "Instructions", fontSize = 20.sp)
                }
                Column(/*horizontalAlignment = Alignment.End,*/ modifier = Modifier.weight(35.0f)) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                        Image(painter = painterResource(R.drawable.ic_test_24dp), contentDescription = "ImageDish")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.border(width = 1.dp, color = Color.Black, shape = CutCornerShape(2.dp))
                            .padding(2.dp, 0.dp)){
                            Text(text = "servings")
                            Text(text = "2")
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.border(width = 1.dp, color = Color.Black, shape = CutCornerShape(2.dp))
                            .padding(2.dp, 0.dp)){
                            Text(text = "readiness", maxLines = 1, overflow = TextOverflow.Clip)
                            Text(text = "45 min")
                        }
                    }
                }
            }

        }
    }
}
//title: String, image: String, source: String, readiness: Int, servings: Int, author: String, types: List<String>,
// ingredients: List<Ingredient>, description: String, instructions: String*/
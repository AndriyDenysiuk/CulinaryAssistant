package com.denysiuk.andriy.culinaryassistant.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.denysiuk.andriy.culinaryassistant.CulinaryIntent
import com.denysiuk.andriy.culinaryassistant.R
import com.denysiuk.andriy.culinaryassistant.model.Recipe
import com.denysiuk.andriy.culinaryassistant.ui.theme.CulinaryAssistantTheme
import com.denysiuk.andriy.culinaryassistant.ui.viewmodels.SearchViewModel


class SearchActivity: ComponentActivity() {
}
@Composable
fun initializeSearchScreen(viewModel: SearchViewModel = hiltViewModel(), switch: (String) -> Unit) {
    val state: SearchState by viewModel.state.collectAsState()
    CulinaryAssistantTheme() {
        Surface() {
            Column() {
                Box(modifier = Modifier
                    .padding(10.dp, 40.dp, 10.dp, 0.dp)
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.query,
                        placeholder = { Text(text = stringResource(R.string.searchField_hint)) },
                        label = { Text(text = stringResource(R.string.searchField_name)) },
                        onValueChange = {
                            viewModel.handle(CulinaryIntent.SearchIntent(it))
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                ) {
                    LazyColumn() {
                        items(state.recipes) {
                            renderRecipe(it, viewModel, switch)
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun previewSearch(){
    CulinaryAssistantTheme() {
        var text by remember { mutableStateOf("")}
        Surface() {
            Column() {
                Box(modifier = Modifier
                    .padding(10.dp, 40.dp, 10.dp, 0.dp)
                    .fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = text,
                        placeholder = { Text(text = stringResource(R.string.searchField_hint)) },
                        label = { Text(text = stringResource(R.string.searchField_name)) },
                        onValueChange = {
                            text = it
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()
                        .padding(10.dp, 5.dp)){
                        Text(text = "Recipe Title", Modifier.weight(85.0f))
                        Box(modifier = Modifier.fillMaxWidth()
                            .weight(15.0f),
                            contentAlignment = Alignment.CenterEnd) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_test_24dp),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun renderRecipe(recipe: Recipe, viewModel: SearchViewModel = hiltViewModel(), switch: (String) -> Unit){
    //val image = BitmapFactory.decodeStream(context.contentResolver.openInputStream(Uri.parse(recipe.image)))
    Row(modifier = Modifier.fillMaxWidth()
        .padding(10.dp, 5.dp)
        .clickable(true){
            viewModel.handle(intent = CulinaryIntent.openRecipe(recipe.id.toString(), switch))
        }){
        Text(text = recipe.title, Modifier.weight(85.0f))
        Box(modifier = Modifier.fillMaxWidth()
            .weight(15.0f),
            contentAlignment = Alignment.CenterEnd) {
            GlideImage(model = recipe.image, contentDescription = "", alignment = Alignment.CenterEnd)
        }
    }
}
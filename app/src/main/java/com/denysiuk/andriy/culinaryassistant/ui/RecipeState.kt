package com.denysiuk.andriy.culinaryassistant.ui

import com.denysiuk.andriy.culinaryassistant.model.DetailedRecipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RecipeState(val recipe: DetailedRecipe, val isLoading: Boolean){
}
@Serializable
data class Ingredient (val name: String, val amount: Double, val unit: String, @SerialName("originalName") val state: String){}
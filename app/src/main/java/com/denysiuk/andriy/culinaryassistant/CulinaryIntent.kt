package com.denysiuk.andriy.culinaryassistant

sealed class CulinaryIntent {
    object searchRecipes: CulinaryIntent()
    data class SearchIntent(val query: String): CulinaryIntent()
    data class openRecipe(val id: String, val navigate: (String) -> Unit): CulinaryIntent()
    data class requestRecipe(val id: String): CulinaryIntent()
    data class openRecipeInBrowser(val url: String): CulinaryIntent()
}
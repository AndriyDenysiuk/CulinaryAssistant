package com.denysiuk.andriy.culinaryassistant.ui

import com.denysiuk.andriy.culinaryassistant.model.Recipe

data class SearchState(val query: String, val recipes: List<Recipe>, val isLoading: Boolean) {
}
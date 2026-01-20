package com.denysiuk.andriy.culinaryassistant.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denysiuk.andriy.culinaryassistant.CulinaryIntent
import com.denysiuk.andriy.culinaryassistant.model.DetailedRecipe
import com.denysiuk.andriy.culinaryassistant.model.Model
import com.denysiuk.andriy.culinaryassistant.ui.RecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel @Inject constructor(val model: Model): ViewModel() {
    private val _state = MutableStateFlow(RecipeState(DetailedRecipe(-1, "", "", "", -1, -1, "", listOf(), listOf(), "", /*""*/), false))
    val state = _state.asStateFlow()
    fun handle(intent: CulinaryIntent){
        when (intent) {
            is CulinaryIntent.requestRecipe -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    model.requestRecipe(intent.id)
                        .onSuccess { recipe ->
                            println(recipe)
                            _state.update { it.copy(recipe = recipe, isLoading = false) }
                        }
                        .onFailure {
                            _state.update { it.copy(isLoading = false) }
                            throw RuntimeException("Failed to retrieve a detailed recipe.")
                        }
                }
            }
            else -> println(intent)
        }
    }
}
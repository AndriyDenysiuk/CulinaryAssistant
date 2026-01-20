package com.denysiuk.andriy.culinaryassistant.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denysiuk.andriy.culinaryassistant.CulinaryIntent
import com.denysiuk.andriy.culinaryassistant.model.Model
import com.denysiuk.andriy.culinaryassistant.ui.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(val model: Model): ViewModel() {
    private val _state = MutableStateFlow(SearchState("", listOf(), false))
    val state = _state.asStateFlow()
    fun handle(intent: CulinaryIntent) {
        when (intent) {
            is CulinaryIntent.SearchIntent -> {
                _state.update { it.copy(isLoading = true, query = intent.query) }
                //withContext(Dispatchers.IO, {model.refresh(intent.query)})
                viewModelScope.launch{
                    try {
                        model.refresh(intent.query)
                            .onSuccess { results ->
                                _state.update { it.copy(isLoading = false, recipes = results) }
                            }
                            .onFailure { e ->
                                _state.update { it.copy(isLoading = false, recipes = listOf()) }
                                println(e.message)
                            }
                    }
                    catch (e: Exception){
                        println(e)
                    }
                }
            }
            is CulinaryIntent.openRecipe -> {
                println(intent.id)
                intent.navigate.invoke(intent.id)
            }
            else -> println(intent)
        }
    }
}
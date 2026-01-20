package com.denysiuk.andriy.culinaryassistant.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    fun searchRecipes(navigate: () -> Unit?) {
        println("Here!")
        navigate.invoke()
    }
}
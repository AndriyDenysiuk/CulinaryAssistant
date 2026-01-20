package com.denysiuk.andriy.culinaryassistant.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.denysiuk.andriy.culinaryassistant.model.Recipe

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.RecipeView>() {
    private val recipes = mutableListOf<Recipe>()

    fun setRecipes(recipes: List<Recipe>){
        this.recipes.clear()
        this.recipes.addAll(recipes)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeView {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecipeView, position: Int) {
        holder.render(recipes.getOrNull(position))
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
    class RecipeView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(recipe: Recipe?){

        }
    }
}

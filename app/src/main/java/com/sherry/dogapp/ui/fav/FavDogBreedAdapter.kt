package com.sherry.dogapp.ui.fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherry.dogapp.databinding.ItemDogInfoBinding
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.utils.loadImageFromURL

class FavDogBreedAdapter(
    private var favDogBreedList: List<DogBreedEntity>,
    private val click: (DogBreedEntity, Int) -> Unit
) : RecyclerView.Adapter<FavDogBreedAdapter.ViewHolder>() {

    fun updateList(dogList: List<DogBreedEntity>, position: Int) {
        favDogBreedList = dogList
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favDogBreedList[position], click)
    }

    override fun getItemCount(): Int = favDogBreedList.size

    class ViewHolder private constructor(private val binding: ItemDogInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favDogBreed: DogBreedEntity, click: (DogBreedEntity, Int) -> Unit) {
            with(binding) {
                tvDogName.text = favDogBreed.breedName.uppercase()
                tvSubtypesCount.text = favDogBreed.subTypeCount.toString()
                favDogBreed.breedImageUrl?.let { path ->
                    loadImageFromURL(root.context, path, ivDogImage)
                }

                ivFav.visibility = View.GONE
                ivDelete.visibility = View.VISIBLE

                ivDelete.setOnClickListener {
                    click.invoke(favDogBreed, adapterPosition)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDogInfoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
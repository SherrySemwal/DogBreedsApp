package com.sherry.dogapp.ui.dog_breed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sherry.dogapp.databinding.ItemDogInfoBinding
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.utils.loadImageFromURL
import java.util.Collections.emptyList

class DogBreedAdapter(private val click: (DogBreedEntity, Int) -> Unit) :
    RecyclerView.Adapter<DogBreedAdapter.ViewHolder>() {

    private var dogBreedList:  List<DogBreedEntity> = emptyList()

    fun setList(dogList: List<DogBreedEntity>) {
        dogBreedList = dogList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dogBreedList[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = dogBreedList.size

    class ViewHolder private constructor(private val binding: ItemDogInfoBinding, ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DogBreedEntity, click: (DogBreedEntity, Int) -> Unit) {
            with(binding) {
                tvDogName.text = item.breedName.uppercase()
                tvSubtypesCount.text = item.subTypeCount.toString()
                ivFav.isSelected = item.isFav
                item.breedImageUrl?.let { path ->
                    loadImageFromURL(root.context, path, ivDogImage)
                }
                ivFav.setOnClickListener {
                    updateIsFav(ivFav, item)
                    click.invoke(item, adapterPosition)
                }
            }
        }

        private fun updateIsFav(dogImage: ImageView, item: DogBreedEntity){
            item.isFav = !item.isFav
            dogImage.isSelected = item.isFav
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
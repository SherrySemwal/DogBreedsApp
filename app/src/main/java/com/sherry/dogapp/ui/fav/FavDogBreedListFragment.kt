package com.sherry.dogapp.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sherry.dogapp.R
import com.sherry.dogapp.databinding.FragmentFavDogBreedListBinding
import com.sherry.dogapp.db.entity.DogBreedEntity
import com.sherry.dogapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavDogBreedListFragment : Fragment() {

    private val favDogBreedViewModel: FavDogBreedViewModel by viewModels()
    private var binding: FragmentFavDogBreedListBinding? = null
    private var favDogBreedAdapter: FavDogBreedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavDogBreedListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        collectData()
    }

    private fun initUi() {
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favDogBreedViewModel.favDogBreedUiState.collect { uiState ->
                    when (uiState) {
                        is FavDogBreedUiState.Loading -> {
                            binding?.pb?.visibility = View.VISIBLE
                        }
                        is FavDogBreedUiState.Success -> {
                            dismissLoading()
                            if (favDogBreedAdapter == null) {
                                setUpRecyclerView(uiState.data)
                            } else{
                                favDogBreedAdapter?.updateList(uiState.data, favDogBreedViewModel.removedPosition)
                            }

                            if(uiState.data.isEmpty()){
                                Toast.makeText(requireContext(), getString(R.string.no_favorities), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(favDogBreedList: List<DogBreedEntity>) {
        favDogBreedAdapter = FavDogBreedAdapter(favDogBreedList) { item, pos ->
            favDogBreedViewModel.deleteDogBreedFromFav(item.breedName)
            favDogBreedViewModel.removedPosition = pos
        }
        binding?.recyclerView?.apply {
            adapter = favDogBreedAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favDogBreedAdapter = null
        binding = null
    }

    private fun dismissLoading() {
        binding?.pb?.visibility = View.GONE
    }
}
package com.sherry.dogapp.ui.dog_breed

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sherry.dogapp.R
import com.sherry.dogapp.databinding.FragmentDogBreedsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DogBreedListFragment : Fragment(), MenuProvider {

    private val dogBreedViewModel: DogBreedViewModel by viewModels()
    private var binding: FragmentDogBreedsBinding? = null
    private var dogBreedAdapter: DogBreedAdapter? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDogBreedsBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        collectData()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.action_favourite ->{
                findNavController().navigate(R.id.action_dogFragment_to_dogBreedFavFragment)
                true
            }else -> false
        }
    }

    private fun initUi(){
        dogBreedAdapter = DogBreedAdapter{ item, pos ->
            dogBreedViewModel.addDogBreedInFav(item.breedName, item.isFav)
            dogBreedAdapter?.notifyItemChanged(pos)
        }
        binding?.recyclerView?.apply{
            adapter = dogBreedAdapter
        }
    }

    private fun collectData(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dogBreedViewModel.dogBreedListUiState.collect { uiState ->
                    when(uiState){
                        is DogBreedUiState.Loading -> {
                            binding?.pb?.visibility = View.VISIBLE
                        }
                        is DogBreedUiState.Success -> {
                            dismissLoading()
                            dogBreedAdapter?.setList(uiState.data)
                        }
                        is DogBreedUiState.Error -> {
                            dismissLoading()
                            Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dogBreedAdapter = null
        binding = null
    }

    private fun dismissLoading() {
        binding?.pb?.visibility = View.GONE
    }
}
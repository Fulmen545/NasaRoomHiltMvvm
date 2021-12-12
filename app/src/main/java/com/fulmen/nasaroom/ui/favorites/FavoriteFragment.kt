package com.fulmen.nasaroom.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fulmen.nasaroom.R
import com.fulmen.nasaroom.databinding.NasaListFragmentBinding
import com.fulmen.nasaroom.ui.SharedViewModel
import com.fulmen.nasaroom.ui.mainlist.NasaAdapter
import com.fulmen.nasaroom.util.Resource
import com.senacor.nasamvvm.data.model.ItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: Fragment(), NasaAdapter.ItemListener {

    private lateinit var binding: NasaListFragmentBinding
    private lateinit var nasaAdapter: NasaAdapter
    private lateinit var sharedViewModel: SharedViewModel

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NasaListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        setUpRecyclerView()
        setUpObserver()
    }

    private fun setUpRecyclerView() {
        nasaAdapter = NasaAdapter(this)
        binding.nasaBlogs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nasaAdapter
        }
    }

    private fun  setUpObserver(){
        viewModel.res.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    nasaAdapter.setItems(ArrayList(it.data))

                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progress.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedItem(blog: ItemData) {
        sharedViewModel.updateData(blog)
        findNavController().navigate(
            R.id.action_FavoriteFragment_to_DetailFragment
        )
    }


}
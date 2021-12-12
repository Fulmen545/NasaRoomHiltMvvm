package com.fulmen.nasaroom.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fulmen.nasaroom.R
import com.fulmen.nasaroom.databinding.DetailFragmentBinding
import com.fulmen.nasaroom.ui.SharedViewModel
import com.fulmen.nasaroom.util.Resource
import com.senacor.nasamvvm.data.model.ItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment() {

    private lateinit var binding: DetailFragmentBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var itemData: ItemData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        setUpSharedObserver()
        setUpExist()
        binding.heartImg.setOnClickListener {
            if (itemData.favorite) {
                viewModel.deleteFavorite(itemData.link)
                binding.heartImg.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                viewModel.insertFavorite(itemData.copy(favorite = true))
                binding.heartImg.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
        }
    }

    private fun setUpSharedObserver() {
        sharedViewModel.detailData.observe(viewLifecycleOwner, { item ->
            viewModel.isFavorite(item.link)
            itemData = item
            binding.apply {
                titleTv.text = item.title
                dateInputTv.text = item.pubDate
                urlInputTv.text = item.link
                descTv.text = item.description.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
                Glide.with(binding.root)
                    .load(item.thumbnail)
                    .fitCenter()
                    .into(image)

            }
        })
    }

    private fun setUpExist() {
        viewModel.exist.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressDetail.visibility = View.GONE
                    it.data?.let {
                        if (it) {
                            itemData = itemData.copy(favorite = true)
                            binding.heartImg.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        } else {
                            itemData = itemData.copy(favorite = false)
                            binding.heartImg.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.heartImg.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }
                Resource.Status.LOADING ->
                    binding.progressDetail.visibility = View.VISIBLE
            }
        })
    }

}
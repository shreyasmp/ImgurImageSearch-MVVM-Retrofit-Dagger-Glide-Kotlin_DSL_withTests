package com.shreyas.imgurphotogallery.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyas.imgurphotogallery.R
import com.shreyas.imgurphotogallery.base.BaseFragment
import com.shreyas.imgurphotogallery.databinding.FragmentImageSearchListBinding
import com.shreyas.imgurphotogallery.model.Images
import com.shreyas.imgurphotogallery.utils.Constants.SELECTED_IMAGE
import com.shreyas.imgurphotogallery.view.adapter.ImagesRecyclerViewAdapter
import com.shreyas.imgurphotogallery.view.callback.ImageListItemClickListener
import com.shreyas.imgurphotogallery.viewmodel.ImageSearchViewModel

/**
 * Image Search Fragment that displays as result in recycler view list with
 * each item as card view item
 */
class ImageSearchListFragment : BaseFragment<ImageSearchViewModel>(), ImageListItemClickListener {

    companion object {
        val TAG: String = ImageSearchListFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentImageSearchListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_search_list, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        subscribeUI()

        return binding.root
    }

    override fun getTitle(): String = getString(R.string.app_name)

    private fun subscribeUI() {
        viewModel.imageSearchResponse.observe(viewLifecycleOwner, { imageListResponse ->
            binding.isLoading = false
            if (imageListResponse != null) {
                val retrievedImagesList = mutableListOf<Images>()
                val iterator = imageListResponse.data.listIterator()
                while (iterator.hasNext()) {
                    val imageDetail = iterator.next()
                    // Filter out NSFW content
                    if (imageDetail.images != null && imageDetail.nsfw != null && !imageDetail.nsfw) {
                        for (images in imageDetail.images) {
                            retrievedImagesList.add(images)
                        }
                    }
                }
                binding.imageSearchedList.adapter =
                    ImagesRecyclerViewAdapter(retrievedImagesList, this)
                binding.imageSearchedList.layoutManager = LinearLayoutManager(context)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        val searchViewItem = menu.findItem(R.id.action_search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = context?.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, "Entered Search Text: $query")
                // make service call on the searched string
                binding.isLoading = true
                viewModel.fetchSearchedImageList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onClick(image: Images) {
        val bundle = Bundle()
        bundle.putSerializable(SELECTED_IMAGE, image)
        findNavController().navigate(R.id.action_ImageListFragment_to_ImageDetailFragment, bundle)
    }
}
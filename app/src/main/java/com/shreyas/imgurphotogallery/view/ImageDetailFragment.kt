package com.shreyas.imgurphotogallery.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shreyas.imgurphotogallery.R
import com.shreyas.imgurphotogallery.base.BaseFragment
import com.shreyas.imgurphotogallery.databinding.FragmentImageDetailBinding
import com.shreyas.imgurphotogallery.model.Images
import com.shreyas.imgurphotogallery.utils.Constants.SELECTED_IMAGE
import com.shreyas.imgurphotogallery.viewmodel.ImageSearchViewModel

/**
 *  Image detail fragment,
 */
class ImageDetailFragment : BaseFragment<ImageSearchViewModel>() {

    companion object {
        val TAG = ImageDetailFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentImageDetailBinding

    private lateinit var image: Images

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail, container, false)

        binding.lifecycleOwner = this

        image = arguments?.getSerializable(SELECTED_IMAGE) as Images

        // Using glide api for loading and caching image faster for recyclerview
        Glide.with(this).load(image.link).into(binding.searchedImage)

        binding.searchedImageDescription.text = image.description

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //make status bar transparent
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
}
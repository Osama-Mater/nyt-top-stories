package com.mattar.nyt_top_stories.favourites

import com.mattar.nyt_top_stories.base.fragment.BaseFragment
import com.mattar.nyt_top_stories.databinding.FavouriteStoriesFragmentBinding
import com.mattar.nyt_top_stories.utils.viewBinding

class FavouriteStoriesFragment : BaseFragment() {
    private val binding by viewBinding(FavouriteStoriesFragmentBinding::bind)

    override fun onResume() {
        super.onResume()
        binding.underConstructionAnimation.playAnimation()
    }
}
package com.mattar.nyt_top_stories.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.fragment.BaseFragment

class FavouriteStoriesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourite_stories_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        underConstructionAnimation.playAnimation()
    }
}
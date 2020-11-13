package com.mattar.nyt_top_stories.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mattar.nyt_top_stories.R

class FavouriteStoriesFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteStoriesFragment()
    }

    private lateinit var viewModel: FavouriteStoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourite_stories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouriteStoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
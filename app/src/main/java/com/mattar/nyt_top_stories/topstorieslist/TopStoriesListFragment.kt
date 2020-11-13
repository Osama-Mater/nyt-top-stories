package com.mattar.nyt_top_stories.topstorieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mattar.nyt_top_stories.R

class TopStoriesListFragment : Fragment() {

    companion object {
        fun newInstance() = TopStoriesListFragment()
    }

    private lateinit var viewModel: TopStoriesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_stories_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopStoriesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
package com.mattar.nyt_top_stories.topstorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.extension.observe
import com.mattar.nyt_top_stories.base.fragment.BaseFragment
import com.mattar.nyt_top_stories.databinding.TopStoriesListFragmentBinding
import com.mattar.nyt_top_stories.topstorieslist.recyclerView.NytTopStoriesAdapter
import com.mattar.nyt_top_stories.utils.viewBinding

class TopStoriesListFragment : BaseFragment() {

    private val binding by viewBinding(TopStoriesListFragmentBinding::bind)

    private val viewModel by viewModels<TopStoriesListViewModel>()
    private val nytTopStoriesAdapter: NytTopStoriesAdapter = NytTopStoriesAdapter()

    private val stateObserver = Observer<TopStoriesListViewModel.ViewState> { viewState ->
        nytTopStoriesAdapter.topStories = viewState.topStories
        if (viewState.isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
        if (viewState.isError) {
            binding.errorAnimation.visibility = View.VISIBLE
        } else {
            binding.errorAnimation.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_stories_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = nytTopStoriesAdapter
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}
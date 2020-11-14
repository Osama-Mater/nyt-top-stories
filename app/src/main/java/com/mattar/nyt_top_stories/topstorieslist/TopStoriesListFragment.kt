package com.mattar.nyt_top_stories.topstorieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.extension.observe
import com.mattar.nyt_top_stories.base.fragment.BaseFragment
import com.mattar.nyt_top_stories.topstorieslist.recyclerView.NytTopStoriesAdapter
import kotlinx.android.synthetic.main.top_stories_list_fragment.*

class TopStoriesListFragment : BaseFragment() {

    private val viewModel by viewModels<TopStoriesListViewModel>()
    private val nytTopStoriesAdapter: NytTopStoriesAdapter = NytTopStoriesAdapter()

    private val stateObserver = Observer<TopStoriesListViewModel.ViewState> { viewState ->
        nytTopStoriesAdapter.topStories = viewState.topStories
        if (viewState.isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
        if (viewState.isError) {
            errorAnimation.visibility = View.VISIBLE
        } else {
            errorAnimation.visibility = View.GONE
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

        val context = requireContext()

        recyclerView.apply {
            setHasFixedSize(true)
            val columnWidth = context.resources.getDimension(R.dimen.image_size).toInt()
//            layoutManager =
//                GridAutofitLayoutManager(
//                    context,
//                    columnWidth
//                )
            adapter = nytTopStoriesAdapter
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}
package com.mattar.nyt_top_stories.storydetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mattar.nyt_top_stories.R

class StoryDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = StoryDetailsFragment()
    }

    private lateinit var viewModel: StoryDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.story_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoryDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
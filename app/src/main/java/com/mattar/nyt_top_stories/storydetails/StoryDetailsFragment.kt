package com.mattar.nyt_top_stories.storydetails

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.fragment.BaseFragment
import com.mattar.nyt_top_stories.databinding.StoryDetailsFragmentBinding
import com.mattar.nyt_top_stories.utils.dateString
import com.mattar.nyt_top_stories.utils.viewBinding
import kotlinx.coroutines.launch

class StoryDetailsFragment : BaseFragment() {
    private val binding by viewBinding(StoryDetailsFragmentBinding::bind)

    private val viewModel by viewModels<StoryDetailsViewModel>()
    private val args: StoryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.start(args.story)
        return inflater.inflate(R.layout.story_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect { viewState ->
                    with(binding) {
                        storyImage.load(viewState.story!!.multimedia?.first { image -> image.format == "Large Thumbnail" }?.url) {
                            crossfade(true)
                            error(R.drawable.ic_image)
                            fallback(R.drawable.ic_image)
                        }
                        storyTitle.text = viewState.story.title
                        storyPublicationDate.text = dateString(viewState.story.published_date)
                        storyAbstract.text = viewState.story.abstract
                        storyUrl.apply {
                            text = viewState.story.short_url
                            movementMethod = LinkMovementMethod.getInstance()
                        }
                    }
                }
            }
        }
        viewModel.loadData()
    }

}
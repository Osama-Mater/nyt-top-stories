package com.mattar.nyt_top_stories.storydetails

import android.os.Bundle
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.extension.observe
import com.mattar.nyt_top_stories.base.fragment.BaseFragment
import com.mattar.nyt_top_stories.databinding.StoryDetailsFragmentBinding
import com.mattar.nyt_top_stories.utils.viewBinding
import java.util.*

class StoryDetailsFragment : BaseFragment() {
    private val binding by viewBinding(StoryDetailsFragmentBinding::bind)

    private val viewModel by viewModels<StoryDetailsViewModel>()
    private val args: StoryDetailsFragmentArgs by navArgs()

    private val stateObserver = Observer<StoryDetailsViewModel.ViewState> { viewState ->
        with(binding) {
            storyImage.load(viewState.story!!.multimedia.filter { image -> image.format == "Large Thumbnail" }
                .first().url) {
                crossfade(true)
                error(R.drawable.ic_image)
                fallback(R.drawable.ic_image)
            }
            storyTitle.text = viewState.story.title
            storyPublicationDate.text = DateUtils.getRelativeTimeSpanString(
                viewState.story.published_date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.DAY_IN_MILLIS
            )
            storyAbstract.text = viewState.story.abstract
            storyUrl.apply {
                text = viewState.story.short_url
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.start(args.story)
        return inflater.inflate(R.layout.story_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }

}
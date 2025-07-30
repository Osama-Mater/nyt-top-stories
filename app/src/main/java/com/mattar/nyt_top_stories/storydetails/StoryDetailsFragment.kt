package com.mattar.nyt_top_stories.storydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.topstorieslist.TopStoriesListViewModel
import com.mattar.nyt_top_stories.utils.dateString

//class StoryDetailsFragment : BaseFragment() {
//    private val binding by viewBinding(StoryDetailsFragmentBinding::bind)
//
//    private val viewModel by viewModels<StoryDetailsViewModel>()
//    private val args: StoryDetailsFragmentArgs by navArgs()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        viewModel.start(args.story)
//        return inflater.inflate(R.layout.story_details_fragment, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.viewStateFlow.collect { viewState ->
//                    with(binding) {
//                        storyImage.load(viewState.story!!.multimedia?.first { image -> image.format == "Large Thumbnail" }?.url) {
//                            crossfade(true)
//                            error(R.drawable.ic_image)
//                            fallback(R.drawable.ic_image)
//                        }
//                        storyTitle.text = viewState.story.title
//                        storyPublicationDate.text = dateString(viewState.story.published_date)
//                        storyAbstract.text = viewState.story.abstract
//                        storyUrl.apply {
//                            text = viewState.story.short_url
//                            movementMethod = LinkMovementMethod.getInstance()
//                        }
//                    }
//                }
//            }
//        }
//        viewModel.loadData()
//    }
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailsScreen(
    modifier: Modifier = Modifier,
    articlesTopStoriesListViewModel: TopStoriesListViewModel,
    onBack: () -> Unit
) {
    val story =
        articlesTopStoriesListViewModel.selectedStory.collectAsStateWithLifecycle().value ?: return

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ), title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")

                    }
                }
            )
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.FillBounds,
                model = story.multimedia?.first { image -> image.format == "Large Thumbnail" }?.url,
                contentDescription = R.string.content_description_media.toString()
            )

            Text(
                text = story.title,
                style = MaterialTheme.typography.headlineSmall, // Approximates textAppearanceHeadline6
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.space_medium),
                    top = dimensionResource(id = R.dimen.space_medium),
                    end = dimensionResource(id = R.dimen.space_medium)
                )
            )

            Text(
                text = dateString(story.published_date),
                style = MaterialTheme.typography.bodyMedium, // Approximates textAppearanceBody2
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Approximates textColorSecondary
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.space_medium),
                    top = dimensionResource(id = R.dimen.space_medium),
                    end = dimensionResource(id = R.dimen.space_medium)
                )
            )

            Text(
                text = story.abstract,
                style = MaterialTheme.typography.bodyLarge, // Approximates textAppearanceBody1
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.space_medium),
                    top = dimensionResource(id = R.dimen.space_medium),
                    end = dimensionResource(id = R.dimen.space_medium)
                )
            )

            val uriHandler = LocalUriHandler.current

            ClickableText(
                text = buildAnnotatedString {
                    append(story.short_url)
                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary, // Typical link color
                            textDecoration = TextDecoration.Underline
                        ),
                        start = 0,
                        end = story.short_url.length
                    )
                    addStringAnnotation(
                        tag = "URL",
                        annotation = story.short_url,
                        start = 0,
                        end = story.short_url.length
                    )
                },
                style = MaterialTheme.typography.headlineSmall, // Approximates textAppearanceHeadline6
                // or consider bodyLarge for URLs if smaller text is desired
                modifier = Modifier.padding(dimensionResource(id = R.dimen.space_medium)),
                onClick = { offset ->
                    // Find the annotation at the clicked offset
                    val annotations =
                        buildAnnotatedString { append(story.short_url) }.getStringAnnotations(
                            "URL",
                            offset,
                            offset
                        )
                    annotations.firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item)
                    }
                }
            )
        }
    }
}
package com.mattar.nyt_top_stories.topstorieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.Multimedia
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.data.model.TopStories
import com.mattar.nyt_top_stories.data.source.NytRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


internal class TopStoriesListViewModelTest {

    @MockK
    internal lateinit var mockNytRepository: NytRepository

    private lateinit var cut: TopStoriesListViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        cut = TopStoriesListViewModel(mockNytRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute getTopStoriesUseCase`() {
        // when
        cut.loadData()

        // then
        coVerify { mockNytRepository.fetchNytTopStories() }
    }

    @Test
    fun `verify state when GetNytTopStoriesUseCase returns empty list`() {
        // given
        coEvery { mockNytRepository.fetchNytTopStories() } returns Result.Success(
            TopStories(
                "", "", 0,
                emptyList(), "", ""
            )
        )

        // when
        cut.loadData()

        // then
        assertEquals(
            TopStoriesListViewModel.ViewState(
                isLoading = false,
                isError = false,
                topStories = listOf()
            ), cut.viewStateFlow.value
        )
    }

    @Test
    fun `verify state when GetNytTopStoriesUseCase returns non-empty list`() {
        // given
        val story = Story(
            section = "us",
            subsection = "",
            title = "The Affidavit for the Search of Trump’s Home, Annotated",
            abstract = "Read the affidavit that outlined the Justice Department’s case for obtaining a warrant to search Mar-a-Lago, former President Donald J. Trump’s Florida home and private club, on Aug. 8.",
            url = "https://www.nytimes.com/interactive/2022/08/26/us/trump-search-affidavit-release.html",
            uri = "nyt://interactive/a472904c-81c5-5f33-ba4f-0cb9df4f5adf",
            byline = "By Charlie Savage",
            item_type = "Interactive",
            updated_date = "2022-08-27T09:49:39-04:00",
            created_date = "2022-08-26T16:17:00-04:00",
            published_date = Date(),
            material_type_facet = "",
            kicker = "",
            des_facet = listOf("Classified Information and State Secrets"),
            org_facet = listOf(
                "Mar-a-Lago (Palm Beach, Fla)",
                "Justice Department"
            ),
            per_facet = listOf("Trump, Donald J"),
            geo_facet = listOf(),
            multimedia = listOf(
                Multimedia(
                    url = "https://static01.nyt.com/images/2022/08/26/us/trump-search-affidavit-release-promo/trump-search-affidavit-release-promo-superJumbo.jpg",
                    format = "Super Jumbo",
                    height = 1333,
                    width = 2000,
                    type = "image",
                    subtype = "photo",
                    caption = "",
                    copyright = "Produced by Charlie Smart"

                ),
                Multimedia(
                    url = "https://static01.nyt.com/images/2022/08/26/us/trump-search-affidavit-release-promo/trump-search-affidavit-release-promo-threeByTwoSmallAt2X.jpg",
                    format = "threeByTwoSmallAt2X",
                    height = 400,
                    width = 600,
                    type = "image",
                    subtype = "photo",
                    caption = "",
                    copyright = "Produced by Charlie Smart"
                ),
                Multimedia(
                    url = "https://static01.nyt.com/images/2022/08/26/us/trump-search-affidavit-release-promo/trump-search-affidavit-release-promo-thumbLarge.jpg",
                    format = "Large Thumbnail",
                    height = 150,
                    width = 150,
                    type = "image",
                    subtype = "photo",
                    caption = "",
                    copyright = "Produced by Charlie Smart"
                )
            ),
            short_url = "https://nyti.ms/3TkcfY8"
        )
        val topStories = TopStories(
            "", "", 0,
            listOf(story), "", ""
        )
        coEvery { mockNytRepository.fetchNytTopStories() } returns Result.Success(topStories)

        // when
        cut.loadData()

        // then
        assertEquals(
            TopStoriesListViewModel.ViewState(
                isLoading = false,
                isError = false,
                topStories = topStories.stories
            ), cut.viewStateFlow.value
        )
    }
}
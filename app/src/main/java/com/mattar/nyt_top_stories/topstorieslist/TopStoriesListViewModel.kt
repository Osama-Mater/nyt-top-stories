package com.mattar.nyt_top_stories.topstorieslist

import androidx.lifecycle.viewModelScope
import com.mattar.nyt_top_stories.base.viewmodel.BaseAction
import com.mattar.nyt_top_stories.base.viewmodel.BaseViewModel
import com.mattar.nyt_top_stories.base.viewmodel.BaseViewState
import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.data.source.NytRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TopStoriesListViewModel @Inject constructor(private val nytRepository: NytRepository) :
    BaseViewModel<TopStoriesListViewModel.ViewState, TopStoriesListViewModel.Action>(ViewState()) {

    override fun onLoadData() {
        getTopStoriesList()
    }

    private fun getTopStoriesList() {
        viewModelScope.launch {
            nytRepository.fetchNytTopStories().also { result ->
                val action = when (result) {
                    is Result.Loading ->
                        Action.TopStoriesListLoading
                    is Result.Success ->
                        Action.TopStoriesListLoadingSuccess(result.data.stories)
                    is Result.Error ->
                        Action.TopStoriesListLoadingFailure
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.TopStoriesListLoading -> state.copy(
            isLoading = true,
            isError = false,
            topStories = listOf()
        )
        is Action.TopStoriesListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            topStories = viewAction.topStories
        )
        is Action.TopStoriesListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            topStories = listOf()
        )
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val topStories: List<Story> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class TopStoriesListLoadingSuccess(val topStories: List<Story>) : Action()
        object TopStoriesListLoadingFailure : Action()
        object TopStoriesListLoading : Action()
    }
}
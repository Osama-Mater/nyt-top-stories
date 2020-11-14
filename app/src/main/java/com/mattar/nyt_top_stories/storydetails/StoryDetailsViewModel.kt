package com.mattar.nyt_top_stories.storydetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.mattar.nyt_top_stories.base.viewmodel.BaseAction
import com.mattar.nyt_top_stories.base.viewmodel.BaseViewModel
import com.mattar.nyt_top_stories.base.viewmodel.BaseViewState
import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.Story

internal class StoryDetailsViewModel @ViewModelInject constructor() :
    BaseViewModel<StoryDetailsViewModel.ViewState, StoryDetailsViewModel.Action>(ViewState()) {

    private val _story = MutableLiveData<Story>()

    fun start(story: Story) {
        _story.value = story
    }

    override fun onLoadData() {
        val result = Result.Success(_story.value)
        val action = Action.StoryLoadingSuccess(result.data!!)

        sendAction(action)
    }

    override fun onReduceState(viewAction: Action): ViewState =
        when (viewAction) {
            is Action.StoryLoading -> state.copy(
                isLoading = true,
                isError = false,
                story = null
            )
            is Action.StoryLoadingSuccess -> state.copy(
                isLoading = false,
                isError = false,
                story = viewAction.story
            )
            is Action.StoryLoadingFailure -> state.copy(
                isLoading = false,
                isError = true,
                story = null
            )
        }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val story: Story? = null
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class StoryLoadingSuccess(val story: Story) : Action()
        object StoryLoadingFailure : Action()
        object StoryLoading : Action()
    }


}
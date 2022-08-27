package com.mattar.nyt_top_stories.topstorieslist.recyclerView

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.delegate.observer
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.databinding.StoryListItemBinding
import com.mattar.nyt_top_stories.topstorieslist.TopStoriesListFragmentDirections
import java.util.*

internal class NytTopStoriesAdapter : RecyclerView.Adapter<NytTopStoriesAdapter.MyViewHolder>() {

    var topStories: List<Story> by observer(listOf()) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            StoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(topStories[position])
        holder.binding.root.setOnClickListener {
            val action =
                TopStoriesListFragmentDirections.actionTopStoriesListFragmentToStoryDetailsFragment(
                    topStories[position]
                )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = topStories.size

    internal inner class MyViewHolder(val binding: StoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var url by observer<String?>(null) {
            binding.storyImage.load(it) {
                crossfade(true)
                error(R.drawable.ic_image)
                fallback(R.drawable.ic_image)
            }
        }

        fun bind(story: Story) {
            url = story.multimedia.filter { image -> image.format == "Large Thumbnail" }.first().url
            binding.storyTitle.text = story.title
            binding.storyPublicationDate.text = DateUtils.getRelativeTimeSpanString(
                story.published_date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.DAY_IN_MILLIS
            )
        }
    }
}

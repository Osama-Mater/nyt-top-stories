package com.mattar.nyt_top_stories.topstorieslist.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.base.delegate.observer
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.topstorieslist.TopStoriesListFragmentDirections
import kotlinx.android.synthetic.main.story_list_item.view.*

internal class NytTopStoriesAdapter : RecyclerView.Adapter<NytTopStoriesAdapter.MyViewHolder>() {

    var topStories: List<Story> by observer(listOf()) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(topStories[position])
        holder.itemView.setOnClickListener {
            val action =
                TopStoriesListFragmentDirections.actionTopStoriesListFragmentToStoryDetailsFragment(
                    topStories[position]
                )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = topStories.size

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var url by observer<String?>(null) {
            itemView.storyImage.load(it) {
                crossfade(true)
                error(R.drawable.ic_image)
                fallback(R.drawable.ic_image)
            }
        }

        fun bind(story: Story) {
            url = story.multimedia.filter { image -> image.format == "thumbLarge" }.first().url
            itemView.storyTitle.text = story.title
            itemView.storyPublicationDate.text = story.published_date

        }
    }
}

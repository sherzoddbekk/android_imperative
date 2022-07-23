package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.activity.MainActivity
import com.example.android_imperative.databinding.ItemTvShowBinding
import com.example.android_imperative.model.TvShow

class TvShowAdapter(var activity: MainActivity, var items: ArrayList<TvShow>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TvShow = items[position]
        if (holder is TVShowViewHolder) {
            Glide.with(activity).load(tvShow.image_thumbnail_path).into(holder.binding.ivMovie)
            holder.binding.tvName.text = tvShow.name
            holder.binding.tvType.text = tvShow.network


            ViewCompat.setTransitionName(holder.binding.ivMovie,tvShow.name)
            //click the image
            holder.binding.ivMovie.setOnClickListener {
                //Save TVShow into Room
                activity.viewModel.insertTvShowToDb(tvShow)

                //Call Details Activity

                activity.callDetailsActivity(tvShow,holder.binding.ivMovie)
            }
        }
    }

    fun setNewTVShows(tvShows:ArrayList<TvShow>){
       // items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val binding = ItemTvShowBinding.bind(view)
    }

}
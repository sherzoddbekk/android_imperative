package com.example.android_imperative.activity

import TvEpisodeAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_imperative.adapter.TVShortAdapter
import com.example.android_imperative.databinding.ActivityDetailsBinding
import com.example.android_imperative.model.Details
import com.example.android_imperative.model.Episode
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewModel.DetailsViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class DetailsActivity : BaseActivity() {
    lateinit var binding: ActivityDetailsBinding
    private val TAG = DetailsActivity::class.java.simpleName
    private val viewModel by viewModels<DetailsViewModel>()
    lateinit var tvShortAdapter :TVShortAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initObServes()
       // refreshAdapter()
        binding.rvShorts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.rvEpisode.layoutManager = gridLayoutManager
        refreshAdapterEpisode(ArrayList())
        Log.d("@@@","Main")
        val iv_detail = binding.ivDetail
        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras!!.getString("show_img")
        val show_name = extras!!.getString("show_name")
        val show_network = extras!!.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransition = extras.getString("iv_movie")
            iv_detail.transitionName = imageTransition
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(iv_detail)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun initObServes() {
        viewModel.tvShowDetails.observe(this) {
            Logger.d(TAG, it.toString())
            refreshAdapter(it.tvShow.pictures)
            refreshAdapterEpisode(it.tvShow.episodes)
            Log.d("@@@@@@", "initObServes: ")
            binding.tvDetails.text = it.tvShow.description

        }
        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, it.toString())
        }
        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(items:List<String>){
        val adapter=TVShortAdapter(this,items)
        binding.rvShorts.adapter=adapter
    }

    private fun refreshAdapterEpisode(item:List<Episode>){
        val adapter=TvEpisodeAdapter(this,item)
        binding.rvEpisode.adapter=adapter
        Log.d("@@@@@", "refreshAdapterEpisode: ")
    }

    private fun getAllSeasons(tvShow: Details){
        val map = TreeMap<String, ArrayList<Episode>>()

        Logger.d("@@@", tvShow.episodes.toString())
        for (i in tvShow.episodes){
            val items = map.getOrDefault("Season ${i.season.toString()}", ArrayList())
            items.add(i)
            map.put("Season ${i.season.toString()}", items)
        }

        for(k in map.keys){
            binding.tabSeason.addTab(binding.tabSeason.newTab().setText(k))
        }

        TvEpisodeAdapter
        TvEpisodeAdapter.submitList(map["Season ${tvShow.episodes[0].season}"])

        binding.tabSeason.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                episodesAdapter.submitList(map[tab?.text])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        Logger.d("@@@", "Map: ${map.size}")

    }
}
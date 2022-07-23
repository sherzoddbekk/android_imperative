package com.example.android_imperative.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.adapter.TvShowAdapter
import com.example.android_imperative.databinding.ActivityMainBinding
import com.example.android_imperative.model.TvShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = DetailsActivity::class.java.simpleName
    val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        initObServers()
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())

        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("@@@@", gridLayoutManager.findLastCompletelyVisibleItemPosition().toString())
                Log.d("@@@@", (viewModel.tvShowsFromApi.value!!.size - 1).toString())

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPage = viewModel.tvShowPopular.value!!.pages
                    Log.d("@@@@", "main")
                    if (nextPage <= totalPage) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                } else {
                    // Log.d("@@@@","ishlamadi")
                }
            }
        })

        binding.bFab.setOnClickListener {
            binding.recyclerViewHome.smoothScrollToPosition(0)
        }
        viewModel.apiTVShowPopular(1)
    }

    fun refreshAdapter(items: ArrayList<TvShow>) {
        adapter = TvShowAdapter(this, items)
        binding.recyclerViewHome.adapter = adapter
    }

    private fun initObServers() {
        /**
         * retrofit related
         */
        viewModel.tvShowsFromApi.observe(this, {
            Logger.d(TAG, it.size.toString())
            adapter.setNewTVShows(it)

        })
        viewModel.errorMessage.observe(this, {
            Logger.d(TAG, it.toString())
        })
        viewModel.isLoading.observe(this, {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    fun callDetailsActivity(tvShow: TvShow, sharedImageView: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, sharedImageView, ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }
}

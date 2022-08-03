package com.example.android_imperative.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.R
import com.example.android_imperative.databinding.ActivityMainBinding
import com.example.android_imperative.model.TvShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity() {
    private val TAG = "TAG" + this::class.simpleName.toString()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        val bnv = binding.bnvMain
        navController = findNavController(R.id.nav_host_fragment)
        bnv.setupWithNavController(navController)
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

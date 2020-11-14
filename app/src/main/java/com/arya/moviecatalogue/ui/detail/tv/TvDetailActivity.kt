package com.arya.moviecatalogue.ui.detail.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.BuildConfig.URL_POSTER
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.viewmodel.ViewModelFactory
import com.arya.moviecatalogue.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TvDetailViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Tv Show Detail"
        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TvDetailViewModel::class.java]

        val extras = intent
        val tvId = extras.getIntExtra("TvId", 0)

        viewModel.setSelectedTv(tvId)
        viewModel.tv.observe(this, { tv ->
            if (tv != null) {
                when (tv.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (tv.data != null){
                        progress_bar.visibility = View.GONE
                        populateTv(tv.data)
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.tv.observe(this, { tv ->
            if (tv != null) {
                when (tv.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (tv.data != null){
                        progress_bar.visibility = View.GONE
                        val state = tv.data.favorite
                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
        }
    }

    private fun populateTv(tvEntity: TvEntity) {
        text_title.text = tvEntity.name
        text_release.text = tvEntity.firstAirDate
        text_overview.text = tvEntity.overview

        Glide.with(this)
            .load(URL_POSTER + tvEntity.posterPath)
            .transform(CenterCrop())
            .into(image_poster)
    }
}
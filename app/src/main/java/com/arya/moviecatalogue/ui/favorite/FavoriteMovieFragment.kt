package com.arya.moviecatalogue.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.ui.detail.movie.MovieDetailActivity
import com.arya.moviecatalogue.ui.movie.MovieAdapter
import com.arya.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

class FavoriteMovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            val adapter = MovieAdapter { showMovieDetail(it) }

            viewModel.getFavoriteMovie().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    tv_no_favorite_movie?.visibility = View.GONE
                    adapter.submitList(movie)
                    adapter.notifyDataSetChanged()
                    Log.d("@Fav", "ADA")
                } else {
                    tv_no_favorite_movie?.visibility = View.VISIBLE
                }
            })

            rv_favorite_movie.setHasFixedSize(true)
            rv_favorite_movie.adapter = adapter
        }
    }

    private fun showMovieDetail(movieEntity: MovieEntity) {
        val intent = Intent(context, MovieDetailActivity::class.java).apply {
            putExtra("MovieId", movieEntity.id)
        }
        startActivity(intent)
    }
}
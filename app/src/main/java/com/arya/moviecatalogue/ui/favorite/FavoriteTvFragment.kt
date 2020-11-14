package com.arya.moviecatalogue.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.ui.detail.tv.TvDetailActivity
import com.arya.moviecatalogue.ui.tv.TvAdapter
import com.arya.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_tv.*

class FavoriteTvFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            val adapter = TvAdapter { showTvDetail(it) }

            viewModel.getFavoriteTv().observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    tv_no_favorite_tv?.visibility = View.GONE
                    adapter.submitList(tv)
                    adapter.notifyDataSetChanged()
                } else {
                    tv_no_favorite_tv?.visibility = View.VISIBLE
                }
            })

            rv_favorite_tv.setHasFixedSize(true)
            rv_favorite_tv.adapter = adapter
        }
    }

    private fun showTvDetail(tvEntity: TvEntity) {
        val intent = Intent(context, TvDetailActivity::class.java).apply {
            putExtra("TvId", tvEntity.id)
        }
        startActivity(intent)
    }
}
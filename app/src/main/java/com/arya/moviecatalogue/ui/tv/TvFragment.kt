package com.arya.moviecatalogue.ui.tv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arya.moviecatalogue.R
import com.arya.moviecatalogue.data.source.local.entity.TvEntity
import com.arya.moviecatalogue.ui.detail.tv.TvDetailActivity
import com.arya.moviecatalogue.viewmodel.ViewModelFactory
import com.arya.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.progress_bar

class TvFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]

            val tvAdapter = TvAdapter { showTvDetail(it) }
            rv_tv.adapter = tvAdapter

            showLoading(true)
            viewModel.getTv().observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            tvAdapter.submitList(tv.data)
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            img_error.visibility = View.VISIBLE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun showTvDetail(tvEntity: TvEntity) {
        val intent = Intent(context, TvDetailActivity::class.java).apply {
            putExtra("TvId", tvEntity.id)
        }
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }
}
package com.dicoding.github.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.databinding.ActivitySearchBinding
import com.dicoding.github.ui.adapter.ListUserAdapter
import com.dicoding.github.ui.viewmodels.MainViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.findUserGithub(searchView.text.toString())
                    false
                }
        }

        mainViewModel.followingData.observe(this){
            setFollowingData(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

    }

    private fun setFollowingData(listSearchResult: List<ItemsItem>) {
        val adapter = ListUserAdapter(listSearchResult)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                val intentDetail = Intent(this@SearchActivity, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_NAME, data)
                startActivity(intentDetail)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}



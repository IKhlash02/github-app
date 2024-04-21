package com.dicoding.github.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.R
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.databinding.ActivityMainBinding
import com.dicoding.github.ui.adapter.ListUserAdapter
import com.dicoding.github.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager


        mainViewModel.followingData.observe(this){
            setFollowingData(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menuSearch ->{
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuFavorite ->{
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuSetting ->{
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



    }



    private fun setFollowingData(listSearchResult: List<ItemsItem>) {
        val adapter = ListUserAdapter(listSearchResult)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_NAME, data)
                startActivity(intentDetail)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
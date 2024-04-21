package com.dicoding.github.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.github.R
import com.dicoding.github.data.local.entity.Favorite
import com.dicoding.github.data.remote.response.ResponseUser
import com.dicoding.github.databinding.ActivityDetailBinding
import com.dicoding.github.ui.adapter.SectionsPagerAdapter
import com.dicoding.github.ui.viewmodels.DetailViewModel
import com.dicoding.github.ui.viewmodels.FavoriteViewFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_NAME = "extra_name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private var favorite: Favorite? = null

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(EXTRA_NAME).toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userName
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs


        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel = obtainViewModel(this@DetailActivity)

        detailViewModel.showDetailUser(userName)

        detailViewModel.detailUser.observe(this){
            if (it != null) {
                setDetailData(it)
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.getFavoriteUserByUsername(userName).observe(this) { favoriteFromLiveData ->
            showFavorite(favoriteFromLiveData)
            favorite = favoriteFromLiveData
        }



    }


    private fun setDetailData(userDetail: ResponseUser) {

        binding.tvUsername.text = userDetail.login
        binding.tvName.text = userDetail.name ?: "Not Name"
        binding.tvFollowersCount.text = getString(R.string._500_followers, userDetail.followers)
        binding.tvFollowingCount.text = getString(R.string._500_followers, userDetail.following)

        Glide.with(this@DetailActivity)
            .load(userDetail.avatarUrl)
            .into(binding.ivAvatar)



        binding.floatingActionButton.setOnClickListener{
            if(favorite == null){
               favorite = Favorite(username = userDetail.login, avatarUrl = userDetail.avatarUrl)
                detailViewModel.insert(favorite as Favorite)
                showToast("Data saved successfully")
            } else {
                detailViewModel.delete(favorite as Favorite)

                showToast("Data Deleted Successfully")
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : DetailViewModel {
        val factory = FavoriteViewFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showFavorite(favorite: Favorite?){
        if (favorite != null) {
            binding.floatingActionButton.setImageResource(R.drawable.favorite_24)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_border_24)

        }
    }
}
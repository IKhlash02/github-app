package com.dicoding.github.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.data.remote.response.ItemsItem
import com.dicoding.github.databinding.FragmentFollowingBinding
import com.dicoding.github.ui.adapter.ListUserAdapter
import com.dicoding.github.ui.viewmodels.FollowingViewModel


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       var index: Int
       var username: String

        arguments.let {
            index = it?.getInt(ARG_SECTION_NUMBER) ?: 0
            username= it?.getString(ARG_USERNAME) ?: ""
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        val followingViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowingViewModel ::class.java]
        followingViewModel.findFollowing(index, username)

        if (index == 1){
            followingViewModel.followersData.observe(requireActivity()){
                if (it != null) {
                    setFollowingData(it)
                }
            }
        }
        else {
            followingViewModel.followingData.observe(requireActivity()){
                if (it != null) {
                    setFollowingData(it)
                }
            }
        }

        followingViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

    }


    private fun setFollowingData(listFollowing: List<ItemsItem>) {
        val adapter = ListUserAdapter(listFollowing)
        binding.rvFollow.adapter = adapter

        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                val intentDetail = Intent(requireActivity(), DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_NAME, data)
                startActivity(intentDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
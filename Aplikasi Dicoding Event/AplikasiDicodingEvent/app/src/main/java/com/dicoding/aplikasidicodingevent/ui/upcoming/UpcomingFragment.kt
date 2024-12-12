package com.dicoding.aplikasidicodingevent.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.databinding.FragmentUpcomingBinding
import com.dicoding.aplikasidicodingevent.response.EventResponse
import com.dicoding.aplikasidicodingevent.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import com.dicoding.aplikasidicodingevent.ui.EventAdapter
import com.dicoding.aplikasidicodingevent.ui.detail.DetailEventActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recycleApi.layoutManager = layoutManager

        fetchEventData()
    }

    private fun fetchEventData() {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        apiService.getEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setupRecyclerView(responseBody.listEvents)
                    }
                } else {
                    Log.e("UpcomingFragment", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showLoading(false)
                Log.e("UpcomingFragment", "onFailure: ${t.message}")
            }
        })
    }

    private fun setupRecyclerView(events: List<ListEventsItem>) {
        val adapter = EventAdapter(requireContext(), events) { eventId ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_EVENT_ID, eventId)
            startActivity(intent)
        }
        binding.recycleApi.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.dicoding.aplikasidicodingevent.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.databinding.FragmentHomeBinding
import com.dicoding.aplikasidicodingevent.response.EventResponse
import com.dicoding.aplikasidicodingevent.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import com.dicoding.aplikasidicodingevent.ui.EventAdapter
import com.dicoding.aplikasidicodingevent.ui.detail.DetailEventActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingEventAdapter: EventAdapter
    private lateinit var finishedEventAdapter: EventAdapter
    private lateinit var searchResultsAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupSearchView()

        fetchActiveEvents()
        fetchFinishedEvents()
    }

    private fun setupRecyclerViews() {
        upcomingEventAdapter = EventAdapter(requireContext(), listOf()) { eventId ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_EVENT_ID, eventId)
            startActivity(intent)
        }

        finishedEventAdapter = EventAdapter(requireContext(), listOf()) { eventId ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_EVENT_ID, eventId)
            startActivity(intent)
        }

        searchResultsAdapter = EventAdapter(requireContext(), listOf()) { eventId ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_EVENT_ID, eventId)
            startActivity(intent)
        }

        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingEventAdapter
        }

        binding.rvFinishedEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = finishedEventAdapter
        }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultsAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchEvents(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    restoreOriginalView()
                }
                return false
            }
        })
    }

    private fun searchEvents(keyword: String) {
        ApiConfig.getApiService().getSearchEvents(keyword).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: listOf()
                    displaySearchResults(events)
                } else {
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun displaySearchResults(events: List<ListEventsItem>) {
        binding.tvUpcomingLabel.visibility = View.GONE
        binding.tvFinishedLabel.visibility = View.GONE
        binding.rvUpcomingEvents.visibility = View.GONE
        binding.rvFinishedEvents.visibility = View.GONE

        searchResultsAdapter.updateEvents(events)
        binding.rvSearchResults.visibility = View.VISIBLE
    }

    private fun restoreOriginalView() {
        binding.tvUpcomingLabel.visibility = View.VISIBLE
        binding.tvFinishedLabel.visibility = View.VISIBLE
        binding.rvUpcomingEvents.visibility = View.VISIBLE
        binding.rvFinishedEvents.visibility = View.VISIBLE

        fetchActiveEvents()
        fetchFinishedEvents()

        binding.rvSearchResults.visibility = View.GONE
    }

    private fun fetchActiveEvents() {
        ApiConfig.getApiService().getEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: listOf()
                    upcomingEventAdapter.updateEvents(events.take(5))
                } else {
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun fetchFinishedEvents() {
        ApiConfig.getApiService().getFinishedEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: listOf()
                    finishedEventAdapter.updateEvents(events.take(5))
                } else {
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

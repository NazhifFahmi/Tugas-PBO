    package com.dicoding.aplikasidicodingevent.ui.finished

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.GridLayoutManager
    import com.dicoding.aplikasidicodingevent.databinding.FragmentFinishedBinding
    import com.dicoding.aplikasidicodingevent.response.EventResponse
    import com.dicoding.aplikasidicodingevent.response.ListEventsItem
    import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
    import com.dicoding.aplikasidicodingevent.ui.EventAdapter
    import com.dicoding.aplikasidicodingevent.ui.detail.DetailEventActivity
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class FinishedFragment : Fragment() {

        private var _binding: FragmentFinishedBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentFinishedBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val layoutManager = GridLayoutManager(context, 2)
            binding.recycleApi.layoutManager = layoutManager

            fetchEventData()
        }

        private fun fetchEventData() {
            showLoading(true)
            val apiService = ApiConfig.getApiService()
            apiService.getFinishedEvents().enqueue(object : Callback<EventResponse> {
                override fun onResponse(
                    call: Call<EventResponse>,
                    response: Response<EventResponse>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        response.body()?.listEvents?.let { events ->
                            setupRecyclerViewWithEvents(events)
                        } ?: run {
                            Log.e("FinishedFragment", "Response body is null")
                            showError("No events found")
                        }
                    } else {
                        Log.e("FinishedFragment", "Error: ${response.message()}")
                        showError("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    showLoading(false)
                    Log.e("FinishedFragment", "Failure: ${t.message}")
                    showError("Network Failure: ${t.message}")
                }
            })
        }

        private fun setupRecyclerViewWithEvents(events: List<ListEventsItem>) {
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

        private fun showError(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }


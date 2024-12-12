package com.dicoding.aplikasidicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.database.FavoriteEvent
import com.dicoding.aplikasidicodingevent.databinding.ActivityDetailEventBinding
import com.dicoding.aplikasidicodingevent.ui.favorite.FavoriteViewModel

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels() // Pastikan penggunaan viewModels() benar
    private val detailViewModel: DetailEventViewModel by viewModels()

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)
        if (eventId != -1) {
            detailViewModel.getDetailEvent(eventId)
            observeEvent()

            favoriteViewModel.getFavoriteById(eventId).observe(this) { favoriteEvent ->
                isFavorite = favoriteEvent != null
                updateFavoriteButtonIcon()
            }
        } else {
            finish()
        }
    }

    private fun observeEvent() {
        detailViewModel.event.observe(this) { eventData ->
            eventData?.let { event ->
                binding.apply {

                    Glide.with(this@DetailEventActivity)
                        .load(event.mediaCover)
                        .into(imgCover)

                    tvEventName.text = event.name
                    tvOwnerName.text = event.ownerName
                    tvEventTime.text = getString(R.string.event_time, event.beginTime, event.endTime)
                    val remainingQuota = event.quota?.minus(event.registrants ?: 0) ?: 0
                    tvQuota.text = getString(R.string.quota, remainingQuota)
                    tvDescription.text = HtmlCompat.fromHtml(event.description.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)

                    btnLink.setOnClickListener {
                        event.link?.let { url ->
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        }
                    }

                    btnFavorite.setOnClickListener {
                        val favoriteEvent = FavoriteEvent(
                            id = event.id.toString(),
                            name = event.name ?: "Unknown",
                            mediaCover = event.mediaCover
                        )
                        if (isFavorite) {
                            favoriteViewModel.removeFavorite(favoriteEvent)
                        } else {
                            favoriteViewModel.addFavorite(favoriteEvent)
                        }
                    }
                }
            }
        }
    }

    private fun updateFavoriteButtonIcon() {
        val icon = if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        binding.btnFavorite.setImageResource(icon)
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}

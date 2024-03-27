package com.practicum.playlistmaker.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.practicum.playlistmaker.databinding.FragmentFavoritesBinding
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.track.TrackAdapter
import com.practicum.playlistmaker.utils.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModel()
    private var binding: FragmentFavoritesBinding? = null

    private lateinit var onTrackClickDebounce: (Track) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        onTrackClickDebounce = debounce(CLICK_DEBOUNCE_DELAY, lifecycleScope, false) {
                track -> viewModel.play(track)
        }

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding == null) return

        viewModel.favoritesTrackList.observe(viewLifecycleOwner) { showTracks(it) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fillFavorites()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showTracks(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            binding?.mediaEmptyFavorites?.visibility = View.VISIBLE
            binding?.favoritesTracks?.visibility = View.GONE
        } else {
            binding?.favoritesTracks?.adapter = TrackAdapter(tracks) { track, _ ->
                onTrackClickDebounce(track)
            }
            binding?.favoritesTracks?.visibility = View.VISIBLE
            binding?.mediaEmptyFavorites?.visibility = View.GONE
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        fun newInstance() = FavoritesFragment().apply {  }
    }
}
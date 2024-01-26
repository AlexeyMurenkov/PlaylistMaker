package com.practicum.playlistmaker.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children
import com.practicum.playlistmaker.Creator
import com.practicum.playlistmaker.presentation.track.history.SearchHistory
import com.practicum.playlistmaker.data.network.ITunesSearchApi
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.presentation.track.TrackAdapter
import com.practicum.playlistmaker.presentation.track.history.HistoryTrackAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val searchService = retrofit.create(ITunesSearchApi::class.java)

    private val tracksInteractor = Creator.provideTracksInteractor()

    private lateinit var binding: ActivitySearchBinding
    private lateinit var history: SearchHistory

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { actionSearch() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        history = SearchHistory(getSharedPreferences(NAME_SEARCH_PREFERENCES, MODE_PRIVATE))

        with(binding) {
            setContentView(root)

            searchBack.setOnClickListener {
                finish()
            }

            initSearchEditText()

            searchText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    actionSearch()
                }
                false
            }

            searchRefresh.setOnClickListener {
                actionSearch()
            }

            searchHistory.adapter = HistoryTrackAdapter(
                history.history,
                {   track, position ->
                    if (position != 0) {
                        with(searchHistory) {
                            adapter?.notifyItemRemoved(position)
                            adapter?.notifyItemInserted(0)
                            adapter?.notifyItemRangeChanged(position, adapter!!.itemCount)
                            scrollToPosition(0)
                        }
                    }
                    play(track)
                },
                {
                    clearSearchHistory()
                }
            )

            hideChildren(searchResults)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SEARCH_TEXT, binding.searchText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredText = savedInstanceState.getString(KEY_SEARCH_TEXT)
        binding.searchText.setText(restoredText)
    }

    private fun actionSearch() {
        handler.removeCallbacks(searchRunnable)
        val searchText = binding.searchText.text.toString()
        hideChildren(binding.searchResults)
        if (searchText.isNotEmpty()) {
            binding.searchProgressBar.visibility = View.VISIBLE
            tracksInteractor.searchTracks(searchText) {
                runOnUiThread {
                    binding.searchProgressBar.visibility = View.GONE
                    if (it.err) {
                        binding.searchConnError.visibility = View.VISIBLE
                    } else if (it.values.isNotEmpty()) {
                        binding.searchTracks.adapter = TrackAdapter(it.values) { track, _ ->
                            play(track)
                        }
                        binding.searchTracks.visibility = View.VISIBLE
                    } else {
                        binding.searchNotFound.visibility = View.VISIBLE
                    }

                }
            }
        }
    }

    private fun play(track: Track) {
        history.add(track)
        startActivity(
            Intent(binding.root.context,  PlayerActivity::class.java)
                .putExtra(KEY_PLAYER_TRACK, track)
        )
    }

    private fun showSearchHistory() {
        with(binding) {
            searchHistory.adapter?.notifyDataSetChanged()
            searchHistoryGroup.visibility = View.VISIBLE
        }
    }

    private fun hideSearchHistory() {
        binding.searchHistoryGroup.visibility = View.GONE
    }

    private fun clearSearchHistory() {
        history.clear()
        hideSearchHistory()
    }

    private fun hideChildren(parent: ViewGroup) {
        parent.children.forEach { child -> child.visibility = View.GONE }
    }

    private fun initSearchEditText() {

        with(binding) {
            searchClear.setOnClickListener {
                searchText.setText("")
                hideChildren(binding.searchResults)
                hideKeyboard(searchText)
            }

            searchText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && searchText.text.isEmpty() && !history.isEmpty()) {
                    showSearchHistory()
                } else {
                    hideSearchHistory()
                }
            }

            searchText.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.isNullOrEmpty()) {
                            searchClear.visibility = View.INVISIBLE
                            if (!history.isEmpty()) showSearchHistory()
                        } else {
                            searchClear.visibility = View.VISIBLE
                            hideSearchHistory()
                            searchDebounce()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                }
            )
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchDebounce() {
        with(handler) {
            removeCallbacks(searchRunnable)
            postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
        }
    }

    companion object {
        private const val BASE_URL = "https://itunes.apple.com"
        private const val KEY_SEARCH_TEXT = "search_text"

        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        const val NAME_SEARCH_PREFERENCES = "settings_preferences"
        const val KEY_PLAYER_TRACK = "track"
    }
}

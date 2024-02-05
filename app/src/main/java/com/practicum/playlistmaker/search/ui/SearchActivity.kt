package com.practicum.playlistmaker.search.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.track.TrackAdapter
import com.practicum.playlistmaker.search.ui.track.history.HistoryTrackAdapter
import com.practicum.playlistmaker.search.domain.models.SearchScreenState

class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory(this)
    }

    private lateinit var binding: ActivitySearchBinding

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { search() }

    private var history = mutableListOf<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            searchBack.setOnClickListener {
                finish()
            }

            initSearchEditText()

            searchText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search()
                }
                false
            }

            searchRefresh.setOnClickListener {
                search()
            }

            searchHistory.adapter = HistoryTrackAdapter(
                history,
                { track, position ->
                    if (position != 0) {
                        with(searchHistory) {
                            adapter?.notifyItemRemoved(position)
                            adapter?.notifyItemInserted(0)
                            adapter?.notifyItemRangeChanged(position, adapter!!.itemCount)
                            scrollToPosition(0)
                        }
                    }
                    viewModel.play(track)
                },
                {
                    viewModel.clearHistory()
                }
            )

            hideChildren(searchResults)
        }

        viewModel.getPlayerScreenState().observe(this) {
            when (it) {
                is SearchScreenState.Process -> showProgressBar()
                is SearchScreenState.Error -> showError()
                is SearchScreenState.FoundTracks -> showTracks(it.tracksList)
                is SearchScreenState.History -> fillHistory(it.tracksList)
                else -> {}
            }
        }
    }

    private fun search() {
        handler.removeCallbacks(searchRunnable)
        hideChildren(binding.searchResults)
        val searchText = binding.searchText.text.toString()
        viewModel.search(searchText)
    }

    private fun showTracks(tracks: List<Track>) {
        hideProgressBar()
        if (tracks.isEmpty()) {
            binding.searchNotFound.visibility = View.VISIBLE
        } else {
            binding.searchTracks.adapter = TrackAdapter(tracks) { track, _ ->
                viewModel.play(track)
            }
            binding.searchTracks.visibility = View.VISIBLE
        }
    }

    private fun fillHistory(history: List<Track>) {
        with(this.history) {
            clear()
            addAll(history)
        }
        if (history.isEmpty()) hideSearchHistory()
    }

    private fun showError() {
        hideProgressBar()
        binding.searchConnError.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.searchProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.searchProgressBar.visibility = View.GONE
    }

    private fun showSearchHistory() {
        with(binding) {
            searchHistory.adapter?.notifyDataSetChanged()
            searchHistoryGroup.visibility = if (history.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun hideSearchHistory() {
        binding.searchHistoryGroup.visibility = View.GONE
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
                if (hasFocus && searchText.text.isEmpty() && history.isNotEmpty()) {
                    showSearchHistory()
                } else {
                    hideSearchHistory()
                }
            }

            searchText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    searchClear.visibility = View.INVISIBLE
                    showSearchHistory()
                } else {
                    searchClear.visibility = View.VISIBLE
                    hideSearchHistory()
                    searchDebounce()
                }
            }
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
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}

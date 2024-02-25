package com.practicum.playlistmaker.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.databinding.FragmentSearchBinding
import com.practicum.playlistmaker.search.domain.models.SearchScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.track.TrackAdapter
import com.practicum.playlistmaker.search.ui.track.history.HistoryTrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    private var binding: FragmentSearchBinding? = null
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { search() }

    private var history = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (binding == null) return
        with(binding!!) {

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

        viewModel.getPlayerScreenState().observe(viewLifecycleOwner) {
            when (it) {
                is SearchScreenState.Process -> showProgressBar()
                is SearchScreenState.Error -> showError()
                is SearchScreenState.FoundTracks -> showTracks(it.tracksList)
                is SearchScreenState.History -> fillHistory(it.tracksList)
                else -> {}
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun search() {
        handler.removeCallbacks(searchRunnable)
        binding?.let { hideChildren(it.searchResults) }
        val searchText = binding?.searchText?.text.toString()
        viewModel.search(searchText)
    }

    private fun showTracks(tracks: List<Track>) {
        hideProgressBar()
        if (tracks.isEmpty()) {
            binding?.searchNotFound?.visibility = View.VISIBLE
        } else {
            binding?.searchTracks?.adapter = TrackAdapter(tracks) { track, _ ->
                viewModel.play(track)
            }
            binding?.searchTracks?.visibility = View.VISIBLE
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
        binding?.searchConnError?.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding?.searchProgressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding?.searchProgressBar?.visibility = View.GONE
    }

    private fun showSearchHistory() {
        binding?.searchHistory?.adapter?.notifyDataSetChanged()
        binding?.searchHistoryGroup?.visibility = if (history.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun hideSearchHistory() {
        binding?.searchHistoryGroup?.visibility = View.GONE
    }

    private fun hideChildren(parent: ViewGroup) {
        parent.children.forEach { child -> child.visibility = View.GONE }
    }

    private fun initSearchEditText() {
        if (binding == null) return
        with(binding!!) {
            searchClear.setOnClickListener {
                searchText.setText("")
                hideChildren(binding!!.searchResults)
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
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchDebounce() {
        with(handler) {
            removeCallbacks(searchRunnable)
            postDelayed(searchRunnable,
                SEARCH_DEBOUNCE_DELAY
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
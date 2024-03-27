package com.practicum.playlistmaker.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.practicum.playlistmaker.databinding.FragmentSearchBinding
import com.practicum.playlistmaker.search.domain.models.SearchScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.track.TrackAdapter
import com.practicum.playlistmaker.search.ui.track.history.HistoryTrackAdapter
import com.practicum.playlistmaker.utils.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    private var binding: FragmentSearchBinding? = null

    private var history = mutableListOf<Track>()

    private lateinit var onTrackClickDebounce: (Track) -> Unit
    private lateinit var onSearchDebounce: (String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(viewLifecycleOwner) {
            onTrackClickDebounce = debounce(CLICK_DEBOUNCE_DELAY, lifecycleScope, false) {
                    track -> viewModel.play(track)
            }
            onSearchDebounce = debounce(SEARCH_DEBOUNCE_DELAY, lifecycleScope, true) {
                    expression -> viewModel.search(expression)
            }
        }

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
                    with(searchHistory) {
                        adapter?.notifyItemMoved(position, 0)
                        scrollToPosition(0)
                    }
                    onTrackClickDebounce(track)
                },
                {
                    viewModel.clearHistory()
                }
            )

            hideChildren(searchResults)
        }

        viewModel.playerScreenState.observe(viewLifecycleOwner) {
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
        binding?.let { hideChildren(it.searchResults) }
        val searchText = binding?.searchText?.text.toString()
        onSearchDebounce(searchText)
    }

    private fun showTracks(tracks: List<Track>) {
        hideProgressBar()
        if (tracks.isEmpty()) {
            binding?.searchNotFound?.visibility = View.VISIBLE
        } else {
            binding?.searchTracks?.adapter = TrackAdapter(tracks) { track, _ ->
                onTrackClickDebounce(track)
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
                    onSearchDebounce(text.toString())
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}

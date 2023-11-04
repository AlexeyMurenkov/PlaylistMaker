package com.practicum.playlistmaker.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children
import com.practicum.playlistmaker.track.history.SearchHistory
import com.practicum.playlistmaker.api.ITunesSearchApi
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.track.Track
import com.practicum.playlistmaker.track.TrackAdapter
import com.practicum.playlistmaker.track.TracksResponse
import com.practicum.playlistmaker.track.history.HistoryTrackAdapter
import com.practicum.playlistmaker.utils.KEY_PLAYER_TRACK
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val searchService = retrofit.create(ITunesSearchApi::class.java)

    private lateinit var binding: ActivitySearchBinding
    private lateinit var history: SearchHistory

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
        val searchText = binding.searchText.text.toString()
        hideChildren(binding.searchResults)
        if (searchText.isNotEmpty()) {
            searchService.search(searchText).enqueue(object : Callback<TracksResponse> {
                override fun onResponse(
                    call: Call<TracksResponse>,
                    response: Response<TracksResponse>
                ) {
                    if (response.code() == 200) {
                        val tracks = response.body()?.results!!
                        if (tracks.isNotEmpty()) {
                            binding.searchTracks.adapter = TrackAdapter(tracks) { track, _ ->
                                play(
                                    track
                                )
                            }
                            binding.searchTracks.visibility = View.VISIBLE
                        } else {
                            binding.searchNotFound.visibility = View.VISIBLE
                        }
                    } else {
                        binding.searchConnError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                    binding.searchConnError.visibility = View.VISIBLE
                }

            })
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

    companion object {
        private const val BASE_URL = "https://itunes.apple.com"
        private const val KEY_SEARCH_TEXT = "search_text"
        const val NAME_SEARCH_PREFERENCES = "settings_preferences"
    }
}

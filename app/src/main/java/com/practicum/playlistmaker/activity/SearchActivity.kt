package com.practicum.playlistmaker.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.children
import com.practicum.playlistmaker.api.ITunesSearchApi
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.track.TrackAdapter
import com.practicum.playlistmaker.track.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val searchService = retrofit.create(ITunesSearchApi::class.java)

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            searchBack.setOnClickListener {
                finish()
            }

            initSearchEditText(searchText, searchClear)

            searchText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    actionSearch()
                }
                false
            }

            searchRefresh.setOnClickListener {
                actionSearch()
            }

            hideChildren(searchResults)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, binding.searchText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredText = savedInstanceState.getString(SEARCH_TEXT)
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
                            binding.searchTracks.adapter = TrackAdapter(tracks)
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

    private fun hideChildren(parent: ViewGroup) {
        parent.children.forEach { child -> child.visibility = View.GONE }
    }

    private fun initSearchEditText(editText: EditText, clear: ImageView) {
        clear.setOnClickListener {
            editText.setText("")
            hideChildren(binding.searchResults)
            hideKeyboard(editText)
        }
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        editText.addTextChangedListener(searchTextWatcher)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}

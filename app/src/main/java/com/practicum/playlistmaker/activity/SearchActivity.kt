package com.practicum.playlistmaker.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.mock.ListTrack
import com.practicum.playlistmaker.track.TrackAdapter

class SearchActivity : AppCompatActivity() {

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

            searchTracks.adapter = TrackAdapter(ListTrack.tracks)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, binding.searchText.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredText = savedInstanceState.getString(SEARCH_TEXT)
        binding.searchText.setText(restoredText)
    }

    private fun initSearchEditText(editText: EditText, clear: ImageView) {
        clear.setOnClickListener {
            editText.setText("")
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
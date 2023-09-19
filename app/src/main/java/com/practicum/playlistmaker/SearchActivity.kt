package com.practicum.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val back = findViewById<TextView>(R.id.search_back)
        back.setOnClickListener {
            finish()
        }

        val searchText = findViewById<EditText>(R.id.search_text)
        val searchClear = findViewById<ImageView>(R.id.search_clear)
        searchClear.setOnClickListener {
            searchText.setText("")
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchClear.visibility = if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        searchText.addTextChangedListener(searchTextWatcher)

    }
}
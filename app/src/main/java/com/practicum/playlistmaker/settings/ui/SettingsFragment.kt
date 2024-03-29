package com.practicum.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModel()

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding == null) return
        with(binding!!) {
            settingsShareApp.setOnClickListener {
                viewModel.shareApp()
            }
            settingsAgreement.setOnClickListener {
                viewModel.showAgreement()
            }
            settingsSupport.setOnClickListener {
                viewModel.mailToSupport()
            }
            settingsDarkTheme.setOnCheckedChangeListener { _, checked ->
                viewModel.switchTheme(checked)
            }
        }

        viewModel.stateIsDarkTheme.observe(viewLifecycleOwner) {
            binding!!.settingsDarkTheme.isChecked = it
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

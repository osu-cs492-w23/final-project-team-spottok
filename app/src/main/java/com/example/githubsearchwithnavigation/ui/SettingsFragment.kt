package com.example.githubsearchwithnavigation.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.githubsearchwithnavigation.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        /*
         * Hook a custom summary provider up to the "language" setting.  This custom summery
         * provider displays "Not set" if no languages are selected, and otherwisem it uses a
         * "quantity string" to display an appropriate string to represent the number of languages
         * selected, e.g. "1 language" or "3 languages" (note the plural "languages" required when
         * multiple languages are selected).  For more on quantity strings in Android, see these
         * docs:
         *
         * https://developer.android.com/guide/topics/resources/string-resource#Plurals
         */
        /*val genrePref: MultiSelectListPreference? = findPreference(
            getString(R.string.pref_sort_key)
        )
        genrePref?.summaryProvider = Preference.SummaryProvider<MultiSelectListPreference> {
            val n = it.values.size
            if (n == 0) {
                getString(R.string.pref_sort_default)
            }
            //else {
                //resources.getQuantityString(R.plurals.pref_sort_key, n, n)
            //}
        }*/
    }
}
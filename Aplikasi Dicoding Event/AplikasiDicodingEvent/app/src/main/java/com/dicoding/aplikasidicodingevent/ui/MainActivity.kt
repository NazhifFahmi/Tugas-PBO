    package com.dicoding.aplikasidicodingevent.ui

    import android.os.Bundle
    import com.google.android.material.bottomnavigation.BottomNavigationView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.app.AppCompatDelegate
    import androidx.lifecycle.lifecycleScope
    import androidx.navigation.findNavController
    import androidx.navigation.ui.AppBarConfiguration
    import androidx.navigation.ui.setupActionBarWithNavController
    import androidx.navigation.ui.setupWithNavController
    import com.dicoding.aplikasidicodingevent.R
    import com.dicoding.aplikasidicodingevent.databinding.ActivityMainBinding
    import com.dicoding.aplikasidicodingevent.ui.setting.SettingPreferences
    import com.dicoding.aplikasidicodingevent.ui.setting.dataStore
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.launch

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            applySavedTheme()

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_upcoming, R.id.navigation_finished, R.id.navigation_favorite, R.id.navigation_setting
            ))
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }

        private fun applySavedTheme() {
            val pref = SettingPreferences.getInstance(dataStore)

            lifecycleScope.launch {
                val isDarkModeActive = pref.getThemeSetting().first()
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
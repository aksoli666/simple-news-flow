package com.example.simplenewsflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simplenewsflow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            _binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mBinding.root)
            mBinding.bottomNavMenu.setupWithNavController(
                navController = mBinding.navHostFragment.findNavController()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
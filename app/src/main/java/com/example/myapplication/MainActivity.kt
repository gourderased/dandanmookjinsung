package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding    // 바인딩 객체에 접근

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavHostFragment 찾고 NavController 가져옴
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_frag) as NavHostFragment
        val navController = navHostFragment.navController

        //바텀 네비게이션뷰와 NavController를 연결
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
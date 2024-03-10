package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding    // 바인딩 객체에 접근

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeNavigation.run {
            setOnItemSelectedListener {item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_calculator_and_result, HomeFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.navigation_calculator -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_calculator_and_result, CalculatorFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.navigation_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_calculator_and_result, MyPageFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                }

                true
            }
            selectedItemId = R.id.navigation_home
        }
    }
}
package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.TodoAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.service.dto.Todo

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val todos = listOf(
        Todo("리싸이클러뷰 부셔버리기 #1", false),
        Todo("리싸이클러뷰 부셔버리기 #2", false),
        Todo("리싸이클러뷰 부셔버리기 #3", false),
        Todo("리싸이클러뷰 부셔버리기 #4", false),
        Todo("리싸이클러뷰 부셔버리기 #5", false),
        Todo("리싸이클러뷰 부셔버리기 #6", false),
        Todo("리싸이클러뷰 부셔버리기 #7", false),
        Todo("리싸이클러뷰 부셔버리기 #8", false),
        Todo("리싸이클러뷰 부셔버리기 #9", false),
        Todo("리싸이클러뷰 부셔버리기 #10", false),
        Todo("리싸이클러뷰 부셔버리기 #11", false),
        Todo("리싸이클러뷰 부셔버리기 #12", false),
        Todo("리싸이클러뷰 부셔버리기 #13", false),
        Todo("리싸이클러뷰 부셔버리기 #14", false),
        Todo("리싸이클러뷰 부셔버리기 #15", false),

        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.todoList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.todoList.adapter = TodoAdapter(todos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
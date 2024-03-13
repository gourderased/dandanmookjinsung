package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Repository.RetrofitClient
import com.example.myapplication.adapters.TodoAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.service.dto.Todo
import retrofit2.Call

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //뷰가 생성된 후 호출, 초기화 작업을 수행
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchTodos()
    }

    //리싸이클러뷰 초기 설정 메소드
    private fun initViews() {
        binding.todoList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    //프래그먼트 파괴될 때 호출됨, 바인딩 인스턴스를 해제
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //API 요청, 응답을 처리
    private fun fetchTodos() {
        // RetrofitClient를 사용, API요청을 비동기적으로 수행, 결과는Callback으로 받음
        RetrofitClient.todoApiService.getTodos().enqueue(object : retrofit2.Callback<List<Todo>> {
            //요청 성공시
            override fun onResponse(
                call: retrofit2.Call<List<Todo>>,
                response: retrofit2.Response<List<Todo>>
            ) {

                //?.let을 통해 null을 받으면 핸들링
                if (response.isSuccessful) {
                    //body를 돌며 투두 객체를 하나씩 생성하며 false로 초기화 후 다시 리스트화
                    response.body()?.let { receivedTodos ->
                        displayTodos(receivedTodos.map {todo ->
                            Todo(todo.title, false)
                        })
                    }
                } else {
                    Log.e(TAG, "API 요청 실패: ${response.errorBody()}")
                }
            }

            //요청 실패시
            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Log.e(TAG, "API 요청 중 오류 발생: ${t.message}")
            }
        })
    }


    private fun displayTodos(todos: List<Todo>) {
        //어댑터에 새로운 데이터 설정
        binding.todoList.adapter = TodoAdapter(todos)
    }
}
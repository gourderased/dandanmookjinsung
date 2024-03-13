package com.example.myapplication.service

import com.example.myapplication.service.dto.Todo
import retrofit2.Call
import retrofit2.http.GET
interface TodoApiService {

    /**
     * [GET] 투두 목록 불러오기
     */
    @GET("/todos")
    fun getTodos(): Call<List<Todo>>
}
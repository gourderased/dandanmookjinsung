package com.example.myapplication.Repository

import com.example.myapplication.service.TodoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//코틀린에서 object는 싱글턴 패턴위해 사용
//api요청보내는 RetrofitClient 인스턴스를 앱내에서 하나만 생성하는 것
object RetrofitClient {
    private const val BASE_URL = "https://koreanjson.com/todos/"

    //lazy는 jpa지연로딩과 같음, 초기화 이후 접근때 저장된 값을 가져오는 것 -> 초기화시 비용 절감
    val todoApiService: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //HTTP응답을 파싱하기 위해 사용 JSON응답을 Java로 자동 변환해줌 -> 스프링에서의 잭슨
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculatorResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //어떤 뷰 사용?, 부모?, 자동으로 프래그먼트 추가해줄 것 인지?
        return inflater.inflate(R.layout.fragment_calculator_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //args에서 계산결과 가져옴
        //?는 자바, 스유에서 옵셔널, null이면 default값으로 슛 -> 안전 호출 연산자라고 함
        val result = arguments?.getString("calculationResult", "계산 결과 없음")

        view.findViewById<TextView>(R.id.tv_num_result).text = result
    }
}
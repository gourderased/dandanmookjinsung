package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //어떤 뷰 사용?, 부모?, 자동으로 프래그먼트 추가해줄 것 인지?
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view는 레이아웃에서 특정 뷰(버튼, 텍스트)를 찾을 때 사용하기 때문에 넘겨줘야함
        setupCalculateButton(view)
        setupDeleteAllButton(view)
        setUpNumberAndOperatorButtons(view)
    }

    //계산하기 버튼
    private fun setupCalculateButton(view: View) {
        val buttonCalculate = view.findViewById<Button>(R.id.btn_num_calculate)

        buttonCalculate.setOnClickListener {
            val inputField = view.findViewById<EditText>(R.id.tv_num_input)
            val expression = inputField.text.toString()
            val (result, errorMessage) = calculateExpression(expression)

            if(errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } else if (result != null) {
                val resultFragment = CalculatorResultFragment()
                //Bundle은 액티비티, 프래그먼트간 데이터 전달시 사용하는 자바에서의 map 키와 값쌍
                val args = Bundle()

                args.putString("calculationResult", result.toString())
                resultFragment.arguments = args

                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_calculator_and_result, resultFragment)
                    .addToBackStack(null).commit()
            }
        }
    }

    //클리어 버튼
    private fun setupDeleteAllButton(view: View) {
        view.findViewById<Button>(R.id.btn_num_delete_all).setOnClickListener {
            view.findViewById<EditText>(R.id.tv_num_input).text.clear()
        }
    }

    //숫자 버튼들
    private fun setUpNumberAndOperatorButtons(view: View) {
        val inputField = view.findViewById<EditText>(R.id.tv_num_input)
        val buttons = listOf(
            R.id.btn_num_zero,
            R.id.btn_num_one,
            R.id.btn_num_two,
            R.id.btn_num_three,
            R.id.btn_num_four,
            R.id.btn_num_five,
            R.id.btn_num_six,
            R.id.btn_num_seven,
            R.id.btn_num_eight,
            R.id.btn_num_nine,
            R.id.btn_num_add,
            R.id.btn_num_subtract
        )

        buttons.forEach { buttonId ->
            view.findViewById<Button>(buttonId).setOnClickListener { button ->
                //button은 view타입임, Button클래스 속성 사용하려면 캐스팅 필요
                val buttonText = (button as Button).text.toString()
                val currentText = inputField.text.toString()

                //연산자 앞뒤로 공백 추가
                if ((buttonText == "+" || buttonText == "-")) {
                    //마지막 문자가 숫자일때만 추가
                    if (currentText.isNotEmpty() && currentText.last().isDigit()) {
                        inputField.append(" $buttonText ")
                    }

                } else {
                    inputField.append(buttonText)
                }

            }
        }
    }

    //식 계산
    private fun calculateExpression(expression: String): Pair<Double?, String?> {
        //수식이 비어있는지 확인
        if(expression.isBlank()) {
            return Pair(null, "수식이 비어있습니다.")
        }

        //val은 불변, 자바에서의 final
        val tokens = expression.split(" ")
            .filter { it.isNotEmpty() }

        if(tokens.last().let { it == "+" || it == "-" }) {
            return Pair(null, "수식이 연산자로 끝납니다.")
        }

        var result = 0.0

        if (tokens.isNotEmpty()) result = tokens[0].toDouble()

        for (i in 1 until tokens.size step 2) {
            when (tokens[i]) {
                "+" -> result += tokens[i + 1].toDouble()
                "-" -> result -= tokens[i + 1].toDouble()
            }
        }

        return Pair(result, null)
    }
}
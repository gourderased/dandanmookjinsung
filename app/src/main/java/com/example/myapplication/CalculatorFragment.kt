package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    // 바인딩 객체 선언
    private lateinit var binding: FragmentCalculatorBinding    // 바인딩 객체에 접근

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        //어떤 뷰 사용?, 부모?, 자동으로 프래그먼트 추가해줄 것 인지?
        //return inflater.inflate(R.layout.fragment_calculator, container, false)

        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view는 레이아웃에서 특정 뷰(버튼, 텍스트)를 찾을 때 사용하기 때문에 넘겨줘야함
        setupCalculateButton()
        setupDeleteAllButton()
        setUpNumberAndOperatorButtons()
    }

    //계산하기 버튼
    private fun setupCalculateButton() {
        binding.btnNumCalculate.setOnClickListener {

            val expression = binding.tvNumInput.text.toString()
            val (result, errorMessage) = calculateExpression(expression)

            errorMessage?.let{
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } ?: run {
                val resultFragment = CalculatorResultFragment().apply {
                    arguments = Bundle().apply {
                        putString("calculationResult", result.toString())
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_calculator_and_result, resultFragment)
                    .addToBackStack(null).commit()
            }

        }
    }

    //클리어 버튼
    private fun setupDeleteAllButton() {
        binding.btnNumDeleteAll.setOnClickListener {
            binding.tvNumInput.text.clear()
        }
    }

    //숫자 버튼들
    private fun setUpNumberAndOperatorButtons() {

        val numberButtons = listOf(
            binding.btnNumZero,
            binding.btnNumOne,
            binding.btnNumTwo,
            binding.btnNumThree,
            binding.btnNumFour,
            binding.btnNumFive,
            binding.btnNumSix,
            binding.btnNumSeven,
            binding.btnNumEight,
            binding.btnNumNine
        )

        val operatorButtons = listOf(
            binding.btnNumAdd,
            binding.btnNumSubtract
        )

        numberButtons.forEach{ button ->
            button.setOnClickListener {
                binding.tvNumInput.append(button.text)
            }
        }

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                val currentText = binding.tvNumInput.text.toString()
                // 마지막 문자가 숫자일 때만 연산자 추가
                if (currentText.isNotEmpty() && currentText.last().isDigit()) {
                    binding.tvNumInput.append(" ${button.text} ")
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
package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTodoBinding
import com.example.myapplication.service.dto.Todo

//투두 리스트를 받아 RecyclerView의 아이템으로 표시하는 어댑터 쳐돌이
class TodoAdapter(private val todos: List<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    //로그의 태그를 정의함, 알아보기 쉽게!! 범위는 클래스까지
    companion object {
        private const val TAG = "TodoAdapter";
    }

    //ViewHolder 생성하는 함수, 최소 생성 횟수만큼만 호출됨 (계속 호출 X)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        Log.d(TAG, "onCreateViewHolder: 새로운 뷰홀더 생성")

        //ItemTodoBinding을 통해 아이템 뷰를 인플레이트함 -> 뷰와 데이터를 바인딩
        //from은 layoutInflater를 넘기기위해 사용, ViewGroup은 view를 상속, context도 갖고있음
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // 생성된 뷰 홀더를 반환
        /**
         * also : viewHolder 객체에 대한 추가 작업
         * _ : 매개변수를 무시함 첫번째 매개변수는 무시하고, isChecked만 쓰게따!!
         */
        return TodoViewHolder(binding).also { holder ->
            binding.cbTodo.setOnCheckedChangeListener { _, isChecked ->
                //체크박스 상태 변경될 때 해당 투두 아이템의 완료 상태를 업데이트
                todos.getOrNull(holder.adapterPosition)?.completed = isChecked
            }
        }
    }

    // 만들어진 ViewHolder에 데이터를 바인딩하는 함수
    // position = 리스트 상에서 몇번째인지 의미
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        //몇 번째 아이템이 바인딩되는지 기록
        Log.d(TAG, "onBindViewHolder: $position")
        //현재 위치의 투두 아이템을 가져와서 뷰 홀더에 바인딩
        holder.bind(todos[position])
    }

    // 전체 아이템의 수를 반환, 리싸이클러뷰에 표시
    override fun getItemCount(): Int = todos.size

    //투두 뷰홀더 클래스
    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

            //투두 아이템들 받아와서 데이터 설정
        fun bind(todo: Todo) {
            binding.tvTodoTitle.text = todo.title;
            binding.cbTodo.isChecked = todo.completed;
        }
    }
}
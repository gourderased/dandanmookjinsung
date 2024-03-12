package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTodoBinding
import com.example.myapplication.service.dto.Todo

class TodoAdapter(private val todos: List<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    companion object {
        private const val TAG = "TodoAdapter_고기";
    }

    //ViewHolder 생성하는 함수, 최소 생성 횟수만큼만 호출됨 (계속 호출 X)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {

        //from은 layoutInflater를 넘기기위해 사용, ViewGroup은 view를 상속, context도 갖고있음
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding).also { holder ->
            binding.cbTodo.setOnCheckedChangeListener { _, isChecked ->
                todos.getOrNull(holder.adapterPosition)?.completed = isChecked
            }
        }
    }

    // 만들어진 ViewHolder에 데이터를 바인딩하는 함수
    // position = 리스트 상에서 몇번째인지 의미
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size

    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.tvTodoTitle.text = todo.title;
            binding.cbTodo.isChecked = todo.completed;
        }
    }
}
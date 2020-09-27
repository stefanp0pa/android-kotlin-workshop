package com.example.todo_app_kotlin

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var itemlist: ArrayList<String> = arrayListOf<String>()
    private var todos: ArrayList<Todo> = arrayListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null && savedInstanceState.containsKey("todos")) {
            todos = savedInstanceState.getSerializable("todos") as ArrayList<Todo>
            for (todo in todos) {
                itemlist.add(todo.getContent())
            }
            Toast.makeText(this, "ItemList has: " + itemlist.size, Toast.LENGTH_SHORT).show()
        }

        var adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, itemlist)

        listView.adapter = adapter
        var index = itemlist.size - 1
        while (index >= 0) {
            if (todos[index].isChecked())
                listView.setItemChecked(index, true)
            index -= 1
        }

        addButton.setOnClickListener {
            var todoContent = todoEditText.text.toString().trim()
            if (todoContent.isNullOrEmpty() || todoContent.isNullOrBlank()) {
                Toast.makeText(this, "You need to pass a valid TODO content", Toast.LENGTH_SHORT).show()
            } else {
                itemlist.add(todoContent)
                todos.add(Todo(todoContent, false))

                Toast.makeText(this, "Succesfully added", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }

            todoEditText.text.clear()
        }

        clearButton.setOnClickListener {
            itemlist.clear()
            todos.clear()
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            todos[i].setCheked(!todos[i].isChecked())
            val status: String = if (todos[i].isChecked())  "CHECK" else "UNCHECK"
            Toast.makeText(this, "You marked the item: " + itemlist.get(i) + " with " + status,
                Toast.LENGTH_SHORT).show()
        }

        deleteButton.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = adapter.count
            var itemPosition = count - 1
            while (itemPosition >= 0) {
                if (position.get(itemPosition)) {
                    itemlist.removeAt(itemPosition)
                    todos.removeAt(itemPosition)
                }
                itemPosition -= 1;
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("todos", todos)
        super.onSaveInstanceState(outState)
    }
}
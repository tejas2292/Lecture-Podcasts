package com.example.lecturepodcasts

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ListActivity : AppCompatActivity() {
    var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Discrete Mathematics Lecture 1",
            "Discrete Mathematics Lecture 2",
            "Theory of Computation Lecture 1",
            "Theory of Computation Lecture 2",
            "Theory of Computation Lecture 3"
        )

        createDialog()


        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, users
        )
        mListView.adapter = arrayAdapter

        mListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("position", position.toString())
                startActivity(intent)
            }
    }

    fun createDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit App")
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            finish()
        }
        alertDialogBuilder.setNegativeButton(
            "Cancel",
            { dialogInterface: DialogInterface, i: Int -> })

        alertDialog = alertDialogBuilder.create()
    }

    override fun onBackPressed() {
        alertDialog?.show()
    }
}
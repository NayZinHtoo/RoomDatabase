package com.example.roomdatabase

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import com.example.roomdatabase.database.AppDatabase
import com.example.roomdatabase.entity.BookEntity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var txtId=findViewById<EditText>(R.id.txt_id)
        var txtName=findViewById<EditText>(R.id.txt_name)

        var txtResult=findViewById<TextView>(R.id.txt_result)

        var db=AppDatabase.getDatabase(this).bookDao()

        /*Thread{
            //Insert case
            var bookEntity=BookEntity()
            bookEntity.bookId=1
            bookEntity.bookName="Kotlin for Android Developer"
            db.saveBooks(bookEntity)

            //fetch Records
            db.getAllBooks().forEach(){
                Log.i("Fetch Records","Id:${it.bookId}")
                Log.i("Fetch Records","Id:${it.bookName}")
            }
        }.start()*/

        btn_insert.setOnClickListener {
            var bookEntity=BookEntity()
            bookEntity.bookId=txtId.text.toString().toInt()
            bookEntity.bookName=txtName.text.toString()
            Thread{
                db.saveBooks(bookEntity)
            }.start()
        }
        btn_select.setOnClickListener {
            var id=1;
            var name="Android Developer"
            var bookList:List<BookEntity>?=null
            Thread{
                bookList=db.getAllBooks()
                bookList!!.forEach(){
                    id=it.bookId
                    name=it.bookName
                    Log.i("Fetch Records","Id:${it.bookId}")
                    Log.i("Fetch Records","Id:${it.bookName}")
                }
                this.runOnUiThread(Runnable {
                    txtResult.text="Result :\n\tBookCount = "+bookList!!.size+"\nLast Data :\n\tId = "+id+"\n\tName = "+name
                })
            }.start()
        }
    }
}

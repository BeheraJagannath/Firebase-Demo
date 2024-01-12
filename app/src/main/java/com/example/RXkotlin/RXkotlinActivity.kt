package com.example.RXkotlin

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.loginfirebasedemo.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Text

data class Person(
    val name:String = "",
    val age:Int=0
)

class RXkotlinActivity : AppCompatActivity() {
    private var backgroundThread: BackgroundThread? = null
    var ratingbar: RatingBar? = null
    var button: Button? = null
    lateinit var textview:TextView

    var listView: ListView? = null
    var contacts = arrayOf("Ajay", "Sachin", "Sumit", "Tarun", "Yogesh")

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxkotlin)
        listView=findViewById(R.id.listview)
        textview =findViewById(R.id.textview)
        FirebaseCoroutines()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        listView!!.setAdapter(adapter)
        registerForContextMenu(listView)


        ratingbar=findViewById(R.id.ratingBar);
        button=findViewById(R.id.button)
        button!! .setOnClickListener{
            val rating = ratingbar!!.getRating().toString()
            Toast.makeText(applicationContext, rating, Toast.LENGTH_LONG).show()

        }

        Log.d(TAG, "Thread is killed ?")
        backgroundThread = BackgroundThread()
        backgroundThread!!.start()

    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        menu.setHeaderTitle("Select The Action")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.call) {
            Toast.makeText(applicationContext, "calling code", Toast.LENGTH_LONG).show()
        } else if (item.getItemId() === R.id.sms) {
            Toast.makeText(applicationContext, "sending sms code", Toast.LENGTH_LONG).show()
        } else {
            return false
        }
        return true
    }

    private fun FirebaseCoroutines() {

        val tutorialdocument = Firebase.firestore.collection("coroutines").document("tutorial")
        val peter = Person("peter",25)
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000)
            tutorialdocument.set(peter).await()
            val person = tutorialdocument.get().await().toObject(Person::class.java)
            withContext(Dispatchers.Main){
                textview.text = person.toString()

            }

        }

    }




}
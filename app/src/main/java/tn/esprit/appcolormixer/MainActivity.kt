package tn.esprit.appcolormixer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var et_name : EditText
    private lateinit var cb_blue : CheckBox
    private lateinit var cb_red : CheckBox
    private lateinit var cb_yellow : CheckBox
    private lateinit var btn_mix : Button
    private var colorList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.color_mixer_title)

        et_name=findViewById(R.id.et_name)
        cb_blue=findViewById(R.id.cb_blue)
        cb_red=findViewById(R.id.cb_red)
        cb_yellow=findViewById(R.id.cb_yellow)
        btn_mix=findViewById(R.id.btn_mix)


        btn_mix.setOnClickListener {
            if(validateInput()){
                saveChoice()
                var  intent=Intent(this, AnswerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveChoice() {
        val sharedPreference =  getSharedPreferences("COLOR_CHOICE",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("color1", colorList[0])
        editor.putString("color2", colorList[1])
        editor.apply()
    }

    private fun validateInput(): Boolean {
        var checked =0
        if(cb_blue.isChecked) {
            checked++
            colorList.add("BLUE")
        }
        if (cb_red.isChecked) {
            checked++
            colorList.add("RED")
        }
        if(cb_yellow.isChecked){
            checked++
            colorList.add("YELLOW")
        }
        Log.d("CHECKED!!!!!", checked.toString())
        if(et_name.text.isEmpty()){
            Toast. makeText(this,getString(R.string.enter_name),Toast. LENGTH_SHORT).show()
            return false
        }else if(checked!=2){
            Toast. makeText(this,getString(R.string.select_two),Toast. LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
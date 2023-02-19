package tn.esprit.appcolormixer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {
    private lateinit var tv_choice : TextView
    private lateinit var rb_purple : RadioButton
    private lateinit var rb_green : RadioButton
    private lateinit var rb_orange : RadioButton
    private lateinit var rg_colors : RadioGroup
    private lateinit var btn_submit : Button
    private var colorList = arrayListOf<String>()
    private var resColor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.choose_answer_title)

        tv_choice=findViewById(R.id.tv_choice)
        rb_purple=findViewById(R.id.rb_purple)
        rb_green=findViewById(R.id.rb_green)
        rb_orange=findViewById(R.id.rb_orange)
        rg_colors=findViewById(R.id.rg_colors)
        btn_submit=findViewById(R.id.btn_submit)

        getChoice()
        getResColor()

        btn_submit.setOnClickListener {
            var intent=Intent(this, ResultAvtivity::class.java)
            intent.putExtra("result", verifyRes())
            startActivity(intent)
            finish()
        }
    }

    private fun verifyRes(): Boolean {
        if(resColor=="PURPLE" && rb_purple.isChecked || resColor=="ORANGE" && rb_orange.isChecked || resColor=="GREEN" && rb_green.isChecked){
            return true
        }
        return false
    }

    private fun getResColor() {
        if(colorList.contains("BLUE") && colorList.contains("RED")){
            resColor="PURPLE"
        }else if(colorList.contains("BLUE") && colorList.contains("YELLOW")){
            resColor="GREEN"
        }else if(colorList.contains("RED") && colorList.contains("YELLOW")){
            resColor="ORANGE"
        }
    }

    private fun getChoice() {
        val sharedPreference =  getSharedPreferences("COLOR_CHOICE",Context.MODE_PRIVATE)
        var color1=sharedPreference.getString("color1","")
        var color2=sharedPreference.getString("color2","")
        if (color1 != null) {
            colorList.add(color1)
        }
        if (color2 != null) {
            colorList.add(color2)
        }
        tv_choice.text=getString(R.string.you_chose_color_and_color,color1,color2)
    }
}
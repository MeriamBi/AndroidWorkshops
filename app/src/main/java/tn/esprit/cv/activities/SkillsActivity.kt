package tn.esprit.cv.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.cv.R

class SkillsActivity : AppCompatActivity() {
    private lateinit var androidSb : SeekBar; private lateinit var iosSb : SeekBar; private lateinit var flutterSb : SeekBar
    private lateinit var arCb : CheckBox; private lateinit var frCb : CheckBox; private lateinit var enCb : CheckBox
    private lateinit var musicCb : CheckBox; private lateinit var sportCb : CheckBox; private lateinit var gamesCb : CheckBox
    private lateinit var submitBtn : Button
    private lateinit var fullname:String;private lateinit var email:String;private lateinit var age:String;
    private lateinit var gender:String;private lateinit var pic:String;
    private lateinit var rmCb : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skills)
        androidSb=findViewById(R.id.sb_android); iosSb=findViewById(R.id.sb_ios); flutterSb=findViewById(
            R.id.sb_flutter
        )
        arCb=findViewById(R.id.cb_ar); frCb=findViewById(R.id.cb_fr); enCb=findViewById(R.id.cb_en)
        musicCb=findViewById(R.id.cb_music); sportCb=findViewById(R.id.cb_sport); gamesCb=findViewById(
            R.id.cb_games
        )
        submitBtn=findViewById(R.id.btn_submit)
        rmCb=findViewById(R.id.cb_rm)


        val actionBar = supportActionBar
        //actionBar!!.title = getString(R.string.create_resume_title)

        fullname = intent.getStringExtra("fullname").toString()
        email = intent.getStringExtra("email").toString()
        age = intent.getStringExtra("age").toString()
        gender = intent.getStringExtra("gender").toString()
        pic = intent.getStringExtra("pic").toString()

        //ON CLICK SUBMIT
        submitBtn.setOnClickListener{
            saveData()
            var  intent= Intent(this, ResumeActivityV2::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun saveGeneralInfo() {
        val sharedPreference =  getSharedPreferences("INFO", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("fullname", fullname)
        editor.putString("email", email)
        editor.putString("age", age)
        editor.putString("gender", gender)
        editor.putString("pic", pic)
        Log.d("TEST", rmCb.isChecked.toString())
        editor.putBoolean("rm", rmCb.isChecked)
        editor.apply()
    }

    private fun saveData() {
        saveGeneralInfo()
        var languages=""
        var hobbies=""
        val sharedPreference =  getSharedPreferences("INFO", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        if (arCb.isChecked)
            languages+=getString(R.string.arabic)+" "
        if (frCb.isChecked)
            languages+=getString(R.string.french)+" "
        if (enCb.isChecked)
            languages+=getString(R.string.english)+" "
        if (musicCb.isChecked)
            hobbies+=getString(R.string.music)+" "
        if (sportCb.isChecked)
            hobbies+=getString(R.string.sport)+" "
        if (gamesCb.isChecked)
            hobbies+=getString(R.string.games)+" "

        editor.putString("languages", languages)
        editor.putString("hobbies", hobbies)
        editor.putString("android", androidSb.progress.toFloat().toString())
        editor.putString("ios", iosSb.progress.toFloat().toString())
        editor.putString("flutter", flutterSb.progress.toFloat().toString())
        editor.apply()
    }
}
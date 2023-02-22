package tn.esprit.leagueoflegends

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ChampionInfoActivity : AppCompatActivity() {
    private lateinit var nameTv : TextView; private lateinit var roleTv : TextView; private lateinit var picIv : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_champion_info)
        nameTv=findViewById(R.id.tv_name);roleTv=findViewById(R.id.tv_role);picIv=findViewById(R.id.iv_pic)

        var pic=intent.getIntExtra("pic",R.drawable.ic_leesin)
        var name=intent.getStringExtra("name")
        var role=intent.getStringExtra("role")

        name?.let { actionBarConfig(it) }

        //get data from intent here
        picIv.setImageResource(pic)
        nameTv.text = getString(R.string.name,name)
        roleTv.text = getString(R.string.role,role)
    }

    private fun actionBarConfig(name:String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.detail_title,name);
        toolbar.setTitleTextColor(Color.WHITE)
    }
}
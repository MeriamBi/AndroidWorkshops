package tn.esprit.leagueoflegends

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import tn.esprit.leagueoflegends.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainView: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainView.root)
        actionBarConfig()

        replaceFragment(ChampionsFragment())

        mainView.btnChampions.setOnClickListener{
            replaceFragment(ChampionsFragment())
        }

        mainView.btnSpells.setOnClickListener{
            replaceFragment(SpellsFragment())
        }
    }

    private fun actionBarConfig() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        toolbar.setTitleTextColor(Color.WHITE)
    }

    fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.info_item -> {
                replaceFragment(DetailsFragment())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
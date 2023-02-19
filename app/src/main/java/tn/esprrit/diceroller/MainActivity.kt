package tn.esprrit.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollBtn:Button=findViewById(R.id.button)
        //val rollBtn=findViewById<Button>(R.id.button)
        rollBtn.setOnClickListener{
            rollDice()
        }
    }
    private fun rollDice(){
        val dice=Dice(6)
        val diceRoll=dice.roll()
        val diceImg: ImageView = findViewById(R.id.imageView)
        val drawableRes=when (diceRoll){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImg.setImageResource(drawableRes)
    }
}
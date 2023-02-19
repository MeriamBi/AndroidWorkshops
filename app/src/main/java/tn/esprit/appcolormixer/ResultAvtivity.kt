package tn.esprit.appcolormixer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ResultAvtivity : AppCompatActivity() {
    private lateinit var iv_result : ImageView
    private lateinit var tv_result : TextView
    private lateinit var tv_androidRes : TextView
    private lateinit var tv_answerJudge : TextView
    private lateinit var btn_quit : Button
    private lateinit var lyt_top : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_avtivity)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.success_res)

        iv_result=findViewById(R.id.iv_result)
        tv_result=findViewById(R.id.tv_result)
        tv_androidRes=findViewById(R.id.tv_androidRes)
        tv_answerJudge=findViewById(R.id.tv_answerJudge)
        btn_quit=findViewById(R.id.btn_quit)
        lyt_top=findViewById(R.id.lyt_top)

        val result:Boolean = intent.getBooleanExtra("result",false)
        if(!result){
            actionBar.title = getString(R.string.failure_res)
            setFailureResult()
        }
        btn_quit.setOnClickListener {
            finish()
        }
    }

    private fun setFailureResult() {
        lyt_top.setBackgroundColor(resources.getColor(R.color.red))
        iv_result.setImageResource(R.drawable.ic_failure)
        tv_androidRes.text=getString(R.string.sorry_android)
        tv_answerJudge.text=getString(R.string.your_answer_is_wrong)
        btn_quit.setBackgroundColor(resources.getColor(R.color.red))
    }
}
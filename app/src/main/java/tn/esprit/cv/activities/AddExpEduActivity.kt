package tn.esprit.cv.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import tn.esprit.cv.R
import tn.esprit.cv.data.Company
import tn.esprit.cv.data.CompanyType
import tn.esprit.cv.utils.AppDataBase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddExpEduActivity : AppCompatActivity() {
    lateinit var type:String
    private lateinit var nameEt : TextInputEditText; private lateinit var addressEt : TextInputEditText;
    private lateinit var startEt : TextInputEditText;private lateinit var endEt : TextInputEditText
    private lateinit var startLyt : TextInputLayout;private lateinit var endLyt : TextInputLayout
    private lateinit var saveBtn : Button
    lateinit var picIv:ImageView
    private var picSet=false;private lateinit var pic:String
    private lateinit var companyType:CompanyType
    lateinit var database: AppDataBase
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exp_edu)
        nameEt=findViewById(R.id.et_name); addressEt=findViewById(R.id.et_address);
        startEt=findViewById(R.id.et_start);endEt=findViewById(R.id.et_end)
        startLyt=findViewById(R.id.lyt_start);endLyt=findViewById(R.id.lyt_end)
        saveBtn=findViewById(R.id.btn_save)
        picIv=findViewById(R.id.iv_pic)
        scope = CoroutineScope(Dispatchers.IO)

        database = AppDataBase.getDatabase(applicationContext)

        type = intent.getStringExtra("type").toString()

        actionBarConfig()
        selectProfilePic()
        setDates()
        addCompany()

    }

    private fun addCompany() {
        //ON CLICK SAVE
        saveBtn.setOnClickListener{
            if(!picSet){
                val snackbar = Snackbar.make(it, "Please select a logo for the company!", Snackbar.LENGTH_LONG)
                snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                snackbar.show()
            }else if(validateInput()) run {
                val company = Company(
                    pic,
                    nameEt.text.toString(),
                    addressEt.text.toString(),
                    startEt.text.toString(),
                    endEt.text.toString(),
                    companyType
                )
                scope.launch {
                    val dao = database.companyDao()
                    dao.insertCompany(company)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // cancel the CoroutineScope when it's no longer needed
        scope.cancel()
    }

    private fun setDates() {
        startEt.setOnClickListener{
            displayDatePicker(startEt)
        }
        endEt.setOnClickListener{
            displayDatePicker(endEt)
        }
    }

    private fun displayDatePicker(et: TextInputEditText) {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener { date ->
            val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            val formattedDate = formatter.format(Date(date))
            et.setText(formattedDate)
        }
    }

    private fun actionBarConfig() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        if(type=="exp"){
            supportActionBar?.title="Add Experience"
            companyType=CompanyType.EXPERIENCE
        }else{
            supportActionBar?.title="Add Education"
            companyType=CompanyType.EDUCATION
        }
        toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun selectProfilePic() {
        picIv.setOnClickListener{
            pickFromGallery()
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startForResult.launch(intent)
    }
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                picIv.setImageURI(data?.data)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveSelectedImage29(data?.data)
                }else{
                    saveSelectedImage(data?.data)
                }
            }
        }

    private fun saveSelectedImage(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "profile_pic_$timestamp.png"
            val file = File(getExternalFilesDir(null), fileName)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Profile picture saved!", Toast.LENGTH_SHORT).show()
            picSet=true
            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            pic=uri.toString()
        }else{
            Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }

    // API 29 and higher
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveSelectedImage29(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "profile_pic_$timestamp.png"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$packageName")
            }
            val resolver = applicationContext.contentResolver
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(fileName)
            resolver.delete(contentUri, selection, selectionArgs)
            val uri = resolver.insert(contentUri, contentValues)

            if (uri != null) {
                try {
                    val outputStream = resolver.openOutputStream(uri)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream?.flush()
                    outputStream?.close()
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                    Toast.makeText(this, "Profile picture saved!", Toast.LENGTH_SHORT).show()
                    picSet=true
                    pic=uri.toString()
                } catch (e: IOException) {
                    resolver.delete(uri, null, null)
                    Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(): Boolean {
        //Vérifier si les champs ne sont pas vides
        if(setError(nameEt,getString(R.string.must_not_be_empty)) || setError(addressEt,getString(R.string.must_not_be_empty)) || setError(startEt,getString(
                R.string.must_not_be_empty
            )) || setError(endEt,getString(
                R.string.must_not_be_empty
            ))){
            return false
        }
        return true
    }

    private fun setError(et: TextInputEditText, errorMsg: String): Boolean {
        if(et.text?.isEmpty() == true){
            (et.parent.parent as TextInputLayout).isErrorEnabled = true
            (et.parent.parent as TextInputLayout).error = errorMsg
            return true
        }else{
            (et.parent.parent as TextInputLayout).isErrorEnabled = false
            return false
        }
    }
}
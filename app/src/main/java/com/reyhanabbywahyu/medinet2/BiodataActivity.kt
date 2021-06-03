package com.reyhanabbywahyu.medinet2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.CalendarContract.Attendees.query
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat.query
import androidx.core.content.ContextCompat
import androidx.customview.widget.Openable
import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.reyhanabbywahyu.medinet2.DBHelper.DBHelper
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.`class`.action.ResponseAction
import com.reyhanabbywahyu.medinet2.`class`.getdata.ResponseGetDataUser
import com.reyhanabbywahyu.medinet2.activity.Login_Activity
import com.reyhanabbywahyu.medinet2.config.NetworkImage
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import com.reyhanabbywahyu.medinet2.utils.PreferenceUtils
import com.reyhanabbywahyu.medinet2.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.Serializable
import java.util.jar.Manifest

class BiodataActivity : AppCompatActivity(), ProgressRequestBody.UploadCallbacks {

    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private var selectedfileUri : Uri?= null
    lateinit var dialog : ProgressDialog
    lateinit var  biodata_image_view : ImageView
    lateinit var  biodata_beratView : TextView
    lateinit var  biodata_tinggiView : TextView
    lateinit var  biodata_namaView : TextView
    lateinit var  biodata_emailView : TextView
    lateinit var biodata_nama_pengguna : EditText
    lateinit var  biodata_tanggallahir : EditText
    lateinit var biodata_passwordlama : EditText
    lateinit var  biodata_passwordBaru : EditText
    lateinit var  biodata_ulangiPassword: EditText
    lateinit var biodata_email : EditText
    lateinit var  biodata_berat : EditText
    lateinit var  biodata_tinggi : EditText
    lateinit var biodata_logout : Button


    lateinit var  btnSimpanPersonal : Button
    lateinit var  btnSimpanAkun : Button
    lateinit var btnSimpanbiodata : Button

    lateinit var  hapusAkun : Button
//    lateinit var  database : DBHelper
    var user : UserResponse?= null

    fun refreshData() {
        val networkGetData = NetworkModule.service().getUserById(user?.id_user!!)
        networkGetData.enqueue(object : Callback<ResponseGetDataUser>{
            override fun onResponse(
                call: Call<ResponseGetDataUser>,
                response: Response<ResponseGetDataUser>
            ) {
                biodata_namaView.text = user?.nama
                biodata_emailView.text = user?.email
                biodata_beratView.text = user?.berat.toString()
                biodata_tinggiView.text = user?.tinggi.toString()
            }

            override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed to Get Information",Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun initViews() {
        biodata_image_view = findViewById(R.id.Img_biodata)

        biodata_beratView = findViewById(R.id.tx_biodata_berat_ganti)
        biodata_tinggiView = findViewById(R.id.tx_biodata_tinggi_ganti)
        biodata_emailView = findViewById(R.id.tx_biodata_email)
        biodata_namaView = findViewById(R.id.tx_biodata_namaPengguna)

        biodata_nama_pengguna = findViewById(R.id.et_biodata_namapengguna)
        biodata_tanggallahir = findViewById(R.id.et_biodata_tanggallahir)
        btnSimpanbiodata = findViewById(R.id.btn_biodata_SimpanBioBio)

        biodata_email =findViewById(R.id.et_biodata_email)
        btnSimpanAkun = findViewById(R.id.btn_biodata_SimpanPerubahanPassword)
        biodata_passwordlama = findViewById(R.id.et_biodata_passwordLama)
        biodata_passwordBaru = findViewById(R.id.et_biodata_passwordBaru)
        biodata_ulangiPassword =findViewById(R.id.et_biodata_Ulangpassword)

        btnSimpanPersonal = findViewById(R.id.btn_biodata_SimpanDataPersonal)
        biodata_tinggi= findViewById(R.id.et_biodata_tinggi)
        biodata_berat = findViewById(R.id.et_biodata_berat)

        hapusAkun = findViewById(R.id.btn_biodata_HapusAKUN)
        biodata_logout = findViewById(R.id.btnBiodata_Logout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)
        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)
        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
  //      database = DBHelper(this)
        initViews()
        refreshData()
        biodata_logout.setOnClickListener {
            PreferenceUtils.saveEmail("",this)
            PreferenceUtils.savePassword("",this)
            var intent = Intent(this, Login_Activity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)


        }
        biodata_image_view.setOnClickListener {


            AlertDialog.Builder(this).
            setTitle("Ganti Foto profil").setMessage("Ehh Maap Pakenya ini ,masih nub").
            setPositiveButton("Gallery",DialogInterface.OnClickListener{dialog,which ->
                openGallery()
                dialog.dismiss()
            }).setNegativeButton("Camera",DialogInterface.OnClickListener { dialog, which ->
                openCamera()
                dialog.dismiss()
            }).show()

        }
        btnSimpanbiodata.setOnClickListener {

            var textNamaNew = biodata_nama_pengguna.text.toString()
            var textTglLahirNew = biodata_tanggallahir.text.toString()
            if(textNamaNew.isNotEmpty()) {
                user?.nama = textNamaNew
                val networkUser =
                    NetworkModule.service().updateUserBiodata(user?.id_user!!, user?.nama!!)
                networkUser.enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(
                        call: Call<ResponseAction>,
                        response: Response<ResponseAction>
                    ) {
                        Toast.makeText(applicationContext,"Berhasil Update Nama",Toast.LENGTH_LONG).show()
                    }
                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                       Toast.makeText(applicationContext,"Gagal Update Nama ",Toast.LENGTH_LONG).show()
                    }
                })
            }
           // database.updateUserBiodata(user!!)
           refreshData()

        }

        btnSimpanAkun.setOnClickListener {

            //var textEmailNew = biodata_email.text.toString()
            var passwordBaru= biodata_passwordBaru.text.toString()
            var passwordlama = biodata_passwordlama.text.toString()
            var ulangipassword = biodata_ulangiPassword.text.toString()

            if(passwordBaru == ulangipassword && passwordlama == user?.password) {
                user?.password = passwordBaru
                val networkPass = NetworkModule.service().updateUserPassword(user?.id_user!!,user?.password!!)
                Log.d("Password Baru",user?.password.toString())
                refreshData()
            }
            else {
                Toast.makeText(this,"Password salah", Toast.LENGTH_LONG).show()
            }
        }
        btnSimpanPersonal.setOnClickListener {
            var tinggiNew= biodata_tinggi.text.toString().toInt()
            var beratNew= biodata_berat.text.toString().toInt()

            if(tinggiNew > 150 && tinggiNew  < 300) {
                user?.tinggi=tinggiNew
                val networkPesonal = NetworkModule.service().updateUserBeratTinggi(user?.id_user!!,user?.tinggi!!,user?.berat!!)
                networkPesonal.enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(
                        call: Call<ResponseAction>,
                        response: Response<ResponseAction>
                    ) {
                        Toast.makeText(applicationContext,"Berhasil Update Tinggi",Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {

                    }
                })
            //    database.updateUserPersonal(user!!)
                refreshData()
            }
            if(beratNew > 30 && beratNew < 300) {

                user?.berat= beratNew
              //  database.updateUserPersonal(user!!)
                val networkPesonal = NetworkModule.service().updateUserBeratTinggi(user?.id_user!!,user?.tinggi!!,user?.berat!!)
                networkPesonal.enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(
                        call: Call<ResponseAction>,
                        response: Response<ResponseAction>
                    ) {
                        Toast.makeText(applicationContext,"Berhasil Update Berat",Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {

                    }
                })
                refreshData()
            }

            else {
                Toast.makeText(this,"Password salah", Toast.LENGTH_LONG).show()
            }

        }
        hapusAkun.setOnClickListener {

            AlertDialog.Builder(this@BiodataActivity).
            setTitle("Apakah Anda Yakin ?").
            setMessage("Akun anda akan dihapus ?").setPositiveButton("Ya", DialogInterface.OnClickListener(){ dialog, which ->
              //  database.deletePersonalData(user!!)
                val deleteNetwork = NetworkModule.service().deleteUser(user?.id_user!!)
                deleteNetwork.enqueue(object : Callback<ResponseAction>{
                    override fun onResponse(
                        call: Call<ResponseAction>,
                        response: Response<ResponseAction>
                    ) {
                        Toast.makeText(this@BiodataActivity, "Telah Dihapus", Toast.LENGTH_LONG).show()
                        var intent = Intent(this@BiodataActivity,Login_Activity::class.java)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {

                    }
                })
            }).setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                dialog.dismiss()

            }).show()

        }
        bottomNavigation.selectedItemId = R.id.nav_biodata
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_beranda ->{
                    val intent_beranda = Intent(this,BerandaActivity::class.java)
                    intent_beranda.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent_beranda.putExtra("EXTRA_USER",user as Serializable)
                    startActivity(intent_beranda)
                }
                R.id.nav_obat ->{
                    val intent = Intent(this,KedaiObat_Activity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.putExtra("EXTRA_USER",user as Serializable)
                    startActivity(intent)
                }
            }
            false
        }

    }

    private fun openCamera() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ intent ->

            Log.d("dsk0",intent.toString())
            intent.resolveActivity(packageManager).also {
                Log.d("dsk2",intent.toString())
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
            }
        }

    }
    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getRealPathFromURI(Uri : Uri?) : String {
        var result : String = ""
        if (Uri != null) {
            val cursor: Cursor? = contentResolver.query(Uri, null, null, null, null)
            if (cursor == null) {
                result = Uri.path.toString()
            } else {
                var idx: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                Log.d("LDSd",idx.toString())
                    result = cursor.getString(idx)
                    cursor.close()
            }
        }

        return result
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun uploadFile() {

        dialog = ProgressDialog(this@BiodataActivity)
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setMessage("Uploading...")
        dialog.isIndeterminate = false
        dialog.setCancelable( false)
        dialog.max = 100
        dialog.show()

        val file = File("downloads",getRealPathFromURI(selectedfileUri))
        Log.d("FILEKFIASD",file.toString())
        val requestFile = ProgressRequestBody(file,this)

        val body = MultipartBody.Part.createFormData("uploaded_file", file.name,requestFile)

        Thread(Runnable {
            NetworkImage.service().uploadFile(body).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Toast.makeText(this@BiodataActivity,"Upload Success",Toast.LENGTH_LONG).show()
                    dialog.dismiss()

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@BiodataActivity,t.message,Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            })
        }).start()
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type="image/*"
            intent.resolveActivity(packageManager).also {
                Log.d("dsk",intent.toString())
                startActivityForResult(intent,REQUEST_PICK_IMAGE)
            }
        }
    }
    private fun checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQUEST_PERMISSION)

        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                if (data != null) {
                    selectedfileUri = data.data
                    biodata_image_view.setImageURI(selectedfileUri)
                    //uploadFile()
                    }
                } else if (requestCode == REQUEST_PICK_IMAGE) {
                    selectedfileUri =  data?.data
                    biodata_image_view.setImageURI(selectedfileUri)
                    uploadFile()
                }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        dialog.progress = percentage

    }
}
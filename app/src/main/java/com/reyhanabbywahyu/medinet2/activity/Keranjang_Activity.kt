package com.reyhanabbywahyu.medinet2.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.reyhanabbywahyu.medinet2.R
import com.reyhanabbywahyu.medinet2.`class`.ObatResponse
import com.reyhanabbywahyu.medinet2.`class`.UserResponse
import com.reyhanabbywahyu.medinet2.Adapter.KeranjangObatAdapter
import com.reyhanabbywahyu.medinet2.BerandaActivity
import com.reyhanabbywahyu.medinet2.`class`.action.ResponseAction
import com.reyhanabbywahyu.medinet2.config.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*
import java.util.jar.Manifest

class Keranjang_Activity : AppCompatActivity() {
    lateinit var recyclerViewKeranjang: RecyclerView
    lateinit var tvKeranjangAlamat: TextView
    lateinit var tvKeranjangCatatan: TextView
    lateinit var RbtnKeranjangTunai: RadioButton
    lateinit var RbtnKeranjangDompet: RadioButton
    lateinit var cvKeranjangPesanSekarang: CardView
    lateinit var tvKeranjangIsiDompet: TextView
    lateinit var tvKeranjangTotal: TextView
    lateinit var imgPrevKeranjang: CardView
    lateinit var radiogroup: RadioGroup
    lateinit var btnKeranjangAlamatGenerate : Button

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    val PERMISSION_ID = 1000
    var user : UserResponse = UserResponse()
    var obat : ObatResponse = ObatResponse()

    override fun onBackPressed() {
        //super.onBackPressed()
        //moveTaskToBack(true);

    }

    private fun initViews() {

        recyclerViewKeranjang = findViewById(R.id.recyclerView)
        tvKeranjangAlamat = findViewById(R.id.tvKeranjangAlamat)
        tvKeranjangCatatan = findViewById(R.id.tvKeranjangCatatan)
        RbtnKeranjangDompet = findViewById(R.id.RbtnKeranjangDompet)
        RbtnKeranjangTunai = findViewById(R.id.RbtnKeranjangTunai)
        cvKeranjangPesanSekarang = findViewById(R.id.cvKeranjangPesanSekarang)
        tvKeranjangIsiDompet = findViewById(R.id.tvKeranjangIsiDompet)
        tvKeranjangTotal = findViewById(R.id.tvKeranjangTotal)
        imgPrevKeranjang = findViewById(R.id.cardViewKeranajgg)
        radiogroup = findViewById(R.id.RbPembayaran)
        btnKeranjangAlamatGenerate = findViewById(R.id.btnKeranjangAlamatGenerate)
    }
    private fun checkAddress() {
        if(user != null) {
            if (user.alamat?.isNotEmpty()!! || user.alamat?.isNotBlank()!!) {
                tvKeranjangAlamat.text = user.alamat
            }
        }
    }
    private fun getDataFromIntent() {
        user = intent.getSerializableExtra("EXTRA_USER") as UserResponse
        if(intent.getSerializableExtra("EXTRA_OBAT") != null) {
            obat = intent.getSerializableExtra("EXTRA_OBAT") as ObatResponse
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        //inisialisasi Id
        initViews()
        getDataFromIntent()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        checkAddress()

        btnKeranjangAlamatGenerate.setOnClickListener {
            getLastLocation()
        }

        imgPrevKeranjang.setOnClickListener {
            // intent = Intent(applicationContext, UtamaActivity::class.java)

            if(intent.getSerializableExtra("EXTRA_OBAT") != null) {
                intent = Intent(applicationContext, Detail_ObatActivity::class.java)
                intent.putExtra("EXTRA_USER", user as Serializable)
                intent.putExtra("EXTRA_OBAT", obat as Serializable)

                startActivity(intent)
                finish()
            }
            else if(intent.getSerializableExtra("EXTRA_OBAT") == null) {

                intent = Intent(applicationContext, BerandaActivity::class.java)
                intent.putExtra("EXTRA_USER", user as Serializable)

                startActivity(intent)
                finish()
            }

        }

        radiogroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(applicationContext, " On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
        })

        tvKeranjangTotal.text = calculateHarga(user.item).toString()
        val adapter = KeranjangObatAdapter(user.item,object : KeranjangObatAdapter.getdata {
            override fun returndata(list: MutableList<ObatResponse>?): Unit{
                tvKeranjangTotal.text = calculateHarga(list).toString()
            }
        })
        recyclerViewKeranjang.adapter = adapter
        recyclerViewKeranjang.scrollToPosition(recyclerViewKeranjang.adapter?.itemCount!! - 1)

        tvKeranjangIsiDompet.text = user.balance.toString()

        cvKeranjangPesanSekarang.setOnClickListener {
            var id: Int = radiogroup.checkedRadioButtonId
            if (id != -1) {
                if(user.item.size != 0) {
                    val radio: RadioButton = findViewById(id)

                    //Dompet Digital Id = 2131361804
                    if (id == 2131361804 && tvKeranjangIsiDompet.text.toString().toDouble() < tvKeranjangTotal.text.toString().toDouble()) {
                        Toast.makeText(this, "Saldo Dompet Tidak Cukup", Toast.LENGTH_SHORT).show()
                        Log.d("PESANAN", "Saldo Tidak Cukup")
                    } else {
                        Log.d("PESANAN DATANG", "Pesanan Sedang Di perjalanan")
                        Toast.makeText(this, "Obat dalam Perjalanan", Toast.LENGTH_SHORT).show()
                        radio_button_click(user)
                    }
                }
            }
            if (tvKeranjangAlamat.text.toString().isNotEmpty() || tvKeranjangAlamat.text.toString().isNotBlank()) {
                user.alamat = tvKeranjangAlamat.text.toString()
                Log.d("Debug","alamat ${user.alamat}")
                Log.d("Debug","user id : ${user.id_user}")
                NetworkModule.service().updateUserAlamat(user.id_user,user.alamat.toString()).enqueue(object : Callback<ResponseAction> {
                    override fun onResponse(call: Call<ResponseAction>, response: Response<ResponseAction>) {
                        Log.d("Debug",response.body()?.message!!)
                        Log.d("Debug","SuccessFully Update Alamat")
                    }

                    override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                        Log.d("Debug","Failed Update Alamat")
                    }

                })
            }
            else {
                Log.d("Debug","Alamat Is Empty ${tvKeranjangAlamat.text.toString()}")
            }
            Toast.makeText(applicationContext,"Keranjang anda kosong",Toast.LENGTH_LONG).show()
        }


    }

    private fun getAddress(lat : Double, long : Double) : String {
        var cityName = ""
        var countryName = ""
        var fullAddress = ""

        var geoCoder = Geocoder(this, Locale.getDefault())
        var address : MutableList<Address> = geoCoder.getFromLocation(lat,long,1)
        cityName = address[0].locality
        countryName = address[0].countryName
        fullAddress = address[0].getAddressLine(0)
        return fullAddress
    }
    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location = task.result
                    if(location == null ) {
                        getNewLocation()
                    }
                    else {
                        tvKeranjangAlamat.text = getAddress(location.latitude,location.longitude)
                    }
                }
            }
            else {
                Toast.makeText(this,"Please Enabled Your Location Service",Toast.LENGTH_LONG).show()
            }
        }
        else {
            requestPermission()
        }
    }
    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }
    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var location = p0.lastLocation
            tvKeranjangAlamat.text = getAddress(location.latitude,location.longitude)

        }
    }
    private fun checkPermission() : Boolean {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                )
        {
            return true
        }

        return false
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_ID)
    }
    private fun isLocationEnabled() : Boolean{
        var locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == PERMISSION_ID) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug","You Have the permission")
            }
        }

    }
    fun radio_button_click(user : UserResponse){
        val radio: RadioButton = findViewById(radiogroup.checkedRadioButtonId)
        val intentSelesai = Intent(this, Selesai_Activity::class.java)
        intentSelesai.putExtra("EXTRA_USER",user as Serializable)
        startActivity(intentSelesai)
        finish()
    }

    fun calculateHarga(list: MutableList<ObatResponse>?): Double {
        var total = 0.0
        list?.forEach {
            total = total + (it.quantity?.times(it.harga!!)!!)

        }

        return total
    }
}
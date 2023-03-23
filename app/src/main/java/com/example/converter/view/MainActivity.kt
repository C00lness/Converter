package com.example.converter.view


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.example.converter.R
import com.example.converter.databinding.ActivityMainBinding
import com.example.converter.helper.Utiltity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        Utiltity.makeStatusBarTransparent(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //init()

        val btnConverter = binding.btnConverter
        val btnLocation = binding.btnLocation
        val btnBanks = binding.btnBanks
        val btnCB = binding.btnCB

        val Fr_Convert = ConverterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.Frame, Fr_Convert, "Fr_Convert")
            .addToBackStack(null)
            .commit()
        btnConverter.isEnabled = false

        btnConverter.setOnClickListener {
            val Fr_Convert = ConverterFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.Frame, Fr_Convert, "Fr_Convert")
                .addToBackStack(null)
                .commit()
            btnLocation.isEnabled = true
            btnBanks.isEnabled = true
            btnCB.isEnabled = true
            btnConverter.isEnabled = false
        }

        btnLocation.setOnClickListener {
            val Fr_Location = MapsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.Frame, Fr_Location, "Fr_Location")
                .addToBackStack(null)
                .commit()
            btnLocation.isEnabled = false
            btnBanks.isEnabled = true
            btnCB.isEnabled = true
            btnConverter.isEnabled = true
        }

        btnBanks.setOnClickListener{
            btnLocation.isEnabled = true
            btnBanks.isEnabled = false
            btnCB.isEnabled = true
            btnConverter.isEnabled = true

            val geoUriString = "geo:0,0?q=банки+рядом&z=2"
            val geoUri: Uri = Uri.parse(geoUriString)
            intent.data = geoUri
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }

        btnCB.setOnClickListener{
            val Fr_CB = CBFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.Frame, Fr_CB, "Fr_CB")
                .addToBackStack(null)
                .commit()
            btnLocation.isEnabled = true
            btnBanks.isEnabled = true
            btnCB.isEnabled = false
            btnConverter.isEnabled = true
        }

    }

    override fun onBackPressed() {
        binding.btnLocation.isEnabled = true
        binding.btnBanks.isEnabled = true
        binding.btnCB.isEnabled = true
        binding.btnConverter.isEnabled = true
        super.onBackPressed()
    }
}
package com.example.converter.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.converter.R
import com.example.converter.helper.EndPoints
import com.example.converter.helper.MainLocale
import com.example.converter.helper.Resource
import com.example.converter.model.Rates
import com.example.converter.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_converter, container, false)


        val spinner1 = rootView.findViewById<MaterialSpinner>(R.id.spnFirstCountry)
        spinner1.setItems(MainLocale.getCountries())

        spinner1.setOnItemSelectedListener { view, position, id, item ->
            val countryCode = MainLocale.getCountryCode(item.toString())
            val currencySymbol = MainLocale.getSymbolCurrency(countryCode)

            selectedItem1 = currencySymbol
            rootView.findViewById<TextView>(R.id.txtFirstCurrencyName).setText(selectedItem1)
        }

        val spinner2 = rootView.findViewById<MaterialSpinner>(R.id.spnSecondCountry)
        spinner2.setItems(MainLocale.getCountries())

        spinner2.setOnItemSelectedListener { view, position, id, item ->
            val countryCode = MainLocale.getCountryCode(item.toString())
            val currencySymbol = MainLocale.getSymbolCurrency(countryCode)

            selectedItem2 = currencySymbol
            rootView.findViewById<TextView>(R.id.txtSecondCurrencyName).setText(selectedItem2)
        }

        rootView.findViewById<Button>(R.id.btnConvert).setOnClickListener{

            val amount = rootView.findViewById<EditText>(R.id.etFirstCurrency).text.toString()
            var numberToCurrency: Double = 1.0
            if (amount.isNotEmpty() && amount != "0") {
                numberToCurrency = amount.toDouble()
            }

            val apiKey = EndPoints.API_KEY
            val from = selectedItem1.toString()
            val to = selectedItem2.toString()

            val mainViewModel: MainViewModel =
                ViewModelProvider(this@ConverterFragment).get(MainViewModel::class.java)
            mainViewModel.getConvertedData(apiKey, from, to, numberToCurrency)

            observeUi(mainViewModel, rootView)
        }

        return rootView
    }

    fun observeUi(mainViewModel: MainViewModel?, view: View){

        mainViewModel?.data?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.status == "success") {

                        val map: Map<String, Rates>
                        map = result.data.rates

                        map.keys.forEach {

                            val rateForAmount = map[it]?.rate_for_amount
                            mainViewModel.convertedRate.value = rateForAmount

                            val formattedString =
                                String.format("%,.2f", mainViewModel.convertedRate.value)
                            Log.d("MyResult", formattedString)
                            view.findViewById<TextView>(R.id.etSecondCurrency).text = formattedString
                        }

                    } else if (result.data?.status == "fail") {
                        Log.e("ErrorConverterResult", "Что-то пошло не так во ViewModel")
                    }
                }
                Resource.Status.ERROR -> {
                    Log.e("ErrorConverterResult", "Что-то пошло не так во ViewModel")
                }
                Resource.Status.LOADING -> {
                    view.findViewById<Button>(R.id.btnConvert).visibility = View.GONE
                }
            }
        })
    }
}
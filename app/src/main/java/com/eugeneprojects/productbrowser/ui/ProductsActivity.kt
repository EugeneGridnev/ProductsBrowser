package com.eugeneprojects.productbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eugeneprojects.productbrowser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
    }
}
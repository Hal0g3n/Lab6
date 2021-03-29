package com.example.lab6.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab6.R
import com.example.lab6.model.MovieSDK

class TransactionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onResume() {
        super.onResume()
        Thread {
            MovieSDK.cart.value?.clear()
            Thread.sleep(5000)
            this.requireActivity().finish()
        }.start()
    }
}
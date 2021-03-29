package com.example.lab6.ui.movieBuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.lab6.R
import com.example.lab6.model.Movie
import com.example.lab6.model.MovieSDK
import com.example.lab6.model.TicketType
import kotlinx.android.synthetic.main.ticketing_fragment.*

class TicketingFragment : Fragment() {

    var movieIndex: Int = 0
    var movie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ticketing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {checkout()}
        childSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, (0..10).toList())
        adultSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, (1..10).toList())

        val spinnerListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { onNothingSelected(parent) }
            override fun onNothingSelected(parent: AdapterView<*>?) {calcPrice()}
        }

        childSpinner.onItemSelectedListener = spinnerListener
        adultSpinner.onItemSelectedListener = spinnerListener
    }

    private fun calcPrice() {
        var totalPrice = movie!!.childPrice * childSpinner.selectedItem as Int
        totalPrice += movie!!.adultPrice * adultSpinner.selectedItem as Int

        totalCostDisplay.text = String.format("$%.2f", totalPrice)
    }

    override fun onStart() {
        super.onStart()
        val args = arguments?.let { TicketingFragmentArgs.fromBundle(it) }
        movieIndex = args?.movieIndex ?: 0
        movie = MovieSDK.movies[movieIndex]

        adultTicket.text = String.format("$%.2f %s", movie!!.adultPrice, adultTicket.text)
        childTicket.text = String.format("$%.2f %s", movie!!.childPrice, childTicket.text)
    }

    private fun checkout() {
        MovieSDK.buy(movie!!, TicketType.ADULT_TICKET, adultSpinner.selectedItem as Int)
        if (childSpinner.selectedItem != 0) MovieSDK.buy(movie!!, TicketType.CHILD_TICKET, childSpinner.selectedItem as Int)
        Navigation.findNavController(requireView()).navigate(R.id.action_ticketingFragment_to_cartFragment)
    }

}
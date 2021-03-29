package com.example.lab6.ui.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6.R
import com.example.lab6.model.MovieSDK
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val adapter = CartRecyclerAdapter(this, MovieSDK.cart.value!!)
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.adapter = adapter

        MovieSDK.cart.observe(viewLifecycleOwner, {
            adapter.cart = it
            adapter.notifyDataSetChanged()
            totalCostDisplay.text = String.format("$%.2f", MovieSDK.totalPrice())
        })

        cardView.setOnClickListener {
            if (MovieSDK.cart.value?.size ?: 0 == 0) {
                Snackbar.make(requireView(), "No Purchase to Make", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Finish Transaction")
                .setMessage("Are you sure you want to end this transaction?")
                .setPositiveButton(android.R.string.yes) { _,_ ->
                    Navigation.findNavController(requireView()).navigate(R.id.action_cartFragment_to_transactionFragment)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Creates the back button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setHomeButtonEnabled(true)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //This helps press the button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                Navigation.findNavController(toolbar).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
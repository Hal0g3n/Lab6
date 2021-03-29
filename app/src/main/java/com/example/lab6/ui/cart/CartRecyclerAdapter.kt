package com.example.lab6.ui.cart

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R
import com.example.lab6.model.MovieSDK
import com.example.lab6.model.Ticket
import com.example.lab6.model.TicketType

class CartRecyclerAdapter(
    private val fragment: CartFragment,
    var cart: ArrayList<Ticket>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.cart_card_layout,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder

        //Delete action
        holder.deleteButton.setOnClickListener {

            AlertDialog.Builder(fragment.requireContext())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this ticket?")
                .setPositiveButton(android.R.string.yes) {
                    _, _ -> MovieSDK.remove(cart[position])
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

        }

        holder.itemName.text = "${cart[position].copies} ${cart[position].movie.name} ${if (cart[position].type == TicketType.CHILD_TICKET) "Child TIcket" else "Adult Ticket"}"
        holder.itemCost.text = String.format("$%.2f", cart[position].cost)
    }

    override fun getItemCount(): Int {return cart.size}

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemCost: TextView = itemView.findViewById(R.id.itemCost)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

}
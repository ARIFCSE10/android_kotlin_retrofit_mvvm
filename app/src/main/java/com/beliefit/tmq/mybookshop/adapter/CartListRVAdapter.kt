package com.beliefit.tmq.mybookshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.activity.CartListActivity
import com.beliefit.tmq.mybookshop.model.BookEntity
import kotlinx.android.synthetic.main.book_list_row_home.view.*


class CartListRVAdapter : RecyclerView.Adapter<CartListRVAdapter.ViewHolder> {

    var context: Context? = null
    var books: List<BookEntity>? = null

    constructor(context: Context) {
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.cart_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books?.size ?: return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (context as CartListActivity).loadImageFromNetworkOrCache(holder, position, books?.get(position)?.preview)

        holder.title?.text = books?.get(position)?.title
        holder.subtitle?.text = books?.get(position)?.subTitle
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var image: ImageView? = null
        var title: TextView? = null
        var subtitle: TextView? = null

        constructor(view: View) : super(view) {
            image = view.image
            title = view.title
            subtitle = view.subtitle
        }
    }
}
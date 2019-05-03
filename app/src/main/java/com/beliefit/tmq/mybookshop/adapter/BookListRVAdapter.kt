package com.beliefit.tmq.mybookshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.activity.HomeActivity
import com.beliefit.tmq.mybookshop.model.BookEntity
import kotlinx.android.synthetic.main.book_list_row_home.view.*


class BookListRVAdapter : RecyclerView.Adapter<BookListRVAdapter.ViewHolder> {

    var context: Context? = null
    var books: List<BookEntity>? = null

    constructor(context: Context) {
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.book_list_row_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books?.size ?: return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (context as HomeActivity).loadImageFromNetworkOrCache(holder, position, books?.get(position)?.preview)

        holder.title?.text = books?.get(position)?.title
        holder.subtitle?.text = books?.get(position)?.subTitle
        holder.wish?.isChecked = books?.get(position)?.isWished ?: false
        holder.cart?.isChecked = books?.get(position)?.isCarted ?: false
        holder.wish?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (context as HomeActivity).addBookToWishList(position)
            } else {
                (context as HomeActivity).removeBookFromWishList(position)
            }
        }

        holder.cart?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (context as HomeActivity).addBookToCartList(position)
            } else {
                (context as HomeActivity).removeBookFromCartList(position)
            }
        }

        holder.rootLayout?.setOnClickListener {
            (context as HomeActivity).gotoDetailViewActivity(books?.get(position))
        }
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var image: ImageView? = null
        var title: TextView? = null
        var subtitle: TextView? = null
        var wish: ToggleButton? = null
        var cart: ToggleButton? = null
        var rootLayout: ConstraintLayout? = null

        constructor(view: View) : super(view) {
            image = view.image
            title = view.title
            subtitle = view.subtitle
            wish = view.wish
            cart = view.cart
            rootLayout = view.book_list_home_row_root
        }
    }
}
package com.rittmann.githubapiapp.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rittmann.androidtools.log.log

abstract class DataBindingBaseAdapter<T>(@NonNull private val values: ArrayList<T>) :
    RecyclerView.Adapter<DataBindingBaseAdapter.DataBindingBaseViewHolder<T>>() {

    protected var itemClickListener: ((View, position: Int, item: T) -> Unit)? = null
    protected lateinit var rootRecyclerView: RecyclerView

    interface OnItemClickListener {
        fun onItemClicked(view: View, positions: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingBaseViewHolder<T> =
        createViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getItemLayout(viewType), parent, false
            )
        )

    override fun onBindViewHolder(holder: DataBindingBaseViewHolder<T>, position: Int) {
        if (!values.isNullOrEmpty()) {
            holder.apply {
                bind(values[position], position, itemClickListener)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rootRecyclerView = recyclerView
    }

    fun subscribeToItemClick(listener: (view: View, position: Int, item: T) -> Unit) {
        itemClickListener = listener
    }

    fun swapData(list: List<T>) {
        values.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addData(list: List<T>) {
        "add ${list.size} already added $itemCount".log()
        val oldS = itemCount
        if (values.size > 0)
            notifyItemChanged(values.size - 1)

        values.apply {
            addAll(list)
        }
//        notifyItemInserted(values.size)
        notifyItemInserted(oldS)
    }

    fun clear() {
        "clear".log()
        values.clear()
        notifyDataSetChanged()
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getItem(position: Int): T = values[position]

    override fun getItemCount(): Int = if (values.isNullOrEmpty()) 0 else values.size

    abstract fun createViewHolder(viewBinding: ViewDataBinding): DataBindingBaseViewHolder<T>

    abstract fun getItemLayout(viewType: Int): Int

    abstract class DataBindingBaseViewHolder<T>(viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        abstract fun bind(
            item: T,
            position: Int,
            itemClickListener: ((View, position: Int, item: T) -> Unit)? = null
        )
    }
}
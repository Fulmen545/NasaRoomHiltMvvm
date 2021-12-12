package com.fulmen.nasaroom.ui.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fulmen.nasaroom.databinding.ListItemBinding
import com.senacor.nasamvvm.data.model.ItemData

class NasaAdapter(private val listener: ItemListener): RecyclerView.Adapter<NasaViewHolder>() {

    interface ItemListener{
        fun onClickedItem(blog: ItemData)
    }

    private val items = ArrayList<ItemData>()

    fun setItems(items: ArrayList<ItemData>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaViewHolder {
        val binding: ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NasaViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NasaViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class NasaViewHolder(private val itemBinding: ListItemBinding, private val listener: NasaAdapter.ItemListener):
    RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{

    private lateinit var nasaItem: ItemData

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: ItemData){
        this.nasaItem = item
        itemBinding.title.text = item.title
        itemBinding.date.text = item.pubDate
        Glide.with(itemBinding.root)
            .load(item.thumbnail)
            .fitCenter()
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(nasaItem)
    }

}
package com.example.galleryapp.imageDisplay

import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ImageItemBinding
import com.example.galleryapp.model.Image
import com.example.galleryapp.stringToBitMap


class GalleryAdapter(private val listener: ItemClickListener) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var imagesList =  listOf<Image>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var multiList = ArrayList<Image>()
    private var isMultiSelectOn = false
    private var actionMode : ActionMode? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = imagesList[position]
        holder.bind(item)
        holder.selectedIcon.visibility = View.INVISIBLE

        holder.container.setOnClickListener{
            if(isMultiSelectOn){

                if(multiList.contains(item)){
                    multiList.remove(item)
                    holder.selectedIcon.visibility = View.INVISIBLE
                }else{
                    multiList.add(item)
                    holder.selectedIcon.visibility = View.VISIBLE
                }

                if(multiList.size == 0){
                    actionMode?.finish()
                }else{
                    actionModeCallback.onPrepareActionMode(actionMode, null)
                }

            }else{
                listener.onItemClick(position)
            }

        }

        holder.container.setOnLongClickListener{
            isMultiSelectOn = true
            if(actionMode == null){
                actionMode = (it.context as AppCompatActivity).startActionMode(actionModeCallback)
            }

            if(multiList.contains(item)){
                multiList.remove(item)
                holder.selectedIcon.visibility = View.INVISIBLE
            }else{
                multiList.add(item)
                holder.selectedIcon.visibility = View.VISIBLE
            }

            if(multiList.size == 0){
                actionMode?.finish()
            }else{
                actionModeCallback.onPrepareActionMode(actionMode, null)
            }

            true
        }

    }

    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            menu.add("Delete")
            val menuInflater = mode.menuInflater
            menuInflater.inflate(R.menu.delete_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu?): Boolean {
            mode.title = "${multiList.size} selected"
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            if(item.itemId == R.id.deleteIcon){
                for(image in multiList){
                    listener.onDelete(image.imageId)
                }

                multiList.clear()
                mode.finish()
                return true
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            isMultiSelectOn = false
            multiList.clear()
            actionMode = null
        }
    }

    override fun getItemCount() = imagesList.size

    class GalleryViewHolder(private val binding : ImageItemBinding) : RecyclerView.ViewHolder(binding.root){

        val container = binding.container
        val selectedIcon = binding.deleteSelected

        companion object{
            fun from(parent : ViewGroup) : GalleryViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
                return GalleryViewHolder(binding)
            }
        }

        fun bind(item : Image){
            val imageBitmap = stringToBitMap(item.imageUrl)
            binding.imageViewItem.setImageBitmap(imageBitmap)
        }

    }

    fun submitList(list : List<Image>){
        imagesList = list
    }
}

interface ItemClickListener{
    fun onItemClick(position : Int)

    fun onDelete(imageId : Long)
}

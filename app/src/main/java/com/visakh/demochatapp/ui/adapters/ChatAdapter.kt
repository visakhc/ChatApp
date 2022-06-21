package com.visakh.demochatapp.ui.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.visakh.demochatapp.data.model.ChatModel
import com.visakh.demochatapp.databinding.ItemChatBinding
import com.visakh.demochatapp.utils.hide
import com.visakh.demochatapp.utils.shortToast
import com.visakh.demochatapp.utils.show


class ChatAdapter(var listener: OnClick) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private var chatList = mutableListOf<ChatModel>()

    inner class ViewHolder(val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatAdapter.ViewHolder {
        return ViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        with(chatList[position]) {
            when (msgDirection) {
                "send" -> {
                    holder.binding.llSend.show()
                    holder.binding.llReceived.hide()
                    holder.binding.tvSendTime.text = time
                    when (mime) {
                        "text" -> {
                            holder.binding.cvMessageSend.show()
                            holder.binding.imageSend.hide()
                            holder.binding.layoutSendImgText.hide()
                            holder.binding.textSend.text = message
                        }

                        "image" -> {
                            holder.binding.imageSend.show()
                            holder.binding.cvMessageSend.hide()
                            holder.binding.layoutSendImgText.hide()
                            holder.binding.imageSend.load(image)
                            holder.binding.imageSend.setOnClickListener {
                                if (holder.binding.imageSend.drawable == null) {
                                    it.context.shortToast("please wait")
                                } else {
                                    listener.openImage(holder.binding.imageSend.drawable)
                                }
                            }
                        }

                        "image_text" -> {
                            holder.binding.layoutSendImgText.show()
                            holder.binding.imageSend.hide()
                            holder.binding.cvMessageSend.hide()
                            holder.binding.tvSendMsgImgText.text = message
                            holder.binding.imgSendImgText.load(image)
                            holder.binding.imgSendImgText.setOnClickListener {
                                if (holder.binding.imgSendImgText.drawable == null) {
                                    it.context.shortToast("please wait")
                                } else {
                                    listener.openImage(holder.binding.imgSendImgText.drawable)
                                }
                            }
                        }

                    }
                }

                "received" -> {
                    holder.binding.llReceived.show()
                    holder.binding.llSend.hide()
                    holder.binding.tvReceivedTime.text = time
                    when (mime) {
                        "text" -> {
                            holder.binding.cvMessageReceived.show()
                            holder.binding.imageReceived.hide()
                            holder.binding.layoutReceivedImgText.hide()
                            holder.binding.textReceived.text = message
                        }

                        "image" -> {
                            holder.binding.imageReceived.show()
                            holder.binding.cvMessageReceived.hide()
                            holder.binding.layoutReceivedImgText.hide()
                            holder.binding.imageReceived.load(image)
                            holder.binding.imageReceived.setOnClickListener {
                                if (holder.binding.imageReceived.drawable == null) {
                                    it.context.shortToast("please wait")
                                } else {
                                    listener.openImage(holder.binding.imageReceived.drawable)
                                }
                            }
                        }

                        "image_text" -> {
                            holder.binding.layoutReceivedImgText.show()
                            holder.binding.imageReceived.hide()
                            holder.binding.cvMessageReceived.hide()
                            holder.binding.textRecImgText.text = message
                            holder.binding.imgRecImgText.load(image)
                            holder.binding.imgRecImgText.setOnClickListener {
                                if (holder.binding.imgRecImgText.drawable == null) {
                                    it.context.shortToast("please wait")
                                } else {
                                    listener.openImage(holder.binding.imgRecImgText.drawable)
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ChatModel>) {
        this.chatList.clear()
        this.chatList.addAll(list.reversed())
        notifyDataSetChanged()
    }

    fun updateList(pos: Int, list: List<ChatModel>) {
        this.chatList.clear()
        this.chatList.addAll(list)
        notifyItemChanged(pos)
    }

    interface OnClick {
        fun openImage(drawable: Drawable)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}
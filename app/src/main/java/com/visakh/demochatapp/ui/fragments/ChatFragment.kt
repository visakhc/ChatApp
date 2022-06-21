package com.visakh.demochatapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.visakh.demochatapp.data.model.ChatModel
import com.visakh.demochatapp.data.model.Response
import com.visakh.demochatapp.databinding.FragmentChatBinding
import com.visakh.demochatapp.ui.adapters.ChatAdapter
import com.visakh.demochatapp.utils.hide
import com.visakh.demochatapp.utils.logThis
import com.visakh.demochatapp.utils.show
import io.socket.client.IO
import io.socket.client.Socket
import java.text.SimpleDateFormat
import java.util.*

class ChatFragment : Fragment() {
    private var mSocket: Socket? = null
    private val IMAGE_REQ_CODE = 123
    private var mId = ""
    private var chatList = mutableListOf<ChatModel>()
    private val calInst = Calendar.getInstance()
    private var chatAdapter: ChatAdapter? = null
    private var binding: FragmentChatBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSocket = IO.socket("http://192.168.1.20:3000")
        mSocket?.connect()
        mSocket?.on("connected") { args ->
            if (mId.isBlank()) {
                mId = args[0].toString()  // change this with your choice of id
                logThis(mId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        handleEvents()
    }

    private fun init() {
        setRecycler()
        socketListeners()

        // getRecyclerData()
    }

    private fun socketListeners() {


        mSocket?.on("typing") { args ->
            requireActivity().runOnUiThread {
                if (args[0] != mId) {
                    if (args[1] == "true") {
                        binding?.lottieTyping?.show()
                    } else {
                        binding?.lottieTyping?.hide()
                    }
                }
            }
        }
        mSocket?.on("receivedMessage") { args ->
            val data = args[0].toString()
            val response = Gson().fromJson(data, Response::class.java)

            val id = response.data?.id.toString()
            val message = response.data?.message.toString()
            val imageUrl = response.data?.imageUrl.toString()
            val timeStamp = response.data?.timeStamp.toString()
            val time = response.data?.time.toString()
            val mime = response.data?.mime.toString()

            requireActivity().runOnUiThread {

                if (id == mId) {
                    chatList.add(
                        ChatModel(
                            message = message,
                            mime = mime,
                            image = imageUrl,
                            msgDirection = "send",
                            time = time
                        )
                    )
                } else {
                    chatList.add(
                        ChatModel(
                            message = message,
                            mime = mime,
                            image = imageUrl,
                            msgDirection = "received",
                            time = time
                        )
                    )
                }
                chatAdapter?.updateList(chatList)
                binding?.recyclerView?.smoothScrollToPosition(0)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleEvents() {


        binding?.etMessage?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 if (count != 0) {
                    mSocket?.emit("userTyping", mId, "true")
                } else {
                    mSocket?.emit("userTyping", mId, "false")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        binding?.ivSend?.setOnClickListener {

            val msg = binding?.etMessage?.text.toString()
            val timeStamp = System.currentTimeMillis().toString()
            val dateFormat = SimpleDateFormat("hh:mm a")
            val time = dateFormat.format(calInst.time)
            val mime = "text"
            val imageUrl = ""
            if (msg.isNotBlank()) {
                try {

                    mSocket?.emit("sendMessage", mId, msg, time, imageUrl, timeStamp, mime)
                    binding?.etMessage?.text?.clear()
                } catch (e: Exception) {
                    logThis("Exception    $e")
                }
            }
        }
        binding?.inclToolbar?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun sendMessage(msg: String, msgType: String, image: String, time: String) {
        chatList.add(
            ChatModel(
                message = msg,
                mime = msgType,
                image = image,
                msgDirection = "send",
                time = time
            )
        )
        chatAdapter?.updateList(chatList.size, chatList)
        binding?.recyclerView?.smoothScrollToPosition(chatList.size)
        binding?.etMessage?.text?.clear()
    }


    private fun getRecyclerData() {

        chatList.clear()
        chatList.add(ChatModel("Hi...", "text", "", "send", "02:02 PM"))
        chatList.add(ChatModel("Hello", "text", "", "recieved", "02:03 PM"))
        chatList.add(ChatModel("n", "image", "https://picsum.photos/500", "send", "02:03 PM"))
        chatList.add(ChatModel("Hiii", "text", "sdf", "recieved", "02:04 PM"))
        chatList.add(ChatModel("Helloooo", "text", "", "send", "02:05 PM"))
        chatList.add(
            ChatModel(
                "Nallww",
                "image",
                "https://picsum.photos/500",
                "recieved",
                "03:00 PM"
            )
        )
        chatList.add(
            ChatModel(
                "Test with Image and Text[Recieved]",
                "image_text",
                "https://picsum.photos/500",
                "recieved",
                "03:10 PM"
            )
        )
        chatList.add(
            ChatModel(
                "Test with Image and Text[Send]",
                "image_text",
                "https://picsum.photos/500",
                "send",
                "03:15 PM"
            )
        )
        chatAdapter?.updateList(chatList)

        binding?.recyclerView?.smoothScrollToPosition(chatList.size - 1)
    }

    private fun setRecycler() {
        chatAdapter = ChatAdapter(object : ChatAdapter.OnClick {
            override fun openImage(drawable: Drawable) {

            }
        })
        binding?.recyclerView?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            adapter = chatAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQ_CODE -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val msgType = data?.getStringExtra("msgType").toString()
                    val msg = data?.getStringExtra("message").toString()
                    val time = data?.getStringExtra("time").toString()
                    val image = data?.getStringExtra("image").toString()
                    sendMessage(msg, msgType, image, time)
                }
            }
        }
    }

}
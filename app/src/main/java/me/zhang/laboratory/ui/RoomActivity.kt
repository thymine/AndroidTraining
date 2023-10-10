package me.zhang.laboratory.ui

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import me.zhang.laboratory.App
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.diff.UserDiffCallback
import me.zhang.laboratory.ui.room.User
import me.zhang.laboratory.utils.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt
import kotlin.random.Random


class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val roomDb = App.getRoomDb()
        val userDao = roomDb.userDao()

        val userList = arrayListOf<User>()
        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                return object :
                    RecyclerView.ViewHolder(
                        layoutInflater.inflate(
                            R.layout.item_user,
                            parent,
                            false
                        )
                    ) {}
            }

            override fun getItemCount(): Int {
                return userList.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val itemView = holder.itemView
                userList[position].apply {
                    itemView.findViewById<View>(R.id.v_header).setBackgroundColor(avatar)
                    itemView.findViewById<TextView>(R.id.tv_uuid).text = uuid
                    itemView.findViewById<TextView>(R.id.tv_name).text = name
                    itemView.findViewById<TextView>(R.id.tv_maiden_name).text = maiden_name
                }

            }
        }
        val rvUsers = findViewById<RecyclerView>(R.id.rv_users)
        rvUsers.apply {
            this.adapter = adapter
            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = 8.dp().roundToInt()
                }
            })
        }
        findViewById<Button>(R.id.btn_add_random_user).setOnClickListener {
            // https://api.namefake.com/
            val nameFakeService = App.getNameFakeService()
            val userCall = nameFakeService.getFakeUser()
            val pb = findViewById<ProgressBar>(R.id.pb)
            pb.visibility = VISIBLE
            userCall.enqueue(object : CallbackAdapter() {
                override fun onSuccess(user: User) {
                    runBlocking {
                        val random = Random(System.currentTimeMillis())
                        val colorInt = Color.argb(
                            255,
                            random.nextInt(256),
                            random.nextInt(256),
                            random.nextInt(256)
                        )
                        user.avatar = colorInt
                        userDao.insert(user)
                    }
                }

                override fun onComplete() {
                    pb.visibility = INVISIBLE
                }
            })
        }

        userDao.getAll().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                val diffCallback = UserDiffCallback(userList, it)
                val diffResult = DiffUtil.calculateDiff(diffCallback)

                userList.clear()
                userList.addAll(it)
                diffResult.dispatchUpdatesTo(adapter)

                rvUsers.post {
                    val targetPosition = adapter.itemCount - 1
                    if (targetPosition > RecyclerView.NO_POSITION) {
                        rvUsers.smoothScrollToPosition(targetPosition)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    abstract class CallbackAdapter : Callback<User> {
        final override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                response.body()?.apply {
                    onSuccess(this)
                }
            }
            onComplete()
        }

        final override fun onFailure(call: Call<User>, t: Throwable) {
            onComplete()
        }

        abstract fun onSuccess(user: User)
        abstract fun onComplete()

    }

}
package uz.mirsaidoff.scaliotest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.mirsaidoff.scaliotest.R
import uz.mirsaidoff.scaliotest.model.User
import uz.mirsaidoff.scaliotest.databinding.ItemUserBinding

class UsersAdapter : PagingDataAdapter<User, UsersAdapter.ViewHolder>(UsersDiffUtilCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val vhBinding = ItemUserBinding.bind(view)

        fun bind(user: User?) {
            with(vhBinding) {
                tvLogin.text = user?.login
                tvAvatarUrl.text = user?.avatarUrl
                tvType.text = user?.type
            }

        }
    }
}

private object UsersDiffUtilCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login
            && oldItem.avatarUrl == newItem.avatarUrl
    }

}
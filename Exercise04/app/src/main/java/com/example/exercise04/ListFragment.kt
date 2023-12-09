package com.example.exercise04

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise04.databinding.DialogLayoutBinding
import com.example.exercise04.databinding.FragmentListBinding
import com.example.exercise04.databinding.ListItemBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: MyAdapter
    lateinit var repo: PlayerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = PlayerRepository.getInstance(requireContext())!!
        adapter = MyAdapter(repo.getData())
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val recView = binding.recyclerView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter

        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            "addNewItem",
            viewLifecycleOwner
        ) { _, bundle ->
            run {
                if (bundle.getBoolean("toAdd")) {
                    val playerName = bundle.getString("name", "")
                    val playerSurname = bundle.getString("surname", "")
                    val playerDesc = bundle.getString("desc", "")
                    val playerNumber = bundle.getString("number", "")
                    val playerPosition = bundle.getString("position", "Striker")
                    val isGood = bundle.getBoolean("good", true)

                    val newItem = DBPlayer(
                        playerName,
                        playerSurname,
                        playerDesc,
                        playerNumber,
                        playerPosition,
                        isGood
                    )

                    repo.addPlayer(newItem)
                    adapter.data = repo.getData()!!
                    adapter.notifyDataSetChanged()
                }
            }
        }

        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add -> {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class MyAdapter(var data: MutableList<DBPlayer>?) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding: ListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val name: TextView = viewBinding.playerName
            val surname: TextView = viewBinding.playerSurname
            val position: TextView = viewBinding.playerPosition
            val img: ImageView = viewBinding.playerImg
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data?.size ?: 0
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val current = data?.get(position)

            if (current != null) {
                holder.name.text = current.playerName
            }
            if (current != null) {
                holder.surname.text = current.playerSurname
            }
            if (current != null) {
                holder.position.text = current.playerPosition
            }

            holder.itemView.setOnClickListener {
                if (current != null) {
                    parentFragmentManager.setFragmentResult(
                        "msgtochild", bundleOf(
                            "id" to current.id,
                            "name" to current.playerName,
                            "surname" to current.playerSurname,
                            "desc" to current.playerDesc,
                            "number" to current.playerNumber,
                            "position" to current.playerPosition,
                            "good" to current.isGood
                        )
                    )
                }

                findNavController().navigate(R.id.action_listFragment_to_showFragment)
            }

            holder.itemView.setOnLongClickListener {
                val view = DialogLayoutBinding.inflate(layoutInflater).root
                val builder = AlertDialog.Builder(requireActivity())

                builder.setTitle("Delete Dialog")
                    .setPositiveButton("Accept") { _, _ ->
                        if (repo.deletePlayer(data!![position])) {
                            notifyDataSetChanged()
                            data = repo.getData()
                        }

                        Toast.makeText(requireActivity(), "Player deleted", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }

                builder.setView(view)

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()

                true
            }

            if (current != null) {
                when (current.playerPosition) {
                    "Striker" -> holder.img.setImageResource(R.drawable.striker)
                    "Middle fielder" -> holder.img.setImageResource(R.drawable.middle)
                    "Defender" -> holder.img.setImageResource(R.drawable.defender)
                    "Goalkeeper" -> holder.img.setImageResource(R.drawable.goalkeeper)
                    else -> holder.img.setImageResource(R.drawable.striker)
                }
            }

            if (current != null) {
                when (current.playerPosition) {
                    "Striker" -> holder.itemView.setBackgroundColor(
                        resources.getColor(
                            R.color.red,
                            null
                        )
                    )

                    "Middle fielder" -> holder.itemView.setBackgroundColor(
                        resources.getColor(
                            R.color.green,
                            null
                        )
                    )

                    "Defender" -> holder.itemView.setBackgroundColor(
                        resources.getColor(
                            R.color.orange,
                            null
                        )
                    )

                    "Goalkeeper" -> holder.itemView.setBackgroundColor(
                        resources.getColor(
                            R.color.yellow,
                            null
                        )
                    )

                    else -> holder.itemView.setBackgroundColor(
                        resources.getColor(
                            R.color.white,
                            null
                        )
                    )
                }
            }
        }
    }

    companion object
}
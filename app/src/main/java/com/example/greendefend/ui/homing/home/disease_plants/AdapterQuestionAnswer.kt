package com.example.greendefend.ui.homing.home.disease_plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.greendefend.R
import com.example.greendefend.domin.model.home.QuestionAnswer


class AdapterQuestionAnswer(private var listQuestion:MutableList<QuestionAnswer>): RecyclerView.Adapter<AdapterQuestionAnswer.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textQuestion: TextView =itemView.findViewById(R.id.questionTxt)
        var textAnswer:TextView=itemView.findViewById(R.id.answerTxt)
        var imgBtn:ImageButton=itemView.findViewById(R.id.imageButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.item_question_and_answer,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var isVisible=false

        var questionData=listQuestion[position]
        holder.textQuestion.text=questionData.question
        holder.textAnswer.text=questionData.answer
        //isVisible is a varialbe defining in QuestionAnswer with value=false
        if (questionData.isVisible) {
            holder.textAnswer.visibility = View.VISIBLE
            holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.arrow_up))
        } else {
            holder.textAnswer.visibility = View.GONE
            holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.arrow_down))
        }

        holder.imgBtn.setOnClickListener {
            //after click it make isVisible=true
            questionData.isVisible = !questionData.isVisible // Toggle the visibility state

            if (questionData.isVisible) {//this line executed
                holder.textAnswer.visibility = View.VISIBLE
                holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.arrow_up))
            } else {
                holder.textAnswer.visibility = View.GONE
                holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.arrow_down))
            }
        }
//        holder.imgBtn.setOnClickListener {
//
//            if(isVisible){
//                holder.textAnswer.visibility=View.GONE
//                holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,R.drawable.arrow_down))
//
//            }else{
//                holder.textAnswer.visibility=View.VISIBLE
//                holder.imgBtn.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,R.drawable.arrow_up))
//
//            }
//            isVisible = !isVisible // Toggle the value of isVisible
//
//
//        }
    }

    override fun getItemCount(): Int {
        return listQuestion.size
    }


}
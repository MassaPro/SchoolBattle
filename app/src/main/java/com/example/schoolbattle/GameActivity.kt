package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*

//на каком вопросе сейчас игрок     статистика ответов(0/1/2)               условие текущего вопроса
public class data_base(var number_of_question: Int,var answer_questions :String, var current_issue : String,
                      var real_answer: Int, var answer_1: Int,var answer_2:Int,var answer_3:Int)



class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val Data_base = data_base(0, "2120", "сколько будет 2+2",
            4,3,5,6)      // потом нужно заменить


        if(Data_base.answer_questions[0]=='0')         //подбор цветов для прямоугольников, обозначающих прогресс игры в зависимости от answer_questions
        {
            image1.setImageResource(R.drawable.none);
        }
        if(Data_base.answer_questions[0]=='1')
        {
            image1.setImageResource(R.drawable.not_right);
        }
        if(Data_base.answer_questions[0]=='2')
        {
            image1.setImageResource(R.drawable.right);
        }

        if(Data_base.answer_questions[1]=='0')
        {
            image2.setImageResource(R.drawable.none);
        }
        if(Data_base.answer_questions[1]=='1')
        {
            image2.setImageResource(R.drawable.not_right);
        }
        if(Data_base.answer_questions[1]=='2')
        {
            image2.setImageResource(R.drawable.right);
        }

        if(Data_base.answer_questions[2]=='0')
        {
            image3.setImageResource(R.drawable.none);
        }
        if(Data_base.answer_questions[2]=='1')
        {
            image3.setImageResource(R.drawable.not_right);
        }
        if(Data_base.answer_questions[2]=='2')
        {
            image3.setImageResource(R.drawable.right);
        }

        if(Data_base.answer_questions[3]=='0')
        {
            image4.setImageResource(R.drawable.none);
        }
        if(Data_base.answer_questions[3]=='1')
        {
            image4.setImageResource(R.drawable.not_right);
        }
        if(Data_base.answer_questions[3]=='2')
        {
            image4.setImageResource(R.drawable.right);
        }




        text_task.text  = Data_base.current_issue // пишем условие задачи
        var list_answers = listOf(Data_base.answer_1, Data_base.answer_2, Data_base.answer_3, Data_base.real_answer ) //список из ответов
        list_answers.shuffled() //перемешиваем его
        button1.text = list_answers[0].toString()  //выдаем в виде кнопок
        button2.text = list_answers[1].toString()
        button3.text = list_answers[2].toString()
        button4.text = list_answers[3].toString()




        button1.setOnClickListener{                                 //проверка при нажатии на кнопку и передача через Intent в другое активити
            var intent = Intent(this,AnaliticalActivity::class.java)
            if(button1.text == Data_base.real_answer.toString()) {
                intent.putExtra("Answer","Right")
            }
            else
            {
                intent.putExtra("Answer","False")
            }
            startActivity(intent)
        }

        button2.setOnClickListener{
            var intent = Intent(this,AnaliticalActivity::class.java)
            if(button2.text == Data_base.real_answer.toString()) {
                intent.putExtra("Answer","Right")
            }
            else
            {
                intent.putExtra("Answer","False")
            }
            startActivity(intent)
        }

        button3.setOnClickListener{
            var intent = Intent(this,AnaliticalActivity::class.java)
            if(button3.text == Data_base.real_answer.toString()) {
                intent.putExtra("Answer","Right")
            }
            else
            {
                intent.putExtra("Answer","False")
            }
            startActivity(intent)
        }

        button4.setOnClickListener{
            var intent = Intent(this,AnaliticalActivity::class.java)
            if(button4.text == Data_base.real_answer.toString()) {
                intent.putExtra("Answer","Right")
            }
            else
            {
                intent.putExtra("Answer","False")
            }
            startActivity(intent)
        }
    }



}

 package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

 class MainActivity : AppCompatActivity() {
     private var tvInput:TextView?=null
     var lastNum:Boolean=false
     var lastDeci:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvinput)
    }
    fun onDigit(view:View){
        tvInput?.append((view as Button).text)
        lastNum=true
        lastDeci=false
       // Toast.makeText(this,"Button Pressed",Toast.LENGTH_LONG).show()
    }
    fun onClear(view: View){
        tvInput?.text=""
    }
    fun onDecimal(view: View){
        if(lastNum && !lastDeci){
            tvInput?.append(".")
            lastNum=false
            lastDeci=true
        }
    }
    fun onOperator(view: View){
        tvInput?.text?.let {
            if (lastNum && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNum=false
                lastDeci=false
            }
        }
    }
    fun onEquals(view: View){
        tvInput?.text.let {
            if(lastNum){
                var tvValue=tvInput?.text.toString()
                var prefix=""
                try {
                    if(tvValue.startsWith("-")){
                        prefix ="-"
                        tvValue=tvValue.substring(1)
                    }
                    if (tvValue.contains("-")){
                        val splitValue=tvValue.split("-")
                        var one=splitValue[0]
                        var two=splitValue[1]
                        if(prefix.isNotEmpty()){
                            one=prefix+one
                        }

                        tvInput?.text=removeDecimalAfterWholeNumber((one.toDouble()-two.toDouble()).toString())
                    }
                    else if (tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        tvInput?.text = removeDecimalAfterWholeNumber((one.toDouble() + two.toDouble()).toString())
                    }
                    else if (tvValue.contains("*")) {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        tvInput?.text = removeDecimalAfterWholeNumber((one.toDouble() * two.toDouble()).toString())
                    }
                    else if (tvValue.contains("/")) {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        tvInput?.text = removeDecimalAfterWholeNumber((one.toDouble() / two.toDouble()).toString())
                    }


                }catch (e:ArithmeticException){
                    e.printStackTrace()
                }
            }
        }
    }
    private fun removeDecimalAfterWholeNumber(result:String):String{
        var value=result
        if(result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return value
    }
    fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("+") || value.contains("*") || value.contains(("/")) || value.contains("-")
        }
    }
}
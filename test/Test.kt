package com.anupam.kotlin_training.basics.test


class question {
    var  id: Int? = 0
        get() =  field
        set(value) {
            field = value
        }
    var ques: String? = ""
        get() = field
        set(value) {
            field = value
        }
    var choices: Array<String> = arrayOf("")
        get() = field
        set(value) {
            field = value
        }
    var answer: Int? = 0
        get() = field
        set(value) {
            field = value
        }
}


class MenuHelper {
    val quest: ArrayList<question> = ArrayList<question>()

    fun showMenu() {
        var choice: Int? = 0
        println("1. Add Question")
        println("2. Show Questions")
        println("3. Test Quesion")
        println("4. Exit")

        while(true) {
            println("Enter Choice")
            choice = readLine()?.toInt()
            when(choice) {
                1 -> "${add()}"
                2 -> "${show()}"
                3 -> "${test()}"
                else -> break
            }
        }
    }

    fun add() {
        val qn: question = question()
        println("performing add operation.....")
        println("Enter Question no.")
        qn.id = readLine()?.toInt()
        println("Enter question")
        qn.ques = readLine().toString()
        println("Enter options for mcq")
        var str :Array<String> = Array<String>(5){""}

        for (i in 1..4){
            print("c${i}")
            str[i] = readLine().toString()
        }
        // qn.choices = str
        qn.choices = str

        println("Enter answer")
        qn.answer = readLine()?.toInt()
        quest.add(qn)
    }

    fun show() {
        println("Displaying list")
        for(i in quest) {
            println("id - ${i.id} ${i.ques}")
            for (j in i.choices)
                println("     $j")
        }
    }

    fun test() {
        println("Enter question no.")
        val no = readLine()?.toInt()

        val op = quest.find{
            it.id == no
        }
        println("give answer")
        val ans = readLine()?.toInt()

        if(ans == op?.answer) {
            println("correct")
        }
        else
        {
            println("wrong")
        }
    }
}


fun main() {
    val x = MenuHelper()
    x.showMenu()
}

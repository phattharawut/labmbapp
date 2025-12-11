package com.example.lab2kotlin2.kotlin2


data class Subject(val id: String, val name: String, val credit: Int)

open  class  Person(fName: String, lName: String, deptName: String) {
    val firstName: String
    val lastName: String
    protected val department: String

    init {
        firstName = fName.replaceFirstChar { it.uppercase() }
        lastName = lName.replaceFirstChar { it.uppercase() }
        department = "$deptName, College of Computing"
    }

    open  fun showDetail(){
        println("$firstName is at $department." )
    }

    companion object{
        fun  showCompanion(fist_Name: String, last_Name: String, age: Int){
            println("Person is called from companion object : $fist_Name $last_Name is $$age years old.")
        }

    }

}


class Teacher(fName: String, lName: String, deptName: String, year: Int): Person(fName, lName, deptName) {

    private var salary : Int = 0
    private val yearClass : Int = year
    private var creditClass : Int = 0


    override fun showDetail() {
        println("$firstName is a teacher for $yearClass years at $department.")
    }


    fun calSalary() {
        when {
            yearClass < 5 -> salary = 25000 + (2000 * yearClass)
            yearClass < 10 -> salary = 36000 + (2000 * (yearClass - 5))
            yearClass < 15 -> salary = 47000 + (2000 * (yearClass - 10))
            yearClass < 20 -> salary = 58000 + (2000 * (yearClass - 15))
            else -> salary = 60000 + (2000 * (yearClass - 20))
        }

        println("${firstName}'s salary is  $salary baht")
    }

    fun teach(subj : Subject){
        println(subj.name.toString())

        creditClass += subj.credit
    }


    fun displayCredit() {
        println("${firstName} teaches $creditClass credits.")
    }
}

object Singleton_Person {
    const val fist_Name = "David"
    const val last_Name = "Bowie"
    var age = 23
    fun showCompanion() {
        println("Person is called from singleton object : $fist_Name $last_Name is $age years old.")
    }
}



fun main() {
    val subject1 = Subject(id = "SC362007", name = "Moblie Device Programming", credit = 3)
    val subject2 = Subject(id = "SC362005", name = "Databease Analysis and Design", credit = 3)
    val subject3 =
        Subject(id = "SC361003", name = "Object Oriented Concepts and Programming", credit = 1)

    val person1 = Person(fName = "Alice", lName = "Wonderland", deptName = "Computer Science")
    println("Member NO 1 :" + person1.firstName + " " + person1.lastName)
    person1.showDetail()
    println()

    println("Member NO 2 :")
    Person.showCompanion("Bobby", "Brown", 25)
    println()

    println("Member NO 3 :")
    Singleton_Person.showCompanion()
    println()


    val teacher1 = Teacher("Cris","Evans","Information Teachnology",10)
    println("Member NO 4 :" + teacher1.firstName +" "+ teacher1.lastName)
    teacher1.showDetail()
    teacher1.calSalary()
    println("Chris Teaches:")
    teacher1.teach(subject1)
    teacher1.teach(subject2)
    teacher1.teach(subject3)
    teacher1.displayCredit()

}


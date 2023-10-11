fun main(args: Array<String>) {
    println("Hello World!")
    println(1 + 2 + 3)

    println("Program arguments: ${args.joinToString()}")

    var age: Int
    var height = 1.75
    val weight: Float = 60.5F
    val lastName = "Swiatek"
    val firstName: String

    height = 1.77
//    weight = 71.5F
    firstName = "Iga"
//    firstName = "Aga"

    println("Hello ${firstName}" + " " + lastName)

    print("The sum is: ")
    printSum(11, 22)
    println("Next sum is: " + addInt(111, 222))

    hello("Rafa", "Nadal")
    hello("You")

    println("The sum 2 is: " + addInt2(22, 33))
    println("The sum 3 is: " + addInt3(222, 333))

    var isIn: Boolean = typedFun(listOf(1, 2, 3, 4, 5), 3)
    println("Is 3 included?: " + isIn)
    isIn = typedFun(listOf("a", "b", "c"), "d")
    println("Is d included?: " + isIn)

    val result = calculate(44, 55, ::addInt)
    println(result)

    funTypes()

    nullTest()

    flowControl(1)

    lambda()
}

fun addInt(a: Int, b: Int): Int {
    return a + b
}

fun printSum(a: Int, b: Int) {
    println("The sum is: " + (a + b))
}

fun hello(first: String, last: String = "Student") {
    println("Hello " + first + " " + last)
}

fun addInt2(a: Int, b: Int): Int = a + b

fun addInt3(a: Int, b: Int) = a + b

fun <T> typedFun(list: List<T>, item: T): Boolean {
    return list.contains(item)
}

fun funTypes() {
    fun hello1() = println("hello 1")
    val hello2: () -> Unit = { println("hello 2") }

    fun newHello(x: Boolean): () -> Unit {
        if (x)
            return ::hello1

        return hello2
    }

    newHello(true)()
    newHello(false)()
}

fun calculate(a: Int, b: Int, operationFun: (Int, Int) -> Int): Int {
    return operationFun(a, b)
}

fun nullTest() {
    var a: String = "abc"
//    a= null

    var b: String? = "abc"
    b = null

    println(a + " " + b)
    println(a.length)
//    println(b.length)
//    a = b

    println(b?.length)

    var l2 = b?.length ?: -1
    println(l2)

    val nList: List<Int?> = listOf(1, 2, null, 4)

    for (item in nList) {
        item?.let { print(it) }
    }

    println()

    var c: String? = "abcd"
//    println(c.length)
    println(c!!.length)
}

fun flowControl(day: Int) {
    val result = when (day) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6 -> "Saturday"
        7 -> "Sunday"
        8, 9 -> "These days were canceled"
        else -> "Invalid day."
    }

    println(result)

    val daysList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    for (s in daysList) {
        print(" " + s)
    }

    println()

    for (i in 1..5) {
        print(" " + i)
    }

    println()
}

fun lambda() {
    val myVar: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    val myVar2: (Int, Int) -> Int = { a, b -> a + b }
    val myVar3 = { a: Int, b: Int -> a + b }
    val myVar4 = { a: Int, b: Int -> a + b }(44, 55)

    println("lambda result 1 = " + myVar(11, 22))
    println("lambda result 2 = " + myVar2(22, 33))
    println("lambda result 3 = " + myVar3(33, 44))
    println("lambda result 4 = " + myVar4)

    fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }

    var x = calculate(11, 22, { a, b -> a * b })
    x = calculate(11, 22) { a, b -> a * b }

    println("trailing lambda = " + x)

    val myArr = intArrayOf(1, 2, 3, 4)

    print("array = ")
    myArr.forEach { x -> print(" $x") }
    println()

    print("squares = ")
    myArr.forEach { print(" " + it * it) }
    println()

    print("omitted arg = ")
    myArr.forEach { _ -> print(" cos ") }
    println()
}
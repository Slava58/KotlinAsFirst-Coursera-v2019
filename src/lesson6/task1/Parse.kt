@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
//fun main() {
//    println("Введите время в формате ЧЧ:ММ:СС")
//    val line = readLine()
//    if (line != null) {
//        val seconds = timeStrToSeconds(line)
//        if (seconds == -1) {
//            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
//        } else {
//            println("Прошло секунд с начала суток: $seconds")
//        }
//    } else {
//        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
//    }
//}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    var listStr = str.split(" ")
    var tempStr = ""
    var month: Int
    if (listStr.size != 3) return tempStr
    var day: Int = listStr[0].toInt()
    var year: Int = listStr[2].toInt()
    var daysInMonth = mutableMapOf<String, Int>(
        "января" to 31,
        "февраля" to 28,
        "марта" to 31,
        "апреля" to 30,
        "мая" to 31,
        "июня" to 30,
        "июля" to 31,
        "августа" to 31,
        "сентября" to 30,
        "октября" to 31,
        "ноября" to 30,
        "декабря" to 31
    )
    if (daysInMonth[listStr[1]] == null) return tempStr
    if (year % 400 == 0 || ((year % 100 != 0) && (year % 4 == 0))) daysInMonth["февраль"] = 29
    if ((day <= 0) || (day > daysInMonth[listStr[1]]!!)) return tempStr
    month = listOf(daysInMonth.keys)[0].indexOf(listStr[1]) + 1
    tempStr = String.format("%02d.%02d.%4d", day, month, year)
    return tempStr
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    var tempList = digital.split(".")
    var day = -1
    var month = -1
    var year = -1
    if (tempList.size != 3) return ""
    var monthSet = arrayOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )
    var dayInMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    try {
        day = tempList[0].toInt()
        month = tempList[1].toInt()
        year = tempList[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if ((year == null) || (year < 0)) return ""
    if (month == null || month !in 1..12) return ""
    if (year % 400 == 0 || ((year % 100 != 0) && (year % 4 == 0))) dayInMonth[1] = 29
    if ((day == null) || (day !in 0..dayInMonth[month - 1])) return ""
    return String.format("%d %s %04d", day, monthSet[month - 1], year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var isBracketOpen = false
    var openBracketIndex = 0
    var result = ""
    var tempStr = ""
    tempStr = phone.filter(fun(it: Char) = (it != ' ' && (it != '-')))

    if (!((tempStr.first() in '1'..'9') || tempStr.first() == '+')) return ""
    println(tempStr)
    for (i in 1 until tempStr.length){
        if(tempStr[i] == ')' && !isBracketOpen) return ""
        if(tempStr[i] == '(' && isBracketOpen) return ""
        if(tempStr[i] == '(') {
            isBracketOpen = true
            openBracketIndex = i
        }
        if (tempStr[i] == ')' && openBracketIndex + 1 >= i) return ""
    }
    tempStr = tempStr.filter(fun(it: Char) = (it != '(' && (it != ')')))
    result += tempStr.first()
    for (i in 1 until tempStr.length) {
        if (tempStr[i] !in '0'..'9') return ""
        result += tempStr[i]
    }
    return result
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var jumpList = jumps.split(' ')
    var maxJump = -1
    var tempJump = 0
    for (str in jumpList){
        if (str == "-" || str == "%") continue
        else {
            try {
                tempJump = str.toInt()
            } catch (e: NumberFormatException){
                return -1
            }
            if (maxJump <= tempJump) maxJump = tempJump
        }
    }
    return maxJump
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val tempList = jumps.split(" ")
    var result = -1
    var high = -1
    if (tempList.size < 2 || tempList.size % 2 != 0) return -1
    for (i in tempList.size - 1 downTo 0 step 2) {
        if (tempList[i].contains('+')) {
            try {
                high = tempList[i - 1].toInt()
            } catch (e: NumberFormatException) {
                return -1
            }
            if (high > result) result = high
        }
    }
    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var sum = 0
    var tempNum = 0
    if (expression.isNullOrEmpty()) throw IllegalArgumentException()
    var exceptionList = expression.split(" ")
    try {
        println(exceptionList[0].first())
        if (exceptionList[0].first() !in '0'..'9') throw IllegalArgumentException()
        sum = exceptionList[0].toInt()
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
    if (exceptionList.size % 2 == 0) throw IllegalArgumentException()
    if (exceptionList.size < 3) return sum
    for (i in 1 until exceptionList.size step 2) {
        try {
            if (exceptionList[i + 1].first() !in '0'..'9') throw IllegalArgumentException()
            tempNum = exceptionList[i + 1].toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException()
        }

        if (exceptionList[i] == "+") sum += tempNum
        else if (exceptionList[i] == "-") sum -= tempNum
        else throw IllegalArgumentException()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var ind = 0
    if (str == null) return -1
    var strList = str.split(" ")
    var previousWord = strList[0].toLowerCase()
    for (word in 1 until strList.size) {
        if (previousWord == strList[word].toLowerCase()) return ind
        ind += previousWord.length + 1
        previousWord = strList[word].toLowerCase()
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val tempList = description.split("; ")
    var result = ""
    var tmpPrice: Double
    var price = -1.0
    var tempResult: List<String>
    for (str in tempList) {
        try {
            tempResult = str.split(" ")
            try {
                tmpPrice = tempResult[1].toDouble()
            } catch (e: NumberFormatException) {
                return ""
            }
            if (tmpPrice < 0) return ""
            if (tmpPrice >= price) {
                price = tmpPrice
                result = tempResult[0]
            }
        } catch (e: IndexOutOfBoundsException) {
            return ""
        }

    }
    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()


fun main() {
    val str = readLine()
    if (str != null)
        println(bestHighJump(str))
}
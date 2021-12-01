package com.tours.utils

typealias ErrorMap = Map<String, Map<String, String>>

class ErrorMaper {
    companion object {
        val errorMap: ErrorMap = mapOf(
            "login" to mapOf(
                "validation.min.string" to "Поле 'логин' должно содержать больше 3 букв",
                "validation.required" to "Поле 'логин' должно быть заполнено",
                "login is already taken" to "Такой логин уже занят",
                ),
            "password" to mapOf(
                "validation.min.string" to "Поле 'пароль' должно содержать больше 3 букв",
                "validation.required" to "Поле 'пароль' должно быть заполнено"
            ),
            "firstname" to mapOf(
                "validation.min.string" to "Поле 'Имя' должно содержать больше 3 букв",
                "validation.required" to "Поле 'Имя' должно быть заполнено"
            ),
            "lastname" to mapOf(
                "validation.min.string" to "Поле 'Фамилия' должно содержать больше 3 букв",
                "validation.required" to "Поле 'Фамилия' должно быть заполнено"
            ),
            "auth" to mapOf(
                "Unauthorized" to "Пользователь не найден",
            ),
        )

        fun mapErrors(errors: Map<String, String>): List<String> {

            return errors.map { (key, value) ->
                val type = errorMap[key]
                if (type === null) {
                    return@map "Неизвестная ошибка тип: $key"
                }

                val her = type[value]

                if(her === null){
                    return@map "Неизвестная ошибка тип: $type, подтипа $value"
                }

                return@map her
            }
        }
    }
}
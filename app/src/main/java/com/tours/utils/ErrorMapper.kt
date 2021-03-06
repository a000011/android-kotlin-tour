package com.tours.utils

typealias ErrorMap = Map<String, Map<String, String>>

class ErrorMapper {
    companion object {
        private val errorMap: ErrorMap = mapOf(
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
            "commentContent" to mapOf(
                "validation.required" to "Поле 'Коментарий' должно быть заполнено",
                "validation.min.string" to "Слишком короткий комментарий"
            ),
            "mark" to mapOf(
                "validation.required" to "Поле 'Оценка' должно быть заполнено",
                "validation.max.numeric" to "Поле 'Оценка' не должно быть больше 5",
                "validation.min.numeric" to "Поле 'Оценка' не должно быть меньше 0"
            ),
            "comment" to mapOf(
                "you have already created comment on this tour page" to "Вы уже оставили отзыв на этом туре"
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
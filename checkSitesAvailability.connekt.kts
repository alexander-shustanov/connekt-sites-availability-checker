val chatIdsParam: String by env
val botToken: String by env
val sitesParam: String by env

val chatIds: List<String> = chatIdsParam.split(",").map { it.trim() }
val sites: List<String> = sitesParam.split(",").map { it.trim() }

useCase("check sites availability") {
    for (site in sites) {
        val isAvailable: Boolean = try {
            val response by GET(site)
            response.code == 200
        } catch (e: Exception) {
            false
        }

        if (!isAvailable) {
            println("❌ $site недоступен")

            val messageText = "❗️Сайт <b>$site</b> недоступен"

            for (chatId in chatIds) {
                POST("https://api.telegram.org/bot{botToken}/sendMessage") {
                    pathParam("botToken", botToken)
                    headers("Content-Type" to "application/json")
                    body(
                        """
                        {
                          "chat_id": "$chatId",
                          "text": "$messageText",
                          "parse_mode": "HTML"
                        }
                        """.trimIndent()
                    )
                }
            }
        } else {
            println("✅ $site доступен")
        }
    }
}

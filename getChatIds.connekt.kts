/**
 * Use this script to get chat id. Write message to the bot and then execute the request.
 */

val botToken: String by env

GET("https://api.telegram.org/bot{botToken}/getUpdates") {
    pathParam("botToken", botToken)
} then {
    val ids = jsonPath().readList("$.result[*].message.chat.id", Long::class.java)
    val usernames = jsonPath().readList("$.result[*].message.chat.username", String::class.java)

    val idsWithUserNames = ids.zip(usernames)

    if (idsWithUserNames.isEmpty()) {
        println("No new messages was sent to bot")
    } else {
        println("There are new messages from users:")

        for ((id, username) in idsWithUserNames) {
            println("${username}: ${id}")
        }
    }
}

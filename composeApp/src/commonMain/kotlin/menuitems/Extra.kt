package menuitems

enum class Extra(private val uiString: String, val description: String) {
    SPREAD_PACKET("Spread Packet", "In-N-Out spread, in a packet."),
    PUP_PATTY("Pup Patty", "An unsalted burger patty. For dogs and weirdos. May sometimes be called a \"Scooby Snack\""),
    PEPPER_PACKET("Pepper Packet", "Black pepper. I can't imagine they'll count out an exact number of these?");

    fun itemName(): String {
        return uiString
    }
}
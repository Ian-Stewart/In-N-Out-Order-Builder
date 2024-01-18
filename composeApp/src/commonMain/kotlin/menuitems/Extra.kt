package menuitems

enum class Extra(private val uiString: String, val description: String): CartItem {
    SPREAD_PACKET("Spread Packet", "In-N-Out spread, in a packet."),
    PUP_PATTY("Pup Patty", "An unsalted burger patty. For dogs and weirdos."),
    PEPPER_PACKET("Pepper Packet", "Black pepper. I can't imagine they'll count out an exact number of these?");

    override fun itemName(): String {
        return uiString
    }
}
data class Place(override var name: String, var parent: Place, var items: Array<Thing>): Thing(name)

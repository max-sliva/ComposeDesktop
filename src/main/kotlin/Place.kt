data class Place(override var name: String, var parent: Place? = null, var items: Array<Thing>? = null): Thing(name)

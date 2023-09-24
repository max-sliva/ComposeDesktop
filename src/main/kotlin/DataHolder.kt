/**
 * Class for returning data either from local or network storage
 */
class DataHolder {
    var things: Array<Thing>? = arrayOf(Place("backShelf"), Place("centerTables"))

    fun getData(): Array<Thing>? {


        return things
    }
}
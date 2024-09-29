sealed class LineItem {
    class Number(val number: Int) : LineItem()
    class Command(val name: String, val lineItems: List<LineItem>) : LineItem()
}
class CustomSet(vararg values: Int) {

    private var root: Tree = values.fold(Tree.E) { tree: Tree, value: Int -> tree + value }

    fun isEmpty(): Boolean = root.isEmpty()

    fun isSubset(other: CustomSet): Boolean = this.root.isSubset(other.root)

    fun isDisjoint(other: CustomSet): Boolean = !this.isSubset(other) && !other.isSubset(this)

    fun contains(value: Int): Boolean = this.root.contains(value)

    fun intersection(other: CustomSet): CustomSet {
        TODO("Implement this function to complete the task")
    }

    fun add(other: Int) {
        this.root += other
    }

    override fun equals(other: Any?): Boolean {
        TODO("Implement this function to complete the task")
    }

    operator fun plus(other: CustomSet): CustomSet {
        TODO("Implement this function to complete the task")
    }

    operator fun minus(other: CustomSet): CustomSet {
        TODO("Implement this function to complete the task")
    }
}

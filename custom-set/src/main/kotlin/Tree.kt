sealed class Tree {
    abstract fun isEmpty(): Boolean
    abstract fun contains(value: Int): Boolean
    abstract operator fun plus(tree: Tree): Tree
    abstract fun pop(): Pair<Int, Tree>?
    abstract fun remove(value: Int): Tree
    abstract fun isSubset(other: Tree): Boolean
    abstract fun values(): List<Int>

    internal class T(val left: Tree, val value: Int, val right: Tree) : Tree() {
        override fun isEmpty(): Boolean = false

        override fun contains(value: Int): Boolean = when {
            value < this.value -> this.left.contains(value)
            value > this.value -> this.right.contains(value)
            else -> true
        }

        override fun plus(tree: Tree): Tree = when (tree) {
            is T -> when {
                tree.value > this.value ->
                    T(
                        this.left,
                        this.value,
                        this.right +
                                T(
                                    E,
                                    tree.value,
                                    tree.right
                                )
                    ) +
                            tree.left

                tree.value < this.value ->
                    T(
                        this.left +
                                T(
                                    tree.left,
                                    tree.value,
                                    E
                                ),
                        this.value,
                        this.right
                    ) +
                            tree.right

                else ->
                    T(
                        this.left + tree.left,
                        this.value,
                        this.right + tree.right
                    )
            }

            E -> this
        }

        override fun pop(): Pair<Int, Tree> = Pair(this.value, this.left + this.right)

        override fun remove(value: Int): Tree = when {
            this.value == value -> this.left + this.right
            value < this.value -> this.left.remove(value) + this.right
            else -> this.left + this.right.remove(value)
        }

        override fun isSubset(other: Tree): Boolean = when (other) {
            E -> false
            is T -> {
                val (popped, rest) = this.pop()
                other.contains(popped) && rest.isSubset(other)
            }
        }
    }

    internal object E : Tree() {
        override fun isEmpty(): Boolean = true
        override fun contains(value: Int): Boolean = false
        override operator fun plus(tree: Tree): Tree = tree
        override fun pop(): Pair<Int, Tree>? = null
        override fun remove(value: Int): Tree = E
        override fun isSubset(other: Tree): Boolean = true
    }

    operator fun plus(value: Int): Tree = when (this) {
        E -> T(E, value, E)
        is T -> when {
            value < this.value -> T(this.left + value, this.value, this.right)
            value > this.value -> T(this.left, this.value, this.right + value)
            else -> this
        }
    }
}
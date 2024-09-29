class EmptyBufferException : Exception()

class BufferFullException : Exception()

class CircularBuffer<T>(size: Int) {
    private var list: MutableList<T?> = MutableList(size) { null }
    private var currentIndex = 0
    private fun nextIndex(): Int = (currentIndex + 1) % list.size
    private fun incrementIndex() {
        this.currentIndex = this.nextIndex()
    }

    private var currentValue: T?
        get() = this.list[this.currentIndex]
        set(value) {
            this.list[this.currentIndex] = value
        }


    fun read(): T {
        val value = this.currentValue ?: throw EmptyBufferException()
        this.currentValue = null
        this.incrementIndex()
        return value
    }

    tailrec fun write(value: T, offset: Int = 0) {
        val indexAtOffset = (this.currentIndex + offset) % list.size
        if (this.list[indexAtOffset] == null) {
            this.list[indexAtOffset] = value
        } else if (offset >= list.size) {
            throw BufferFullException()
        } else {
            this.write(value, offset + 1)
        }
    }

    fun overwrite(value: T) = if (this.list.contains(null)) {
        this.write(value)
    } else {
        this.currentValue = value
        this.incrementIndex()
    }


    fun clear() {
        this.list = MutableList(this.list.size) { null }
    }
}
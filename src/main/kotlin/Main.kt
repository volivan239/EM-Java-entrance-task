import java.io.*
import kotlin.random.*
import kotlin.system.measureTimeMillis

fun testIO(nTimes: Int, rng: Random = Random(239), size: Int = 128 * 1024 * 1024) {
    var sm_write: Long = 0
    var sm_read: Long = 0
    repeat(nTimes) {
        val out = FileOutputStream("tmp_file")
        val write_data = rng.nextBytes(size)
        val cur_write = measureTimeMillis {
            out.write(write_data)
            out.channel.force(true)
        }
        out.close()

        val inp = FileInputStream("tmp_file")
        val read_data: ByteArray
        val cur_read = measureTimeMillis {
            read_data = inp.readBytes()
        }
        assert(read_data.contentEquals(write_data))
        inp.close()

        sm_write += cur_write
        sm_read += cur_read
        println("Write = $cur_write, read = $cur_read")
    }
    println("Avg: Write = ${sm_write / nTimes}, Read = ${sm_read / nTimes}")
}

fun main() {
    testIO(10)
}

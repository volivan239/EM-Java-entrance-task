import java.io.*
import kotlin.random.Random
import kotlin.io.path.createTempFile
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

/**
 * @param file File to which test data will be written
 * @param writeSize Size of data (in MB) that will be written/read in each test
 */
class IOSpeedTester(
    private val file: File = createTempFile().toFile(),
    private val writeSize: Int = 256
) {
    private val rng: Random = Random

    /**
     * nTimes times writes and reads random data to/from disk, measuring speed
     * @return results for each attempt: first element is writing speed (in MB/s), second is reading speed (in MB/s)
     */
    fun doTests(nTimes: Int): List<Pair<Int, Int>> = List(nTimes) {
        val writeData = rng.nextBytes(writeSize * 1024 * 1024)

        val out = FileOutputStream(file)
        val writeTime = 0.001 * measureTimeMillis {
            out.write(writeData)
            out.channel.force(true)
        }
        out.close()

        val inp = FileInputStream(file)
        val readData: ByteArray
        val readTime = 0.001 * measureTimeMillis {
            readData = inp.readBytes()
        }
        assert(readData.contentEquals(writeData))
        inp.close()

        Pair((writeSize / writeTime).roundToInt(), (writeSize / readTime).roundToInt())
    }
}
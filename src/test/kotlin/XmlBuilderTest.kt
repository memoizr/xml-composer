import com.memoizr.assertk.expect
import org.junit.Test

class XmlBuilderTest {
    @Test
    fun `creates an empty tag`() {
        val document = document {
            "div" {

            }
        }

        expect that document isEqualToIgnoringWhitespace """<div/>"""
    }

    @Test
    fun `creates a tag with content`() {
        val document = document {
            "div"("Hello, World!")
        }
        expect that document isEqualToIgnoringWhitespace """<div>Hello, World!</div>"""
    }

    @Test
    fun `creates a tag with nested tags`() {
        val document = document {
            "div" {
                "p" {

                }
            }
        }
        expect that document isEqualToIgnoringWhitespace """<div>
        <p/>
        </div>"""
    }
}
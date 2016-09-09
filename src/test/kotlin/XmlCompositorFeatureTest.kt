import com.memoizr.assertk.expect
import org.junit.Test

class XmlGeneratorFeatureTest {
    @Test
    fun `valid xml can be generated using builders`() {
        val doc: String = document {
            "html" {
                "body" {
                    "div" {
                        "p"("content")
                    }
                }
            }
        }
        val expected =
                """<html>
                    <body>
                        <div>
                            <p>content</p>
                        </div>
                    </body>
        </html>"""

        expect that doc isEqualToIgnoringWhitespace expected
    }
}

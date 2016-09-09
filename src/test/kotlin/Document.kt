import com.sun.org.apache.xml.internal.serialize.OutputFormat
import com.sun.org.apache.xml.internal.serialize.XMLSerializer
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory


fun document(content: DocumentBuilder.() -> Unit): String {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
    val document = documentBuilderFactory.newDocumentBuilder().newDocument()
    val documentBuilder = DocumentBuilder(document)
    documentBuilder.content()
    return documentBuilder.toString()
}

class DocumentBuilder(private val document: Document) {

    operator fun String.invoke(content: ElementBuilder.() -> Unit) {
        val element = document.createElement(this)
        val elementBuilder = ElementBuilder(document, element)
        elementBuilder.content()
        document.appendChild(element)
    }

    class ElementBuilder(private val document: Document, private val element: Element) {
        operator fun String.invoke(content: ElementBuilder.() -> Unit) {
            val newElement = document.createElement(this)
            val elementBuilder = ElementBuilder(document, newElement)
            elementBuilder.content()
            element.appendChild(newElement)
        }

        operator fun String.invoke(content: String) {
            val newElement = document.createElement(this)
            val attribute = document.createTextNode(content)
            newElement.appendChild(attribute)
            element.appendChild(newElement)
        }
    }

    operator fun String.invoke(content: String) {
        val element = document.createElement(this)
        val attribute = document.createTextNode(content)
        element.appendChild(attribute)
        document.appendChild(element)
    }

    override fun toString(): String {
        val format = OutputFormat(document);
        format.setIndenting(true);
        format.omitXMLDeclaration = true
        val stringWriter = StringWriter();
        val serializer = XMLSerializer(stringWriter, format);
        serializer.serialize(document);

        return stringWriter.toString();
    }
}

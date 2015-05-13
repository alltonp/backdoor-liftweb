package im.mange.backdoor

import com.twitter.chill.ScalaKryoInstantiator
import im.mange.backdoor.serialiser.LocalDateSerializer
import org.joda.time.LocalDate

object Cryopreservation {
  import java.util.Base64

  import net.liftweb.json.Serialization._
  import net.liftweb.json._

  private val kryo = RegisteringKryoPool.withByteArrayOutputStream(10,
    new ScalaKryoInstantiator(),
    Seq(ClassAndSerializer(classOf[LocalDate], new LocalDateSerializer()))
  )

  def freeze(thing: Any): String = {
    val bytes = kryo.toBytesWithClass(thing)
    val base64Encoded = Base64.getEncoder.encodeToString(bytes)
    val message = BackdoorMessage(base64Encoded)

    implicit val formats = Serialization.formats(NoTypeHints)
    pretty(render(JsonParser.parse(write(message))))
  }

  def thaw(json: String): Any = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val reloaded = parse(json).extract[BackdoorMessage]
    kryo.fromBytes(Base64.getDecoder.decode(reloaded.data))
  }
}

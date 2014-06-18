package net.room271

import akka.actor.{Props, ActorLogging, Actor}
import akka.io.Tcp._
import akka.util.ByteString

object Handler {

  def props(handler: Request => Response): Props = Props(new Handler(handler))
}

class Handler(handler: Request => Response) extends Actor with ActorLogging {

  def receive = {
    case Received(data) =>
      log.debug("Received! - {}", data)
      sender ! handleRequest(data)
      sender ! Close
    case PeerClosed => context stop self
  }

  def handleRequest(data: ByteString): ByteString = {
    val request = HttpParser.parse(data)
    val response = request.map(handler)
      .getOrElse(Response(400, Map(), None))

    asByteString(response)
  }

  def asByteString(response: Response): ByteString = {
    val CRLF = "\r\n"
    val statusLine = s"HTTP/1.1 ${response.status} Success"
    val headers = response.headers
      .map { case (key, value) => s"$key:$value$CRLF" }
      .mkString
    val body = response.body.getOrElse("")

    ByteString.fromString(statusLine + CRLF + headers + CRLF + body)
  }
}

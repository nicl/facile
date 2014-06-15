package net.room271

import akka.actor.{ActorLogging, Actor}
import akka.io.Tcp._

class Handler extends Actor with ActorLogging {

  def receive = {
    case Received(data) =>
      log.debug("Received! - {}", data)
      sender ! Write(data)
      sender ! Close
    case PeerClosed => context stop self
  }
}

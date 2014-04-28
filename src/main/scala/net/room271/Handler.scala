package net.room271

import akka.actor.Actor
import akka.io.Tcp

class Handler extends Actor {

  import Tcp._

  def receive = {
    case Received(data) => sender ! Write(data)
    case PeerClosed => context stop self
  }
}

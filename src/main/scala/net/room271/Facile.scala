package net.room271

import akka.actor.{Props, ActorSystem}
import akka.io.{IO, Tcp}

object Facile extends App {

  val system = ActorSystem("facile-service")
  val manager = IO(Tcp)
  val handler = system.actorOf(Props[Handler])

  val server = system.actorOf(Server.props(handler))
}

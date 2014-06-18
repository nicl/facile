package net.room271

import akka.actor.{ActorLogging, ActorRef, Props, Actor}
import akka.io.{IO, Tcp}
import java.net.InetSocketAddress
import Tcp._

object Server {

  def props(handler: ActorRef): Props = Props(new Server(handler))
}

class Server(handler: ActorRef) extends Actor with ActorLogging {

  import context.system

  log.debug("Binding!")
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9000))

  def receive = {
    case b @ Bound(localAddress) =>
      log.debug("Bound.")

    case CommandFailed(_) =>
      log.debug("Command failed.")
      context stop self

    case c @ Connected(remote, local) =>
      log.debug("Connected.")
      val connection = sender()
      connection ! Register(handler)
  }
}
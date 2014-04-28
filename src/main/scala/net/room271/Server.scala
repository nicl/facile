package net.room271

import akka.actor.{ActorRef, Props, Actor}
import akka.io.{IO, Tcp}
import java.net.InetSocketAddress

class Server(handler: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9000))

  def receive = {
    case b @ Bound(localAddress) =>
      println(s"Bound $localAddress !!!")

    case CommandFailed(_: Bind) => context stop self

    case c @ Connected(remote, local) =>
      val handler = context.actorOf(Props[Handler])
      val connection = sender()
      connection ! Register(handler)
  }
}

object Server {

  def props(handler: ActorRef): Props = Props(classOf[Handler])
}
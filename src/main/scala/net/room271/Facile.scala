package net.room271

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

object Facile extends App {

  val config = ConfigFactory.load()
  val system = ActorSystem("facile-service", config)

  val handler = system.actorOf(Props[Handler])
  system.actorOf(Server.props(handler))

  readLine(s"Hit ENTER to exit ...${System.getProperty("line.separator")}")
  system.shutdown()
}

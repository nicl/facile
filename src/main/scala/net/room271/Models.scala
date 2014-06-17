package net.room271

import java.security.cert.X509Certificate

case class Request(
  serverPort: Int,
  serverName: String,
  remoteAddr: String,
  uri: String,
  queryString: Option[String],
  scheme: Scheme,
  requestMethod: RequestMethod,
  sslClientCert: Option[X509Certificate],
  headers: Map[String, String],
  body: Option[String])

case class Response(
  status: Int,
  headers: Map[String, String],
  body: Option[String])

sealed trait RequestMethod
case object Get extends RequestMethod
case object Head extends RequestMethod
case object Post extends RequestMethod
case object Put extends RequestMethod
case object Delete extends RequestMethod
case object Trace extends RequestMethod
case object Options extends RequestMethod
case object Patch extends RequestMethod

sealed trait Scheme
case object Http extends Scheme
case object Https extends Scheme
package net.room271

import java.io.InputStream
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
  body: Option[InputStream])

case class Response(
  status: Int,
  headers: Map[String, String],
  body: Option[InputStream])

sealed trait RequestMethod
case object Get
case object Head
case object Post
case object Put
case object Delete
case object Trace
case object Options
case object Patch

sealed trait Scheme
case object Http
case object Https

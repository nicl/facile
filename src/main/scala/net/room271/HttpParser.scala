package net.room271

import akka.util.ByteString
import scala.util.parsing.combinator.RegexParsers

object HttpParser extends RegexParsers {

  val stringLiteral = (s"[^$CRLF]").r
  val nonSpaceString = "[^ ]*".r
  val CRLF = "\r\n"

  // See: http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5
  //
  // An example request:
  //   GET / HTTP/1.1
  //   User-Agent: curl/7.30.0
  //   Host: localhost:9000
  //   Accept: */*
  //
  // Generally CRLF (escaped in Scala as: '\r\n') is used to separate parts
  def parse(input: ByteString): Request = {
    input.to


    ???
  }

  def request: Parser[Request] = requestLine ~ CRLF ~ opt(headers) ~ CRLF ~ opt(body)

  def requestLine: Parser[RequestLine] = method ~ uri ~ version

  def method: Parser[RequestMethod] = get | post

  def get: Parser[Get] = "GET" ^^ { _ => Get }

  def post: Parser[Post] = "POST" ^^ { _ => Post }

  def uri: Parser[String] = nonSpaceString

  def version: Parser[String] = nonSpaceString

  def headers: Parser[Map[String, String]] = rep(header) ^^ { _.toMap }

  def header: Parser[(String, String)] = nonSpaceString ~ ":" ~ nonSpaceString ^^ {
    case key ~ _ ~ value => (key, value)
  }

  def body: Parser[String] =

  case class RequestLine(method: RequestMethod, uri: String, version: String)
}





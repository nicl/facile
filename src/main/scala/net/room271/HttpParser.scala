package net.room271

import akka.util.ByteString
import scala.util.parsing.combinator.RegexParsers
import java.io.InputStreamReader

object HttpParser extends RegexParsers {

  val stringLiteral = s"[^$CRLF]".r
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
  def parse(input: ByteString): ParseResult[Request] = {
    parse(request, new InputStreamReader(input.iterator.asInputStream))
  }

  def request: Parser[Request] = requestLine ~ opt(headers) ~ CRLF ~ opt(body) ^^ {
    case r ~ h ~ _ ~ b => Request(r, h.getOrElse(Map.empty), b)
  }

  def requestLine: Parser[RequestLine] = method ~ uri ~ version <~ CRLF ^^ {
    case m ~ u ~ v => RequestLine(m, u, v)
  }

  def method: Parser[RequestMethod] = get | post

  def get: Parser[RequestMethod] = "GET" ^^ { _ => Get }

  def post: Parser[RequestMethod] = "POST" ^^ { _ => Post }

  def uri: Parser[String] = nonSpaceString

  def version: Parser[String] = nonSpaceString

  def headers: Parser[Map[String, String]] = rep(header) ^^ { _.toMap }

  def header: Parser[(String, String)] = nonSpaceString ~ ":" ~ nonSpaceString ^^ {
    case k ~ _ ~ v => (k, v)
  }

  def body: Parser[String] = ".*".r
}





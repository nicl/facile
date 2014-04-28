Facile
======

A simple HTTP routing layer written in Scala.

## Design principles

* lightly typesafe
* performant
* simple
* easy to extend

Inspired by Clojure libraries (specifically Ring and Compojure),
Facile aims to be a small and lightweight HTTP routing layer that is
easy to use and extend.

### Performant

Facile is built upon Akka IO - an actor-based, asynchronous model. An
actor is created for each request. Actors are lightweight constructs,
and an actor-based approach stands in contrast to thread-based systems.

### Easy to extend

Facile mirrors the Clojure Ring library in that it is designed to be
built upon. While it is possible to use it on its own, the expectation
is that people will build abstraction layers on top of it which
simplify the creation of handlers, and so on.

### Lightly typesafe

Typesafety is a great feature and it should be taken advantage of in a
language with a rich type system like Scala. However, wrapping
everything in specific types leads to code bloat and often makes it
harder to explore and interact with code. Facile aims to wrap HTTP
*lightly*, providing a sensible layer of typesafety without going
overboard. Sometimes a plain String is good enough.

For example, headers are made accessible as:
`Map[String, String]`. See the 'Simple' section below for fuller
examples.

### Simple

Simple means not complex. As such, Facile does just one thing: match
HTTP requests to responses using a few abstractions:

* handlers (ordinary functions which accept a request and return a response)
* middleware (functions which return handlers)

A request is defined as:

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
  body: Option[InputSteam]
)

And a response is simply:

case class Response(
  status: Int,
  headers: Map[String, String],
  body: Option[{String, InputStream}]
)

package org.akkajs

import scala.scalajs.js

import akka.actor._

class WSTwitterChannelActor(connection: js.Dynamic) extends Actor {
  import TwitterMsgs._

  override def preStart() = {
    val twitterActor = context.actorOf(Props[TwitterActor])

    connection.on("message", (message: js.Dynamic) => {
      twitterActor ! Track(message.utf8Data.toString)
    })

    connection.on("close", (reasonCode: js.Dynamic, description: js.Dynamic) => {
      self ! PoisonPill
    })
  }

  def receive = {
    case tweet: String =>
      connection.send(tweet)
  }
}

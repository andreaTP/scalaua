package org.akkajs

import scala.scalajs.js

import akka.actor._

object Main extends js.JSApp {

  def main() = {

    implicit val system = ActorSystem("ui-akkajs")

    val ui = system.actorOf(Props(new UIActor()))

  }

}

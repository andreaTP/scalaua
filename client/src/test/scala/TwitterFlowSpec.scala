package org.akkajs

import org.scalatest._
import akka.actor._
import akka.testkit._
import scala.scalajs.js
import akka.stream._
import akka.stream.scaladsl._
import akka.stream.testkit.scaladsl._

class TwitterFlowSpec extends TestKit(ActorSystem("WSCSpec"))
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  def composeTweet(name: String, text: String) = {
    (
      TwitterMsgs.Tweet(name, text),
      js.JSON.stringify(
        js.Dynamic.literal(
          "user" -> js.Dynamic.literal("name" -> name),
          "text" -> text)
      )
    )
  }

  "A Twitter Flow" must {

    "parse incoming tweets and emit valid ones" in {
      import system.dispatcher
      implicit val materializer = ActorMaterializer()

      val (pub, sub) = TestSource.probe[String]
        .via(TwitterFlow())
        .toMat(TestSink.probe[TwitterMsgs.Tweet])(Keep.both)
        .run()

      sub.request(n = 2)

      val (tweet1, tweet1Str) = composeTweet("foo", "bar")
      pub.sendNext(tweet1Str)

      pub.sendNext("{unparsableMessage}")

      val (tweet2, tweet2Str) = composeTweet("bar", "baz")
      pub.sendNext(tweet2Str)

      sub.expectNextUnordered(tweet1, tweet2)
    }
  }
}

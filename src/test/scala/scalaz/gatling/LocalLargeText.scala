package scalaz.gatling

import scalaz.effect.benchmark.model.Url

abstract class LocalLargeText(url: Url, atOnce: Int, constant: Int, ramp: Int)
    extends AbstractLoadTest(url, "/largetext", atOnce, constant, ramp)

abstract class RemoteLargeText(url: Url, atOnce: Int, constant: Int, ramp: Int)
    extends AbstractLoadTest(url, "/largetext/stream", atOnce, constant, ramp)

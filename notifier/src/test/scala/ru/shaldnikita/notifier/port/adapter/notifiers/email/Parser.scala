package ru.shaldnikita.notifier.port.adapter.notifiers.email

import java.io.{File, FileInputStream, FileOutputStream}
import java.net.URI
import java.nio.file.{Files, Path}

import akka.actor.ActorSystem
import akka.http.javadsl.model.RequestEntity
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{headers, _}
import akka.http.scaladsl.model.headers._
import akka.stream.ActorMaterializer
import ru.shaldnikita.notifier.SpecBase

import scala.collection.parallel.immutable
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 28.04.2019
  */
class Parser extends SpecBase{
  implicit val system: ActorSystem = ActorSystem("reminder")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  "asd" should "asd" in {
    val headers: scala.collection.immutable.Seq[HttpHeader] = scala.collection.immutable.Seq(
      new Cookie(scala.collection.immutable.Seq(
        HttpCookiePair(("SID"->"5bb2cp0dch0c5jdraeba1k36as893oay")),
        HttpCookiePair(("phonecodes"->"1")),
        HttpCookiePair(("cto_lwid"->"5d83b81f-c8ed-4cba-85db-adf1393b516b")),
        HttpCookiePair(("sw_metrics"->"1556455847066")),
        HttpCookiePair(("sw_subscr"->"1")),
        HttpCookiePair(("_ym_uid"->"1556455847255417160")),
        HttpCookiePair(("_ym_d"->"1556455847")),
        HttpCookiePair(("_ym_isad"->"1")),
        HttpCookiePair(("_fbp"->"fb.1.1556455847321.1197357950")),
        HttpCookiePair(("_ga"->"GA1.2.1486051169.1556455847")),
        HttpCookiePair(("_gid"->"GA1.2.777129759.1556455847")),
        HttpCookiePair(("__utmc"->"89133446")),
        HttpCookiePair(("last_viewed"->"11823024")),
        HttpCookiePair(("mrc"->"app_id%3D611986%26is_app_user%3D0%26window_id%3DCometName_4d143816ef65aa307abef8dd3d4aea79")),
        HttpCookiePair(("BSK_LAST"->"0")),
        HttpCookiePair(("LKS_LAST"->"0")),
        HttpCookiePair(("__utma"->"89133446.1486051169.1556455847.1556458387.1556464145.3")),
        HttpCookiePair(("__zlcmid"->"s2iCF5x3FaNZUy")),
        HttpCookiePair(("__zlcprivacy"->"1")),
        HttpCookiePair(("__atuvc"->"7%7C18")),
        HttpCookiePair(("no_redirect"->"1")),
        HttpCookiePair(("tmr_detect"->"1%7C1556465413930"))
      )))
    val res = Future.traverse(scala.Range(209, 210).toList) { number =>
      Http().singleRequest(HttpRequest(
        HttpMethods.GET,
        Uri(s"https://www.litres.ru/pages/get_pdf_page/?file=14859925&page=$number&rt=w1900&ft=gif"),
        headers
      )).flatMap { response =>
        response.entity.toStrict(5 seconds).map(_.data)
          .map(_.toByteBuffer)
          .map(bb => {
            val file = new File(s"files/page$number.jpg")
            val fos = new FileOutputStream(file)
            fos.write(bb.array())
            fos.close()
          })

        // print(response)
        /*val ref = response.getHeader("Location").get().value()
        println(ref)*/
      }
    }
    Thread.sleep(100000)


  }
}

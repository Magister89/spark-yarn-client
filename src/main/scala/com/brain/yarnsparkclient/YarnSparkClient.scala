package com.brain.yarnsparkclient

import java.security.PrivilegedExceptionAction

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.hadoop.yarn.api.records.ApplicationReport
import org.apache.hadoop.yarn.client.api.YarnClient
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.JavaConverters._

class YarnSparkClient(val conf: Configuration,
                      val ugi: UserGroupInformation,
                      spark: SparkSession) {

  val client = YarnClient.createYarnClient()

  def init() = client.init(conf)
  private def start() = client.start()
  private def stop() = client.stop()

  def getApplications(): Dataset[YarnApp] = {

    val apps = ugi.doAs(new PrivilegedExceptionAction[List[ApplicationReport]] {
      def run(): List[ApplicationReport] = {
        start()
        val apps = client.getApplications().asScala.toList
        stop()
        apps
      }
    } )

    import spark.implicits._
    apps.map(x =>
      YarnApp(
        x.getApplicationId.toString,
        x.getName,
        x.getUser(),
        x.getYarnApplicationState.toString)).toDS()
  }

}

package com.brain.yarnsparkclient

import java.security.PrivilegedAction
import java.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.hadoop.yarn.api.records.ApplicationReport
import org.apache.hadoop.yarn.client.api.YarnClient
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.JavaConverters._

class YarnKerberosClient(val conf: Configuration,
                         val ugi: UserGroupInformation,
                         sparkSession: SparkSession) extends Client[YarnApp]
  with Mapping[YarnApp, ApplicationReport] {

  val client: YarnClient = YarnClient.createYarnClient()

  def init(): Unit = client.init(conf)
  def start(): Unit = execute(ugi, createAction({ _ => client.start()}))
  def stop(): Unit = execute(ugi, createAction({ _ => client.stop()}))

  def map(apps: List[ApplicationReport]): Dataset[YarnApp] = {

    import sparkSession.implicits._
    apps.map(x =>
      YarnApp(
        x.getApplicationId.toString,
        x.getName,
        x.getUser,
        x.getYarnApplicationState.toString)).toDS()
  }

  def setToHashSet[T](set: Set[T]): util.HashSet[T] = {
    val hashTypes = new util.HashSet[T]
    set.foreach(x => hashTypes.add(x))
    hashTypes
  }

  def execute[T](ugi: UserGroupInformation, action: PrivilegedAction[T]): T = {
    ugi.doAs(action)
  }

  def createAction[T](methodCall: Unit => T): PrivilegedAction[T] = {
    new PrivilegedAction[T] {
      override def run(): T = {
        methodCall.apply()
      }
    }
  }

  def getApplications: Dataset[YarnApp] =
    map(execute(ugi, createAction({ _ => client.getApplications.asScala.toList })))

  def getApplications(types: Set[String]): Dataset[YarnApp] =
    map(execute(ugi, createAction({ _ => client.getApplications(setToHashSet(types)).asScala.toList })))


}

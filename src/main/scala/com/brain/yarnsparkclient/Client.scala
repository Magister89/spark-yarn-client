package com.brain.yarnsparkclient

import java.security.PrivilegedAction

import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.sql.{Dataset, SparkSession}

trait Client[A] {

  def init(): Unit
  def start(): Unit
  def stop(): Unit
  def execute[T](ugi: UserGroupInformation, action: PrivilegedAction[T]): T
  def createAction[T](methodCall: Unit => T): PrivilegedAction[T]
  def getApplications: Dataset[A]
  def getApplications(types: Set[String]): Dataset[A]

}

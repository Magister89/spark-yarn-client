package com.brain.yarnsparkclient

import org.apache.spark.sql.{Dataset, SparkSession}
import java.util

trait Mapping[A, B] {
  def map(obj: List[B]): Dataset[A]
  def setToHashSet[T](set: Set[T]): util.HashSet[T]
}

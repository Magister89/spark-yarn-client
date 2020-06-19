package com.brain.yarnsparkclient

import java.io.{File, FileInputStream}

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.WildcardFileFilter
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkFiles
import scala.collection.JavaConversions._

class YarnConfiguration extends Configuration {

  def init() = {

    val dir = new File(SparkFiles.getRootDirectory())
    val files = FileUtils.listFiles(dir, new WildcardFileFilter("*.xml"), null)
    for (file <- files) {
      addResource(new FileInputStream(file))
    }
  }
}

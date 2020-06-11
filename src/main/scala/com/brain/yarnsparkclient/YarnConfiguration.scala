package com.brain.yarnsparkclient

import java.io.{File, FileInputStream}

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.WildcardFileFilter
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkFiles

class YarnConfiguration extends Configuration {

  def init() = {

    val dir = new File(SparkFiles.getRootDirectory())
    val files = FileUtils.listFiles(dir, new WildcardFileFilter("*.xml"), null)
    files.forEach(file => {
      val in = new FileInputStream(file)
      addResource(in)
    })
  }
}

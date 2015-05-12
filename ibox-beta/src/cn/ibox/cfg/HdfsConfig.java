package cn.ibox.cfg;

import ibox.util.properties.PropertiesContent;

import org.apache.hadoop.mapred.JobConf;

public class HdfsConfig {
	public static String getHdfsPath(){
		return PropertiesContent.get("hdfsPath");
	}
	public static JobConf config(){
		JobConf conf=new JobConf();
		conf.addResource("classPath:/core-site.xml");
		conf.addResource("classPath:/hdfs-site.xml");
		conf.addResource("classPath:/mapred-site.xml");
		return conf;
		
	}
	

}

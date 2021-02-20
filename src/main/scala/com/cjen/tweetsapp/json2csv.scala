package tweetsapp

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Column
import org.apache.spark.sql.types.StringType

object json2csv {
    
    def run(spark: SparkSession, filename: String, dirname: String): Unit = {
        import spark.implicits._

        //val stringify = udf((vs: Seq[String]) => vs match {
        //    case null => null
        //    case _    => s"""[${vs.mkString(",")}]"""
        //})

        val df = spark.read.json(filename)
        .withColumn("index", $"index".cast(IntegerType))
        .withColumn("Hr", $"Hr".cast(IntegerType))
        .withColumn("Min", $"Min".cast(IntegerType))
        .withColumn("Sec", $"Sec".cast(IntegerType))
        .withColumn("Retweets", $"Retweets".cast(IntegerType))
        .withColumn("Replies", $"Replies".cast(IntegerType))
        .withColumn("Likes", $"Likes".cast(IntegerType))
        .withColumn("Quotes", $"Quotes".cast(IntegerType))
        .withColumn("Place", $"Place".cast(StringType))

        df.show()

        df.printSchema()   

        val dumpCSV = df.write.csv(dirname)

    }

    //def stringify(c: Column) = concat(lit("["), concat_ws(",", c), lit("]")) 
    //https://stackoverflow.com/questions/40426106/spark-2-0-x-dump-a-csv-file-from-a-dataframe-containing-one-array-of-type-string

}


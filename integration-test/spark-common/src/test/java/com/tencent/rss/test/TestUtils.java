/*
 * Tencent is pleased to support the open source community by making
 * Firestorm-Spark remote shuffle server available. 
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * https://opensource.org/licenses/Apache-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.rss.test;

import com.google.common.collect.Lists;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

public class TestUtils {
  private TestUtils() {

  }
  static JavaPairRDD<String, Integer> getRDD(JavaSparkContext jsc) {
    JavaPairRDD<String, Integer> javaPairRDD1 = jsc.parallelizePairs(Lists.newArrayList(
        new Tuple2<>("cat1", 11), new Tuple2<>("dog", 22),
        new Tuple2<>("cat", 33), new Tuple2<>("pig", 44),
        new Tuple2<>("duck", 55), new Tuple2<>("cat", 66)), 2);
    return javaPairRDD1;
  }

  static JavaPairRDD<String, Tuple2<Integer, Integer>> combineByKeyRDD(JavaPairRDD javaPairRDD1) {
    JavaPairRDD<String, Tuple2<Integer, Integer>> javaPairRDD = javaPairRDD1
        .combineByKey((Function<Integer, Tuple2<Integer, Integer>>) i -> new Tuple2<>(1, i),
            (Function2<Tuple2<Integer, Integer>, Integer, Tuple2<Integer, Integer>>) (tuple, i) ->
                new Tuple2<>(tuple._1 + 1, tuple._2 + i),
            (Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>) (tuple1, tuple2) ->
                new Tuple2<>(tuple1._1 + tuple2._1, tuple1._2 + tuple2._2)
        );
    return javaPairRDD;
  }
}

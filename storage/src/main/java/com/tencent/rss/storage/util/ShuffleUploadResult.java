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

package com.tencent.rss.storage.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ShuffleUploadResult {
  private String shuffleKey;
  private long size;
  private List<Integer> partitions;

  public ShuffleUploadResult() {
    this.size = 0;
    partitions = null;
  }

  public ShuffleUploadResult(long size, List<Integer> partitions) {
    this.size = size;
    this.partitions = partitions;
  }

  public String getShuffleKey() {
    return shuffleKey;
  }

  public void setShuffleKey(String shuffleKey) {
    this.shuffleKey = shuffleKey;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public List<Integer> getPartitions() {
    return partitions;
  }

  public void setPartitions(List<Integer> partitions) {
    this.partitions = partitions;
  }

  public static ShuffleUploadResult merge(List<ShuffleUploadResult> results) {
    if (results == null || results.isEmpty()) {
      return null;
    }

    long size = results.stream().map(ShuffleUploadResult::getSize).reduce(0L, Long::sum);
    List<Integer> partitions = results
        .stream()
        .map(ShuffleUploadResult::getPartitions)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    return new ShuffleUploadResult(size, partitions);
  }

}

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

package com.tencent.rss.common;

public class BufferSegment {

  private long blockId;
  private long offset;
  private int length;
  private int uncompressLength;
  private long crc;
  private long taskAttemptId;

  public BufferSegment(long blockId, long offset, int length, int uncompressLength, long crc, long taskAttemptId) {
    this.blockId = blockId;
    this.offset = offset;
    this.length = length;
    this.uncompressLength = uncompressLength;
    this.crc = crc;
    this.taskAttemptId = taskAttemptId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BufferSegment) {
      return blockId == ((BufferSegment) obj).getBlockId()
          && offset == ((BufferSegment) obj).getOffset()
          && length == ((BufferSegment) obj).getLength()
          && uncompressLength == ((BufferSegment) obj).getUncompressLength()
          && crc == ((BufferSegment) obj).getCrc()
          && taskAttemptId == ((BufferSegment) obj).getTaskAttemptId();
    }
    return false;
  }

  @Override
  public String toString() {
    return "BufferSegment{blockId[" + blockId + "], taskAttemptId[" + taskAttemptId
        + "], offset[" + offset + "], length[" + length + "], crc[" + crc
        + "], uncompressLength[" + uncompressLength + "]}";
  }

  public int getOffset() {
    if (offset > Integer.MAX_VALUE) {
      throw new RuntimeException("Unsupported offset[" + offset + "] for BufferSegment");
    }
    return (int) offset;
  }

  public int getLength() {
    return length;
  }

  public long getBlockId() {
    return blockId;
  }

  public long getCrc() {
    return crc;
  }

  public int getUncompressLength() {
    return uncompressLength;
  }

  public long getTaskAttemptId() {
    return taskAttemptId;
  }
}

/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.examples.tutorials.impl;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class SalaryReducerFactory
        implements ReducerFactory<String, SalaryTuple, Integer> {

    @Override
    public Reducer<String, SalaryTuple, Integer> newReducer(String key) {
        return new SalaryReducer();
    }

    private static class SalaryReducer
            extends Reducer<String, SalaryTuple, Integer> {

        private volatile int count;
        private volatile int amount;

        @Override
        public void reduce(SalaryTuple value) {
            count += value.getCount();
            amount += value.getAmount();
        }

        @Override
        public Integer finalizeReduce() {
            return (int) ((double) amount / count);
        }
    }
}
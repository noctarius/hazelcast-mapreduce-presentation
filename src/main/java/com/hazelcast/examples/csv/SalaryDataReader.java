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

package com.hazelcast.examples.csv;

import com.hazelcast.examples.model.SalaryMonth;
import com.hazelcast.examples.model.SalaryYear;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class SalaryDataReader
        implements DataReader<SalaryYear> {

    @Override
    public List<SalaryYear> read(InputStream is)
            throws Exception {

        List<SalaryYear> elements = new ArrayList<>();
        try (ICsvListReader reader = new CsvListReader(new InputStreamReader(is), CsvPreference.EXCEL_PREFERENCE)) {
            reader.getHeader(true);

            List<String> element;
            while ((element = reader.read()) != null) {
                String[] tokens = element.toArray(new String[element.size()]);

                SalaryYear salaryYear = new SalaryYear();
                salaryYear.setEmail(tokens[0]);
                salaryYear.setYear(2013);

                List<SalaryMonth> months = new ArrayList<>(12);
                for (int i = 1; i < 13; i++) {
                    months.add(buildSalaryMonth(i, tokens[i]));
                }

                salaryYear.setMonths(months);
                elements.add(salaryYear);
            }
        }
        return elements;
    }

    private SalaryMonth buildSalaryMonth(int month, String token) {
        SalaryMonth salaryMonth = new SalaryMonth();
        salaryMonth.setSalary(Integer.parseInt(token));
        salaryMonth.setMonth(Month.of(month));
        return salaryMonth;
    }
}

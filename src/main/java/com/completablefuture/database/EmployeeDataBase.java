package com.completablefuture.database;

import com.completablefuture.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EmployeeDataBase {
    public static List<Employee> fetchEmployee() {
        try {
            return new ObjectMapper()
                    .readValue(new File("employees.json"), new TypeReference<List<Employee>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

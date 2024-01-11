package com.test.socket.main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainClass {

    public static void writeToExcel(Map<String, Integer> dataMap, String outputFilePath) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            Sheet sheet = workbook.createSheet("Data");

            int rowNumber = 0;
            for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
                Row row = sheet.createRow(rowNumber++);
                Cell cellValue = row.createCell(0);
                Cell cellQuantity = row.createCell(1);

                cellValue.setCellValue(entry.getKey());
                cellQuantity.setCellValue(entry.getValue());
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the Excel file: " + e.getMessage());
        }
    }
    public static Map<String, Integer> readFileAndStore(String filePath) {
        Map<String, Integer> dataMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ",2);

                String[] values = (columns[0]).split("\\.");
                String value=values[1].substring(2);


                    String[] params = columns[1].split("[\\s@&.?$+-]+");
                    int n = params.length;
                    Integer s = Integer.parseInt(params[n-3]);



                // Store values in the HashMap
                dataMap.put(value, s);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        return dataMap;
    }

    public static void main(String[] args) {
        String filePath = "count.txt";
        Map<String, Integer> result = readFileAndStore(filePath);

        // Example usage: Print the values stored in the HashMap
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println("Value: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
        writeToExcel(result, "output.xlsx");
    }

}

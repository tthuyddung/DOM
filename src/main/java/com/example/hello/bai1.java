package com.example.hello;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class bai1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập đường dẫn thư mục: ");
        String directoryPath = scanner.nextLine();
        scanner.close();

        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            try {
                String xml = generateXML(directory);
                System.out.println(xml);
                saveXMLToFile(xml, "directory_tree.xml");
                System.out.println("Đã lưu cây thư mục vào tệp tin directory_tree.xml thành công!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Đường dẫn thư mục không hợp lệ!");
        }
    }

    public static String generateXML(File directory) {
        StringBuilder xml = new StringBuilder();

        xml.append("<").append(directory.getName()).append(">");
        xml.append(System.lineSeparator());

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    xml.append(generateXML(file));
                } else {
                    xml.append("<file>").append(file.getName()).append("</file>");
                    xml.append(System.lineSeparator());
                }
            }
        }

        xml.append("</").append(directory.getName()).append(">");
        xml.append(System.lineSeparator());

        return xml.toString();
    }

    public static void saveXMLToFile(String xml, String filePath) throws IOException {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.write(System.lineSeparator());
            writer.write(xml);
        }
    }
}
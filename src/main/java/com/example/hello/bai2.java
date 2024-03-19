package com.example.hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class bai2 {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Nguyễn Văn A", 20, 3.5));
        students.add(new Student("Trần Thị B", 19, 3.2));
        students.add(new Student("Lê Hoàng C", 21, 3.8));

        exportToXML(students, "student_list.xml");
    }

    public static void exportToXML(List<Student> students, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);

            for (Student student : students) {
                Element studentElement = doc.createElement("student");
                rootElement.appendChild(studentElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(student.getName()));
                studentElement.appendChild(nameElement);

                Element ageElement = doc.createElement("age");
                ageElement.appendChild(doc.createTextNode(String.valueOf(student.getAge())));
                studentElement.appendChild(ageElement);

                Element gpaElement = doc.createElement("gpa");
                gpaElement.appendChild(doc.createTextNode(String.valueOf(student.getGpa())));
                studentElement.appendChild(gpaElement);
            }

            // Tạo một trình tạo XML và thiết lập định dạng đầu ra
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");

            // Tạo nguồn dữ liệu từ tài liệu XML
            DOMSource source = new DOMSource(doc);

            // Tạo luồng đầu ra dưới dạng tệp tin
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            StreamResult result = new StreamResult(fos);

            // Xuất XML vào tệp tin
            transformer.transform(source, result);

            System.out.println("Đã xuất danh sách sinh viên vào tệp tin XML thành công!");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static class Student {
        private String name;
        private int age;
        private double gpa;

        public Student(String name, int age, double gpa) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getGpa() {
            return gpa;
        }
    }
}
package com.javascraper.demo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.intenthq.gander.utils.JSoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException, SQLException {

        SpringApplication.run(DemoApplication.class, args);

        // Registering the Driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        // Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost/thehub";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
        System.out.println("Connection established......");

        final String url = "https://thehub.io/jobs?countryCode=DK";

        try {
            final Document document = Jsoup.connect(url).get();
            BufferedWriter writer = null;
            // String ticker = null;

            for (Element row : document.select("span.card-job-find-list__position")) {

                if (row.select("span.card-job-find-list__position").text().equals("")) {
                    continue;
                } else {
                    writer = new BufferedWriter(new FileWriter("C:/Users/Abdul/Documents/test2.txt"));

                    final String ticker = row.select("span.card-job-find-list__position").text();

                    writer.write(ticker);

                    writer.close();

                }

                // Inserting values
                String query = "INSERT INTO jobs(jobtitle) VALUES (?)";

                PreparedStatement pstmt = con.prepareStatement(query);

                FileReader reader = new FileReader("C:/Users/Abdul/Documents/test2.txt");

                pstmt.setCharacterStream(1, reader);

                pstmt.execute();

                pstmt.close();

                System.out.println("Data inserted......");
            }

            for (Element rows : document.select("div.bullet-inline-list span")) {

                if (rows.select("span:nth-child(1)").text().equals("")) {
                    continue;
                } else {
                    writer = new BufferedWriter(new FileWriter("C:/Users/Abdul/Documents/test4.txt"));

                    final String ticker = rows.select("span:nth-child(1)").text();

                    writer.write(ticker);

                    writer.close();

                }

                // Inserting values
                String query = "INSERT INTO jobs(company) VALUES (?)";

                PreparedStatement pstmt = con.prepareStatement(query);

                FileReader reader = new FileReader("C:/Users/Abdul/Documents/test4.txt");

                // FileReader reader2 = new FileReader("C:/Users/Abdul/Documents/test4.txt");

                pstmt.setCharacterStream(1, reader);

                // pstmt.setCharacterStream(2, reader2);

                pstmt.execute();

            }

            System.out.println("Data inserted......");

            System.out.println(document.outerHtml());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

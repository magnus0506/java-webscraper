

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
        String mysqlUrl = "jdbc:mysql://3.236.216.244:3306/thehub";
        Connection con = DriverManager.getConnection(mysqlUrl, "user", "password");
        System.out.println("Connection established......");

        final String url = "https://thehub.io/jobs?countryCode=DK";

        try {
            final Document document = Jsoup.connect(url).get();
            BufferedWriter writer;
            BufferedWriter writer2;
            BufferedWriter writer3;
            BufferedWriter writer4;
            // String ticker = null;

            for (Element row : document.select("div.media-item__content")) {

                writer = new BufferedWriter(new FileWriter("test2.txt"));

                writer2 = new BufferedWriter(new FileWriter("test4.txt"));

                writer3 = new BufferedWriter(new FileWriter("test5.txt"));

                writer4 = new BufferedWriter(new FileWriter("test6.txt"));

                String ticker = row.select("span.card-job-find-list__position").text();

                String ticker2 = row.select("div.bullet-inline-list span:nth-child(1)").text();

                String ticker3 = row.select("div.bullet-inline-list span:nth-child(2)").text();

                String ticker4 = row.select("div.bullet-inline-list span:nth-child(3)").text();

                writer.write(ticker);

                writer2.write(ticker2);

                writer2.close();

                writer3.write(ticker3);

                writer3.close();

                writer4.write(ticker4);

                writer4.close();

                writer.close();

                // Inserting values
                String query = "INSERT INTO jobs(jobtitle, company, location, jobtype) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(query);

                FileReader reader = new FileReader("test2.txt");

                FileReader reader2 = new FileReader("test4.txt");

                FileReader reader3 = new FileReader("test5.txt");

                FileReader reader4 = new FileReader("test6.txt");

                pstmt.setCharacterStream(1, reader);

                pstmt.setCharacterStream(2, reader2);

                pstmt.setCharacterStream(3, reader3);

                pstmt.setCharacterStream(4, reader4);

                pstmt.execute();

                pstmt.close();

                System.out.println("Data inserted......");
            }

            System.out.println("Data inserted......");

            System.out.println(document.outerHtml());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
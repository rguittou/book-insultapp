package org.openshift;

import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsultGenerator {
    //initialize connexion


	public String generateInsult() {
        Connection conn = null;
		String vowels = "AEIOU";
		String article = "an";
		String insults = "";
		try
		{
            System.out.println("Flag 1");
			String databaseURL = "jdbc:postgresql://172.17.0.9/app2";
			String username = System.getenv("admin");
			String password = System.getenv("admin");
            System.out.println("Flag 2 set connection:");
			Connection connection = DriverManager.getConnection(databaseURL, username, password);
            System.out.println("Flag 3 connection setting:"+connection.getClientInfo());
			if (connection !=null) {
				String SQL = "select a.string AS first, b.string AS second, c.string AS noun from short_adjective a , long_adjective b, noun c ORDER BY random() limit 1";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
                System.out.println("Flag 4 results:"+rs.toString());
				while (rs.next()) {
					if (vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
						article = "a";
					}
					insults=  String.format("Thou art %s %s %s %s!", article, rs.getString("first"), rs.getString("second"), rs.getString("noun"));
				}
				rs.close();
				connection.close();
			}
		}
		catch (Exception e) {System.out.println("Exception :"+e.getMessage());}

		return insults;
	}
}
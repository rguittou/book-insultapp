package org.openshift;

import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsultGenerator {
    //initialize connexion
	Connection conn = null;

	public String generateInsult() {
		String vowels = "AEIOU";
		String article = "an";
		String insults = "";
		try
		{
			String databaseURL = "jdbc:postgresql://172.17.0.9/app2";
			String username = System.getenv("admin");
			String password = System.getenv("admin");
			Connection connection = DriverManager.getConnection(databaseURL, username, password);

			if (connection !=null) {
				String SQL = "select a.string AS first, b.string AS second, c.string AS noun from short_adjective a , long_adjective b, noun c ORDER BY random() limit 1";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
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
		catch (Exception e) {return "Database connection problem!";}

		return insults;
	}
}
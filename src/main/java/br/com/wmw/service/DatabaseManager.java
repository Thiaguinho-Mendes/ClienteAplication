package br.com.wmw.service;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;

public class DatabaseManager {

	public static SQLiteUtil sqLiteUtil;

	static {

		try {
			sqLiteUtil = new SQLiteUtil(Settings.appPath, "ClienteApilcation.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static Connection getConnection() throws SQLException {
		return sqLiteUtil.con();
	}

	public static void loadTabelas() throws SQLException {
		Statement st = getConnection().createStatement();
		st.execute("CREATE TABLE IF NOT EXISTS cliente (codigo INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, nome VARCHAR(255) NOT NULL, tipoPessoa VARCHAR(20)  NOT NULL, cpfCnpj VARCHAR(20)  NOT NULL, telefone VARCHAR(20)  NOT NULL, email VARCHAR(255), origem VARCHAR(20))");
		st.close();
	}

}

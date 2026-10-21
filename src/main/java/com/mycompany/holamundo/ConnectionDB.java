/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.holamundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JulioFuentes
 */

public class ConnectionDB {

    private static final String URL = "jdbc:sqlserver://avanzada2.database.windows.net:1433;database=estudiantes;user=fuentes@avanzada2;password=2024ix1cloverAP;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private static final String USER = "fuentes";
    private static final String PASSWORD = "2024ix1cloverAPK";

    private static Connection conexion = null;

    private ConnectionDB() {
    }

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //establecer conexion
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.err.println("-> Conexion exitosa a SQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error no se encontro el Driver de mssql-jdbc " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con SQL " + e.getMessage());
        }

        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.err.println("-> La conexion con SQL fue cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion" + e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.holamundo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
/**
 *
 * @author JulioFuentes
 */
public class HolaMundo {

    public static void main(String[] args) {
        System.out.println("+++Probando conexion a SQL SERVER+++");
        Connection cn = ConnectionDB.getConexion();
        if (cn == null) {
            System.out.println("No se puede establecer conexion");
            return;
        }

        Scanner sc = new Scanner(System.in);
        try {
            int opcion = 0;
            while (opcion != 5) {
                System.out.println();
                System.out.println("=== MENU ALUMNOS ===");
                System.out.println("1. Insertar alumno");
                System.out.println("2. Listar alumnos");
                System.out.println("3. Actualizar alumno");
                System.out.println("4. Eliminar alumno");
                System.out.println("5. Salir");
                System.out.print("Que accion quieres realizar: ");
                opcion = leerEntero(sc);

                try {
                    switch (opcion) {
                        case 1 -> insertar(cn, sc);
                        case 2 -> listar(cn);
                        case 3 -> actualizar(cn, sc);
                        case 4 -> eliminar(cn, sc);
                        case 5 -> System.out.println("Saliendo...");
                        default -> System.out.println("Opcion no valida");
                    }
                } catch (SQLException e) {
                    System.err.println("Error en la operacion con Alumnos: " + e.getMessage());
                }
            }
        }
        finally {
            ConnectionDB.cerrarConexion();
        }
    }

    private static void insertar(Connection cn, Scanner sc) throws SQLException {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();

        String sql = "INSERT INTO Alumnos (nombre, correo) VALUES (?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas: " + filas);
        }
    }

    private static void listar(Connection cn) throws SQLException {
        String sql = "SELECT id, nombre, correo FROM Alumnos";
        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("--- Lista de Alumnos ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.println("========================================================================");
                System.out.println(id + " | " + nombre + " | " + correo);
                System.out.println("========================================================================");
            }
        }
    }

    private static void actualizar(Connection cn, Scanner sc) throws SQLException {
        System.out.print("Id del alumno a actualizar: ");
        int id = leerEntero(sc);
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo correo: ");
        String correo = sc.nextLine();

        String sql = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setInt(3, id);
            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filas);
        }
    }

    private static void eliminar(Connection cn, Scanner sc) throws SQLException {
        System.out.print("Id del alumno a eliminar: ");
        int id = leerEntero(sc);

        String sql = "DELETE FROM Alumnos WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas: " + filas);
        }
    }

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un numero valido: ");
            }
        }
    }
}
